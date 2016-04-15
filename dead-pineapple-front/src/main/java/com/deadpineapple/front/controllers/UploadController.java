package com.deadpineapple.front.controllers;

import com.deadpineapple.videoHelper.information.VideoInformation;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * Created by saziri on 14/03/2016.
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
    VideoInformation videoInformation;
    @RequestMapping(method = RequestMethod.GET)
    public String addUser() {
        System.out.println("Invoking User");
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
                    new File(request.getServletContext().getRealPath("/") + "upload/").mkdirs();
                    File file = new File(request.getServletContext().getRealPath("/") + "upload/", item.getName());
                    item.write(file);
                    // Create a new video Information
                    videoInformation = new VideoInformation(request.getServletContext().getRealPath("/") + "upload/"+ item.getName());
                    JSONObject jsono = new JSONObject();
                    jsono.put("name", item.getName());
                    jsono.put("size", item.getSize());
                    jsono.put("duration", videoInformation.getDuration());
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
            String imageName = request.getParameter("getthumb");
            imageName = imageName.substring(0, imageName.lastIndexOf('.'))+".png";
            String thumb = request.getServletContext().getRealPath("/") + "upload/" + "thumb_" + imageName;

            videoInformation = new VideoInformation(request.getServletContext().getRealPath("/") + "upload/" + request.getParameter("getthumb"));
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
            File file = new File(request.getServletContext().getRealPath("/") + "upload/" + request.getParameter("delfile"));
            if (file.exists()) {
                file.delete(); // TODO:check and report success
            }
        }
    }
    @RequestMapping(value = "/duration", method = RequestMethod.GET)
    public void getDuration(HttpServletRequest request, HttpServletResponse response){
        if(request.getParameter("getDuration") != null && !request.getParameter("getthumb").isEmpty()){

        }
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
}
