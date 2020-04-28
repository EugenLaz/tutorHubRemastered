package TutorHub.Service.Funds;


import TutorHub.Service.Data.UserDaoService;
import TutorHub.model.User;
import com.paypal.api.payments.Amount;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentProcessor {

    @Autowired
    UserDaoService userDao;

    public void withdrawFunds(String payerId, double amount){
        User payer = userDao.getByUserName(payerId);
        if ( payer.getBalance().doubleValue() < amount){
            throw new RuntimeException("Insufficient Funds");
        }
        else {
            payer.updateBalance(BigDecimal.valueOf(amount * (-1)));
            userDao.saveUser(payer);
        }
    }

    public void depositFunds(String payeeId, double amount){
            User user = userDao.getByUserName(payeeId);
            user.updateBalance(BigDecimal.valueOf(amount));
            userDao.saveUser(user);
    }
}
