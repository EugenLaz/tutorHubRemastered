package TutorHub.Controller;


import TutorHub.Service.Data.UserDaoService;
import TutorHub.Service.JsonParsing.Deserializers;
import TutorHub.Service.JsonParsing.Desirializers.UserJsonDeserializer;
import TutorHub.Service.JsonParsing.MyJsonParser;
import TutorHub.model.User;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class RegistrationContoller {

    @Autowired
    UserDaoService dao;

    @Autowired
    MyJsonParser parser;

    @PostMapping(path = "/register")
    public ResponseEntity register(@RequestBody String userJson) {
        System.out.println("from registerContoller");
        User user = parser.deserializeUser(userJson, Deserializers.UserCredentials);
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
//                dao.saveUser(user);
                responseMessage="Registration successful";
            break;
        }
        System.out.println(user);
        Gson gson = new Gson();
        return ResponseEntity.ok()
                .body(gson.toJson(gson.toJson(responseMessage),String.class));
    }


}
