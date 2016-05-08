package com.deadpineapple.front.controllers;

import com.deadpineapple.dal.dao.IConvertedFileDao;
import com.deadpineapple.dal.dao.ITransactionDao;
import com.deadpineapple.dal.dao.TransactionDao;
import com.deadpineapple.dal.entity.ConvertedFile;
import com.deadpineapple.dal.entity.Transaction;
import com.deadpineapple.dal.entity.UserAccount;
import com.deadpineapple.front.Forms.LoginForm;
import com.deadpineapple.front.tools.VideoFile;
import com.deadpineapple.videoHelper.TimeSpan;
import com.deadpineapple.videoHelper.information.VideoInformation;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.persistence.Transient;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by saziri on 14/03/2016.
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends HttpServlet {

    @Autowired
    IConvertedFileDao convertedFileDao;
    ConvertedFile convertedFile;
    ArrayList<VideoFile> convertedFiles = new ArrayList();

    @Autowired
    ITransactionDao transactionDao;
    List<Transaction> transactions = new ArrayList();
    JSONArray jsonTransactions, jsonConvertedFiles;
    JSONObject jsonTransaction;
    int idTransaction;

    String UPLOAD_PATH;
    LoginForm userData;
    UserAccount user;
    VideoInformation videoInformation;

    public void setConvertedFileDao(IConvertedFileDao convertedFileDao) {
        this.convertedFileDao = convertedFileDao;
    }
    public void setTransactionDao(ITransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String uploadPage(HttpServletRequest request) {
        userData = (LoginForm) request.getSession().getAttribute("LOGGEDIN_USER");
        user = (UserAccount) request.getSession().getAttribute("USER_INFORMATIONS");
        UPLOAD_PATH = request.getServletContext().getRealPath("/") + "upload/"+user.getFirstName()+"_"+user.getLastName()+"/";
        System.out.println("Invoking Upload page");
        return "upload";
    }

    // Prepare the upload
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public void prepareUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("getfile") != null && !request.getParameter("getfile").isEmpty()) {
            // Save location
            File file = new File(request.getServletContext().getRealPath("/") + "videos/" + request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }  // } // TODO: check and report success
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void uploadVideo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }
        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    new File(UPLOAD_PATH).mkdirs();
                    File file = new File(UPLOAD_PATH, item.getName());
                    item.write(file);

                    // Save video in bdd
                    String filePath = UPLOAD_PATH + item.getName();
                    Date creationDate = new Date();
                    VideoFile video = new VideoFile();
                    convertedFile = new ConvertedFile();
                    convertedFile.setUserAccount(user);
                    convertedFile.setFilePath(filePath);
                    convertedFile.setCreationDate(creationDate);
                    convertedFile.setOriginalName(item.getName());
                    convertedFile.setOldType(FilenameUtils.getExtension(filePath));
                    //convertedFile.setNewType();
                    convertedFile.setSize((int) item.getSize());
                    convertedFileDao.createFile(convertedFile);

                    // Create a new video Information
                    videoInformation = new VideoInformation(filePath);
                    TimeSpan duration = videoInformation.getDuration();
                    double price = 0;

                    double time = (double)((duration.getHeures() * 60) + duration.getMinutes());
                    System.out.println("temps ="+time +duration.getHeures() * 60);
                    if(time < 5){
                        price = Math.log(5) - 1;
                    }
                    else{
                        price = Math.log(time) - 1;

                    }

                    // add video information into converted file
                    video.setConvertedFile(convertedFile);
                    video.setVideoInformation(videoInformation);
                    convertedFiles.add(video);
                    JSONObject jsono = new JSONObject();
                    jsono.put("name", item.getName());
                    jsono.put("size", item.getSize());
                    jsono.put("duration", duration);
                    jsono.put("price", String.format("%.2f", price));
                    jsono.put("url", "UploadServlet?getfile=" + item.getName());
                    jsono.put("thumbnail_url", "/upload/getThumb?getthumb=" + item.getName());
                    jsono.put("delete_url", "/upload/deleteFile?delfile=" + item.getName());
                    jsono.put("delete_type", "GET");
                    json.put(jsono);
                    System.out.println(json.toString());
                }
            }
        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            writer.write(json.toString());
            writer.close();
        }
    }
    @RequestMapping(value = "/getThumb", method = RequestMethod.GET)
    public void getThumb(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
            String filePath = UPLOAD_PATH +request.getParameter("getthumb");
            String imageName = request.getParameter("getthumb");
            imageName = imageName.substring(0, imageName.lastIndexOf('.'))+".png";
            String thumb = UPLOAD_PATH + "thumb_" + imageName;
            for (VideoFile video:
                    convertedFiles) {
                if(video.getConvertedFile().getFilePath().equals(filePath)){
                    videoInformation = video.getVideoInformation();
                    break;
                }
            }
            videoInformation.generateAThumbnailImage(thumb);
            File file = new File(thumb);
            if (file.exists()) {
                System.out.println(file.getAbsolutePath());
                BufferedImage resizeImagePng = resizeImage(ImageIO.read(file));
                ImageIO.write(resizeImagePng, "png", new File(thumb));
                // Set a previsualisation image to the video and display it on the client size
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        }
    }
    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
    public void deleteFile(HttpServletRequest request){
        if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(UPLOAD_PATH + request.getParameter("delfile"));
            String filePath = UPLOAD_PATH + request.getParameter("delfile");
            if (file.exists()) {
                for (VideoFile video:
                        convertedFiles) {
                    if(video.getConvertedFile().getFilePath().equals(filePath)){

                        // delete file from bdd
                        file.delete();
                        break;
                    }
                }
            }
        }
    }
    @RequestMapping(value = "/setFormat", method = RequestMethod.GET)
    public void setConvertFormat(HttpServletRequest request){
        if (request.getParameter("format") != null && !request.getParameter("format").isEmpty()) {
            String filePath = UPLOAD_PATH + request.getParameter("file");
            String format = request.getParameter("format");

            for (VideoFile video:
                    convertedFiles) {
                if(video.getConvertedFile().getFilePath().equals(filePath)){
                    System.out.println("Vidéo trouvée2"+filePath);
                    convertedFileDao.updateFile(video.getConvertedFile()).setNewType(format);
                    break;
                }
            }
        }
    }
    @RequestMapping(value="/convert", method = RequestMethod.GET)
    public void convert(){
        // Start converting video
    }
    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("jpg")) {
                mimetype = "image/jpg";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("jpeg")) {
                mimetype = "image/jpeg";
            } else if (getSuffix(file.getName()).equalsIgnoreCase("gif")) {
                mimetype = "image/gif";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype = mtMap.getContentType(file);
            }
        }
        return mimetype;
    }


    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        return suffix;
    }
    private static BufferedImage resizeImage(BufferedImage originalImage) {
        BufferedImage resizedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 100, 100, null);
        g.dispose();

        return resizedImage;
    }

    private void getHistory(){
        // Get transactions from bdd
        transactions = transactionDao.getTransByUser(user);
        // Get the first transaction and init parameters
        Transaction transactionTest = transactions.get(0);
        initTransaction(transactionTest);

        for(Transaction aTransaction: transactions){
            // if the transaction is different, create new transaction
            if(aTransaction.getIdTransaction() != idTransaction){
                jsonTransactions.put(jsonTransaction);
                initTransaction(aTransaction);
            }
            ConvertedFile cVideo = aTransaction.getConvertedFiles();
            if(cVideo != null){
                JSONObject jsonConvertedFile = new JSONObject();
                jsonConvertedFile.put("name", cVideo.getOriginalName());
                jsonConvertedFile.put("size", cVideo.getSize());
                //jsono.put("duration", uVideo.get);
                //jsono.put("price", String.format("%.2f", price));
                // If video is not converted yet, what link ?
                jsonConvertedFile.put("url", "upload/downloadFile?fileName=" + cVideo.getOriginalName());
                jsonConvertedFile.put("thumbnail_url", "/upload/getThumb?getthumb=" + cVideo.getOriginalName());
                jsonConvertedFile.put("delete_url", "/upload/deleteFile?delfile=" + cVideo.getOriginalName());
                jsonConvertedFile.put("delete_type", "GET");
                jsonConvertedFiles.put(jsonConvertedFile);
            }

        }

    }
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        if(fileName == null || fileName.equals("")){
            throw new ServletException("File Name can't be null or empty");
        }
        File file = new File(UPLOAD_PATH, fileName);
        if(!file.exists()){
            throw new ServletException("File doesn't exists on server.");
        }
        System.out.println("File location on server::"+file.getAbsolutePath());
        ServletContext ctx = getServletContext();
        InputStream fis = new FileInputStream(file);
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentType(mimeType != null? mimeType:"application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        ServletOutputStream os       = response.getOutputStream();
        byte[] bufferData = new byte[1024];
        int read=0;
        while((read = fis.read(bufferData))!= -1){
            os.write(bufferData, 0, read);
        }
        os.flush();
        os.close();
        fis.close();
        System.out.println("File downloaded at client successfully");
    }
    private void initTransaction(Transaction transaction){
        jsonTransaction = new JSONObject();
        jsonTransaction.put("date",transaction.getDate());
        jsonTransaction.put("price",transaction.getPrix());
        jsonConvertedFiles = new JSONArray();
        idTransaction = transaction.getIdTransaction();
    }
}
