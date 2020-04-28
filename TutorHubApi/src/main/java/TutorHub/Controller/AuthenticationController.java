package TutorHub.Controller;


import TutorHub.model.AuthBean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class AuthenticationController {

    @GetMapping(path = "/basicauth")
    public AuthBean basicauth() {
        System.out.println("worked");
        return new AuthBean("You are authenticated");
    }


}
