package TutorHub.Controller;


import TutorHub.Service.Data.UserDaoService;
import TutorHub.model.AuthBean;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthenticationController {

    @Autowired
    UserDaoService userDao;

    @GetMapping(path = "/getRole")
    public ResponseEntity roleLoader(@RequestParam("username") String username) {
        String roles = userDao.getByUserName(username).getUserRoles().toString();
        Gson gson = new Gson();
        return ResponseEntity.ok().body(gson.toJson(roles, String.class));
    }

    @GetMapping(path = "/basicauth")
    public AuthBean basicauth() {
        return new AuthBean("You are authenticated");
    }


}
