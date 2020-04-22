package TutorHub.Controller;


import TutorHub.Service.Data.UserDaoService;
import TutorHub.Service.JsonParsing.UserDeserializers;
import TutorHub.Service.JsonParsing.MyJsonParser;
import TutorHub.model.User;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class RegistrationContoller {

    protected static final Logger registrationLogger = LogManager.getLogger(RegistrationContoller.class);


    @Autowired
    UserDaoService dao;

    @Autowired
    MyJsonParser parser;

    @PostMapping(path = "/register")
    public ResponseEntity register(@RequestBody String userJson) {
        System.out.println("from registerContoller");
        User user = parser.deserializeUser(userJson, UserDeserializers.UserCredentials);
        int registrationSuccessMarker = dao.checkIfUnique(user);
        String responseMessage=null;
        switch (registrationSuccessMarker){
            case 0:
                responseMessage="The user with given Email already exists!";
                break;
            case -1:
                responseMessage="The user with given Username already exits";
                break;
            case 1:
                dao.saveUser(user);
                responseMessage="Registration successful";
            break;
        }
        System.out.println(user);
        Gson gson = new Gson();

        String message = "New user Registered:" + user.toString();
        registrationLogger.info(message);

        return ResponseEntity.ok()
                .body(gson.toJson(gson.toJson(responseMessage),String.class));
    }


}
