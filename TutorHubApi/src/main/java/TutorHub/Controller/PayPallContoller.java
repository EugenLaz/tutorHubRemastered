package TutorHub.Controller;

import TutorHub.Service.Funds.PayPalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/paypal")
public class PayPallContoller {

    @Autowired
    PayPalClient payPalClient;

    @PostMapping(value = "/make/payment")
    public Map<String, Object> makePayment(@RequestParam("sum") String sum) {
        return payPalClient.createPayment(sum);
    }

    @GetMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(HttpServletRequest request) {
        return payPalClient.completePayment(request);
    }

}
