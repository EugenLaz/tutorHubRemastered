package TutorHub.Controller;


import TutorHub.Service.Data.UserDaoService;
import TutorHub.model.AuthBean;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class AuthenticationController {

    @Autowired
    UserDaoService userDao;

    @GetMapping(path = "/getRole")
    public ResponseEntity roleLoader(@RequestParam("username") String username) {
        System.out.println(username);
        String roles=userDao.getByUserName(username).getUserRoles().toString();
        System.out.println(roles);
        Gson gson = new Gson();
        return ResponseEntity.ok().body(gson.toJson(roles,String.class));
    }

    @GetMapping(path = "/basicauth")
    public AuthBean basicauth() {
        System.out.println("worked");
        return new AuthBean("You are authenticated");
    }




}
