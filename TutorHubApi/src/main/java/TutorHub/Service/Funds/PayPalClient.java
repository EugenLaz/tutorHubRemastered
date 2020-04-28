package TutorHub.Service.Funds;

import TutorHub.Service.Data.UserDaoService;
import TutorHub.model.User;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PayPalClient {

    @Autowired
    UserDaoService userDao;

    @Autowired
    PaymentProcessor paymentProcessor;

    String clientId = "AXjf7gYVK6bBhq8ji-YlfzSJvE82AGbfUMYVrA2cf0an3isa5whE-eC2nxTueEbiuKEXbNIJkn0awZB8";
    String clientSecret = "EOAnTuHQq7oyXOKxFDM9ORP_VsnUfb7JsMYNCfOPRBlPSSziti4G0cLT1Om5jfiY2ArKyelMt4xRA7oy";

    public Map<String, Object> createPayment(String sum) {
        Map<String, Object> response = new HashMap<String, Object>();
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(sum);
        Transaction transaction = new Transaction();
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
        redirectUrls.setCancelUrl("http://localhost:4200/user/profile");
        redirectUrls.setReturnUrl("http://localhost:4200/user/paymentPage");
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            createdPayment = payment.create(context);
            if (createdPayment != null) {
                List<Links> links = createdPayment.getLinks();
                for (Links link : links) {
                    if (link.getRel().equals("approval_url")) {
                        redirectUrl = link.getHref();
                        break;
                    }
                }
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
            e.printStackTrace();
        }

        return response;
    }

    public Map<String, Object> completePayment(HttpServletRequest req) {
        Map<String, Object> response = new HashMap();

        APIContext context = new APIContext(clientId, clientSecret, "sandbox");

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(req.getParameter("PayerID"));

        try {
            Payment payment = Payment.get(context, req.getParameter("paymentId"));
            Payment executedPayment = payment.execute(context, paymentExecution);
            if (payment != null && executedPayment.getState().equals("approved")) {
                System.out.println("FROM APPROVED");
                String userAccountId = req.getParameter("userID");
                Amount transactionAmount = executedPayment.getTransactions().get(0).getAmount();
                paymentProcessor.depositFunds(userAccountId,Double.valueOf(transactionAmount.getTotal()));
                response.put("status", "success");
                response.put("message", "Your balance is updated");
            }
        } catch (PayPalRESTException e ) {
            response.put("status", "error");
            response.put("message", e.getDetails().getName());
        }
        System.out.println(response);
        return response;
    }

}
