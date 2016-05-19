package com.deadpineapple.front.controllers;

import com.deadpineapple.dal.dao.IConvertedFileDao;
import com.deadpineapple.dal.entity.ConvertedFile;
import com.deadpineapple.dal.entity.UserAccount;
import com.deadpineapple.front.Forms.LoginForm;
import com.deadpineapple.front.tools.PayPalService;
import com.deadpineapple.front.tools.VideoFile;
import com.deadpineapple.videoHelper.TimeSpan;
import com.deadpineapple.videoHelper.information.VideoInformation;
import com.dropbox.core.*;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.SearchResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by saziri on 14/03/2016.
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends HttpServlet {
    // Video variables
    @Autowired
    IConvertedFileDao convertedFileDao;
    ConvertedFile convertedFile;
    ArrayList<VideoFile> convertedFiles = new ArrayList();
    JSONArray json;

    String UPLOAD_PATH;
    LoginForm userData;
    UserAccount user;
    VideoInformation videoInformation;
    PayPalService ps;

    // Dropbox config
    final String APP_KEY = "3xt31on71g5n2d6";
    final String APP_SECRET = "01hgg9uje17vwwv";
    final String URL_SITE = "http://localhost:8080/upload/auth";
    DbxWebAuth webAuth;
    DbxClientV2 client;
    DbxRequestConfig config;
    HttpSession session;

    List<String> ext = new ArrayList<String>(Arrays.asList(".avi", ".mp4", ".ogg", ".flv", ".swf", ".dv", ".mov"));

    public void setConvertedFileDao(IConvertedFileDao convertedFileDao) {
        this.convertedFileDao = convertedFileDao;
    }
    @RequestMapping(method = RequestMethod.GET)
    public String uploadPage(HttpServletRequest request, Model model,HttpServletResponse response ) throws JsonReader.FileLoadException, IOException {
        userData = (LoginForm) request.getSession().getAttribute("LOGGEDIN_USER");
        user = (UserAccount) request.getSession().getAttribute("USER_INFORMATIONS");
        UPLOAD_PATH = request.getServletContext().getRealPath("/") + "upload/"
                + user.getFirstName() + "_"
                + user.getLastName() + "/";
        // Initiate an instance of dropbox
        model.addAttribute("dropboxUrl", getDropBoxUrl(request));
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
        json = new JSONArray();
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
    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public void getUploadedFiles(HttpServletResponse response) throws IOException {
        JSONArray history = new JSONArray();
        List<ConvertedFile> convertedFiles = convertedFileDao.findByUser(user);
        for(ConvertedFile cf : convertedFiles){
            // Get duration
            videoInformation = new VideoInformation(cf.getFilePath());
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
            JSONObject jsono = new JSONObject();
            jsono.put("name", cf.getOriginalName());
            jsono.put("size", cf.getSize());
            jsono.put("duration", duration);
            jsono.put("price", String.format("%.2f", price));
            jsono.put("url", "UploadServlet?getfile=" + cf.getOriginalName());
            jsono.put("thumbnail_url", "/upload/getThumb?getthumb=" + cf.getOriginalName());
            jsono.put("delete_url", "/upload/deleteFile?delfile=" + cf.getOriginalName());
            jsono.put("delete_type", "GET");
            history.put(jsono);
        }
        if(history != null) {
            PrintWriter writer = response.getWriter();
            response.setContentType("application/json");
            writer.write(history.toString());
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
                        convertedFileDao.createFile(video.getConvertedFile());
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

    @RequestMapping(value="/facture", method = RequestMethod.GET)
    public String convert(){
        // Créate the Transaction and redirect to PayPal

        //TODO : créer Transaction

        //PayPal section
        ps = new PayPalService(0.10);
        String paypalURL = ps.startCheckOut();
        if (paypalURL != null)
            return "redirect:"+paypalURL;
        else
            return "";
    }

    @RequestMapping(value="/payement", method = RequestMethod.GET)
    public String finish(HttpServletRequest request){
        // fin de la transaction
        // validation payement et passage de transaction isPayed à true
        String paymentID = (String) request.getParameter("paymentId");
        String token = (String) request.getParameter("token");
        String payerID = (String) request.getParameter("PayerID");

        boolean transactStatus = ps.finishCheckOut(paymentID,payerID, token);
        if (transactStatus) {
            return "redirect:/dashboard";
        }
        else
            return "";
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
        BufferedImage resizedImage = new BufferedImage(80, 57, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 80, 57, null);
        g.dispose();

        return resizedImage;
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

    private String getDropBoxUrl(HttpServletRequest request) throws JsonReader.FileLoadException {
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        config = new DbxRequestConfig(
                "JavaTutorial/1.0", Locale.getDefault().toString());
        HttpSession session = request.getSession(true);
        String sessionKey = "dropbox-auth-csrf-token";
        DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
        webAuth = new DbxWebAuth(config, appInfo, URL_SITE, csrfTokenStore);
        String authorizeUrl = webAuth.start();
        return authorizeUrl;
    }
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ModelAndView auth(HttpServletRequest request,HttpServletResponse response, Model model) throws DbxException, IOException {
        // Load the request token we saved in part 1.
        DbxAuthFinish authFinish = null;
        try {
            authFinish = webAuth.finish(request.getParameterMap());
        }
        catch (DbxWebAuth.BadRequestException ex) {
            log("On /dropbox-auth-finish: Bad request: " + ex.getMessage());
            response.sendError(400);
        }
        catch (DbxWebAuth.BadStateException ex) {
            // Send them back to the start of the auth flow.
            response.sendRedirect("http://my-server.com/dropbox-auth-start");
        }
        catch (DbxWebAuth.CsrfException ex) {
            log("On /dropbox-auth-finish: CSRF mismatch: " + ex.getMessage());
        }
        catch (DbxWebAuth.NotApprovedException ex) {
            // When Dropbox asked "Do you want to allow this app to access your
            // Dropbox account?", the user clicked "No".
        }
        catch (DbxWebAuth.ProviderException ex) {
            System.out.println("On /dropbox-auth-finish: Auth failed: " + ex.getMessage());
            response.sendError(503, "Error communicating with Dropbox.");
        }
        catch (DbxException ex) {
            System.out.println("On /dropbox-auth-finish: Error getting token: " + ex.getMessage());
            response.sendError(503, "Error communicating with Dropbox.");
        }

        String accessToken = authFinish.getAccessToken();
        client = new DbxClientV2(config, accessToken);
        //System.out.println("Linked account: " + client.getAccountInfo().displayName);
        ArrayList<String> dropboxFolders =  getVideoFiles();
        model.addAttribute("dropboxFiles",dropboxFolders);
        return new ModelAndView("upload", "model", model);
    }
    private ArrayList<String> getVideoFiles() throws DbxException {
        ArrayList<String> dropboxFolders = new ArrayList();
        try {
            for(String format : ext){
                SearchResult search = client.files().search("", format);
                for(int i = 0; i < search.getMatches().size(); i++){
                    String pat = search.getMatches().get(i).getMetadata().getPathDisplay();
                    dropboxFolders.add(pat);
                    System.out.println(pat);
                }
            }



        } catch (DbxException e) {
            e.printStackTrace();
        }
        return dropboxFolders;
    }

    @RequestMapping(value = "/uploadDb", method = RequestMethod.GET)
    public void downloadDropboxFile(HttpServletRequest request, HttpServletResponse response) throws IOException, DbxException {
        String fileName = request.getParameter("fileName");
        String filePath = UPLOAD_PATH+fileName;
        Long  size;
        FileOutputStream outputStream = new FileOutputStream(filePath);
        try {
            DbxDownloader<FileMetadata> download = client.files().download(filePath);
            download.download(outputStream);
            size = download.getResult().getSize();
        } finally {
            outputStream.close();
        }

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        //Save video in bdd

        Date creationDate = new Date();
        VideoFile video = new VideoFile();
        convertedFile = new ConvertedFile();
        convertedFile.setUserAccount(user);
        convertedFile.setFilePath(filePath);
        convertedFile.setCreationDate(creationDate);
        convertedFile.setOriginalName(fileName);
        convertedFile.setOldType(FilenameUtils.getExtension(filePath));
        //convertedFile.setNewType();

        convertedFile.setSize(Integer.parseInt(size.toString()));
        convertedFileDao.createFile(convertedFile);

        // Create a new video Information
        videoInformation = new VideoInformation(filePath);
        TimeSpan duration = videoInformation.getDuration();
        double price = 0;

        double time = (double)((duration.getHeures() * 60) + duration.getMinutes());
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
        jsono.put("name", fileName);
        jsono.put("size", size);
        jsono.put("duration", duration);
        jsono.put("price", String.format("%.2f", price));
        jsono.put("url", "UploadServlet?getfile=" + fileName);
        jsono.put("thumbnail_url", "/upload/getThumb?getthumb=" + fileName);
        jsono.put("delete_url", "/upload/deleteFile?delfile=" + fileName);
        jsono.put("delete_type", "GET");

        writer.write(jsono.toString());
        writer.close();
    }
}
