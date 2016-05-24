package com.deadpineapple.front.controllers;

import com.deadpineapple.dal.RabbitMqEntities.FileIsUploaded;
import com.deadpineapple.dal.dao.ITransactionDao;
import com.deadpineapple.dal.entity.ConvertedFile;
import com.deadpineapple.dal.entity.Transaction;
import com.deadpineapple.dal.entity.UserAccount;
import com.deadpineapple.front.Forms.LoginForm;
import com.deadpineapple.front.tools.Invoice;
import com.deadpineapple.rabbitmq.RabbitInit;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    int idTransaction;
    static int totalSpace = 10240;
    double invoicePrice;

    public void setTransactionDao(ITransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getInvoices(HttpServletRequest request, Model model){
        userData = (LoginForm) request.getSession().getAttribute("LOGGEDIN_USER");
        user = (UserAccount) request.getSession().getAttribute("USER_INFORMATIONS");
        UPLOAD_PATH = request.getServletContext().getRealPath("/") + "upload/"
                + user.getFirstName() + "_"
                + user.getLastName() + "/";
        invoices = new ArrayList();
        getHistory();
        System.out.println("size"+invoices.size());
        //System.out.println(invoices.get(0).get(0).get("name"));
        model.addAttribute("invoices", invoices);

        double userSize = user.getTotalSize();
        System.out.println("Taile totale :"+user.getTotalSize()+" &"+userSize);
        double spaceLeft = (userSize/ totalSpace) * 100;
        model.addAttribute("spacePercent",spaceLeft);
        model.addAttribute("userSize", userSize);
        model.addAttribute("userAccount", new UserAccount());
        return new ModelAndView("dashboard", "model", model);
    }
    private void initTransaction(Transaction transaction){
        invoice = new Invoice();
        invoicePrice = 0.0;
        invoice.setDate(transaction.getDate());
        idTransaction = transaction.getIdTransaction();
    }
    private void getHistory(){
        // Get transactions from bdd
        transactions = transactionDao.getTransByUser(user);

        if (transactions.size() > 0) {
            // Get the first transaction and init parameters
            Transaction transactionTest = transactions.get(0);
            initTransaction(transactionTest);
            RabbitInit init = new RabbitInit();
            for (Transaction aTransaction : transactions) {
                invoicePrice += aTransaction.getPrix();
                System.out.println("Price :"+ invoicePrice);
                // if the transaction is different, create new transaction
                if (aTransaction.getIdTransaction() != idTransaction) {
                    invoice.setPrice(Math.round(invoicePrice*100.0)/100.0);
                    invoices.add(invoice);
                    invoice = new Invoice();
                    //jsonTransactions.put(jsonTransaction);
                    initTransaction(aTransaction);
                }
                ConvertedFile cVideo = aTransaction.getConvertedFiles();
                if (cVideo != null) {
                    invoice.addConvertedFile(cVideo);
                    // If video is not converted yet start conversion
                    if(aTransaction.getPayed() && cVideo.getConverted() == null){
                        FileIsUploaded videoToConvert = new FileIsUploaded(cVideo.getId(), cVideo.getFilePath(), cVideo.getNewType(), "mpeg4");
                        //init.getFileUploadedSender().send(videoToConvert);
                        //cVideo.setConverted(false);
                        System.out.println("Conversion du fichier : "+cVideo.getOriginalName());
                    }
                }

            }
            invoice.setPrice(Math.round(invoicePrice*100.0)/100.0);
            invoices.add(invoice);
            //init.closeAll();
        }

    }
}
