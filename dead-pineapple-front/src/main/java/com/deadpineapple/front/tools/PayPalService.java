package com.deadpineapple.front.tools;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mikael on 19/05/16.
 */
public class PayPalService {

    private APIContext apiContext;
    private double price;

    public PayPalService(double price){
        this.price = price;
    }

    public String startCheckOut(){
        try {
            Map<String, String> sdkConfig = new HashMap<String, String>();
            sdkConfig.put("mode", "sandbox");

            //clientID, client secret, map
            String accessToken = new OAuthTokenCredential(
                    "AUiWqyPU7-S9Lb7B40caix-WdmLbdeuyz-gFXJxm7R1PWa8cVxkz5kvkxpYzlBqUpajd6El2ybpD0w_D",
                    "EO5ZSJ7XAKLMKax7cAuS9E6h2J7KV1pK4_Lf-OozscG5M0VYBB_85Mqa1QGObHgHiwJXVyVU5IB4exEj",
                    sdkConfig).getAccessToken();

            apiContext = new APIContext(accessToken);
            apiContext.setConfigurationMap(sdkConfig);

            Amount amount = new Amount();
            amount.setCurrency("EUR");
            String p = String.format("%1$,.2f", price).replace(',','.');
            amount.setTotal(p);

            Transaction transaction = new Transaction();
            transaction.setDescription("creating a payment");
            transaction.setAmount(amount);

            List<Transaction> transactions = new ArrayList<Transaction>();
            transactions.add(transaction);

            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");

            Payment payment = new Payment();
            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setTransactions(transactions);
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl("http://localhost:8080/upload/payement");
            redirectUrls.setReturnUrl("http://localhost:8080/upload/payement");
            payment.setRedirectUrls(redirectUrls);

            Payment createdPayment = payment.create(apiContext);
            List<Links> list = createdPayment.getLinks();

            for (Links l:list) {
                if (l.getRel().equals("approval_url"))
                {
                    return l.getHref();
                }
            }

        }
        catch (Exception e)
        {

        }
        return null;
    }

    public boolean finishCheckOut(String paymentID, String payerID, String token)
    {
        try{
            Payment payment = Payment.get(apiContext,paymentID);
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(payerID);
            payment.execute(apiContext, paymentExecute);
            return true;

        } catch (PayPalRESTException e) {
            return false;
        }
    }

}
