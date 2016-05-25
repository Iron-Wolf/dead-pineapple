package com.deadpineapple.front.controllers;

import com.deadpineapple.dal.RabbitMqEntities.FileIsUploaded;
import com.deadpineapple.dal.dao.ITransactionDao;
import com.deadpineapple.dal.entity.ConvertedFile;
import com.deadpineapple.dal.entity.Transaction;
import com.deadpineapple.dal.entity.UserAccount;
import com.deadpineapple.front.Forms.LoginForm;
import com.deadpineapple.front.tools.Invoice;
import com.deadpineapple.rabbitmq.RabbitInit;
import com.dropbox.core.*;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by saziri on 16/05/2016.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    LoginForm userData;
    UserAccount user;
    String UPLOAD_PATH;

    // Transaction
    @Autowired
    ITransactionDao transactionDao;
    List<Transaction> transactions = new ArrayList();
    ArrayList<Invoice> invoices;
    Invoice invoice;
    Date dateTransaction;
    static int totalSpace = 10240;
    double invoicePrice;

    // Dropbox config
    final String APP_KEY = "3xt31on71g5n2d6";
    final String APP_SECRET = "01hgg9uje17vwwv";
    final String URL_SITE = "http://localhost:8080/dashboard/auth";
    DbxWebAuth webAuth;
    DbxClientV2 client;
    DbxRequestConfig config;

    public void setTransactionDao(ITransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getInvoices(HttpServletRequest request, Model model) {
        userData = (LoginForm) request.getSession().getAttribute("LOGGEDIN_USER");
        user = (UserAccount) request.getSession().getAttribute("USER_INFORMATIONS");
        UPLOAD_PATH = request.getServletContext().getRealPath("/") + "upload/"
                + user.getFirstName().trim() + "_"
                + user.getLastName().trim() + "/";
        invoices = new ArrayList();
        getHistory(request.getRealPath("/WEB-INF/rabbitConfig.xml"));
        System.out.println("size" + invoices.size());
        //System.out.println(invoices.get(0).get(0).get("name"));
        model.addAttribute("invoices", invoices);

        double userSize = user.getTotalSize();
        System.out.println("Taile totale :" + user.getTotalSize() + " &" + userSize);
        double spaceLeft = (userSize / totalSpace) * 100;
        model.addAttribute("spacePercent", spaceLeft);
        model.addAttribute("userSize", userSize);
        model.addAttribute("userAccount", new UserAccount());
        return new ModelAndView("dashboard", "model", model);
    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        if (fileName == null || fileName.equals("")) {
            throw new ServletException("File Name can't be null or empty");
        }
        File file = new File(UPLOAD_PATH, fileName);
        if (!file.exists()) {
            throw new ServletException("File doesn't exists on server.");
        }
        System.out.println("File location on server::" + file.getAbsolutePath());
        ServletContext ctx = request.getServletContext();
        InputStream fis = new FileInputStream(file);
        String mimeType = ctx.getMimeType(file.getAbsolutePath());
        response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        ServletOutputStream os = response.getOutputStream();
        byte[] bufferData = new byte[1024];
        int read = 0;
        while ((read = fis.read(bufferData)) != -1) {
            os.write(bufferData, 0, read);
        }
        os.flush();
        os.close();
        fis.close();
        System.out.println("File downloaded at client successfully");
    }

    @RequestMapping(value = "/downloadFileDb", method = RequestMethod.GET)
    protected String uploadInDb(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JsonReader.FileLoadException {
        String accessToken = (String) request.getSession().getAttribute("ACCESS_TOKEN");

      config = new DbxRequestConfig(
                "JavaTutorial/1.0", Locale.getDefault().toString());
        if (accessToken != null || accessToken != "") {
            client = new DbxClientV2(config, accessToken);
            String fileName = request.getParameter("fileName");
            if (fileName == null || fileName.equals("")) {
                throw new ServletException("File Name can't be null or empty");
            }
            File file = new File(UPLOAD_PATH, fileName);
            if (!file.exists()) {
                throw new ServletException("File doesn't exists on server.");
            }
            System.out.println("File location on server::" + file.getAbsolutePath());
            InputStream in = new FileInputStream(fileName);
            try {
                FileMetadata metadata = client.files().uploadBuilder(fileName).uploadAndFinish(in);
            } catch (DbxException e) {
                e.printStackTrace();
            }
        } else {
            return "redirect:" + getDropBoxUrl(request);
        }
        return "redirect:/dashboard";
    }

    private void initTransaction(Transaction transaction) {
        invoice = new Invoice();
        invoicePrice = 0.0;
        invoice.setDate(transaction.getDate());
        dateTransaction = transaction.getDate();
    }

    private void getHistory(String path) {
        // Get transactions from bdd
        transactions = transactionDao.getTransByUser(user);

        if (transactions.size() > 0) {
            // Get the first transaction and init parameters
            Transaction transactionTest = transactions.get(0);
            initTransaction(transactionTest);
            for (Transaction aTransaction : transactions) {

                System.out.println("Price :" + invoicePrice + "id " + aTransaction.getIdTransaction());
                // if the transaction is different, create new transaction
                if (!aTransaction.getDate().equals(dateTransaction)) {
                    invoice.setPrice(Math.round(invoicePrice * 100.0) / 100.0);
                    invoices.add(invoice);
                    invoice = new Invoice();
                    //jsonTransactions.put(jsonTransaction);
                    initTransaction(aTransaction);
                }
                invoicePrice += aTransaction.getPrix();
                ConvertedFile cVideo = aTransaction.getConvertedFiles();
                if (cVideo != null) {
                    invoice.addConvertedFile(cVideo);
                    // If video is not converted yet start conversion
                    if (aTransaction.getPayed() && cVideo.getConverted() == null) {
                        FileIsUploaded videoToConvert = new FileIsUploaded(cVideo.getId(), cVideo.getFilePath(), cVideo.getNewType(), null);
                        RabbitInit init = new RabbitInit(path);
                        init.getFileUploadedSender().send(videoToConvert);
                        init.closeAll();
                        cVideo.setConverted(false);
                        System.out.println("Conversion du fichier : " + cVideo.getOriginalName());
                    }
                }

            }
            invoice.setPrice(Math.round(invoicePrice * 100.0) / 100.0);
            invoices.add(invoice);
            //init.closeAll();
        }

    }

    // DropBox auth
    private String getDropBoxUrl(HttpServletRequest request) throws JsonReader.FileLoadException {
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        HttpSession session = request.getSession(true);
        String sessionKey = "dropbox-auth-csrf-token";
        DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
        webAuth = new DbxWebAuth(config, appInfo, URL_SITE, csrfTokenStore);
        String authorizeUrl = webAuth.start();
        return authorizeUrl;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public org.springframework.web.servlet.ModelAndView auth(HttpServletRequest request, HttpServletResponse response, Model model) throws DbxException, IOException {
        // Load the request token we saved in part 1.
        DbxAuthFinish authFinish = null;
        try {
            authFinish = webAuth.finish(request.getParameterMap());
        } catch (DbxWebAuth.BadRequestException ex) {
            response.sendError(400);
        } catch (DbxWebAuth.BadStateException ex) {
            // Send them back to the start of the auth flow.
            response.sendRedirect("http://localhost:8080/dashboard/");
        } catch (DbxWebAuth.CsrfException ex) {
        } catch (DbxWebAuth.NotApprovedException ex) {
            // When Dropbox asked "Do you want to allow this app to access your
            // Dropbox account?", the user clicked "No".
        } catch (DbxWebAuth.ProviderException ex) {
            response.sendError(503, "Error communicating with Dropbox.");
        } catch (DbxException ex) {
            response.sendError(503, "Error communicating with Dropbox.");
        }

        String accessToken = authFinish.getAccessToken();
        client = new DbxClientV2(config, accessToken);
        return new org.springframework.web.servlet.ModelAndView("dashboard", "model", model);
    }
}
