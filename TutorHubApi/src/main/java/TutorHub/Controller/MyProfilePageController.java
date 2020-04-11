package TutorHub.Controller;

import TutorHub.Service.Data.UserDaoService;
import TutorHub.Service.JsonParsing.Deserializers;
import TutorHub.Service.JsonParsing.Desirializers.UserInfoJsonDeserializer;
import TutorHub.Service.JsonParsing.Desirializers.UserJsonDeserializer;
import TutorHub.Service.JsonParsing.MyJsonParser;
import TutorHub.model.User;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class MyProfilePageController {

    @Autowired
    UserDaoService dao;

    @Autowired
    MyJsonParser parser;

    @GetMapping(path = "/getUserInfo")
    public User getInfo(@RequestParam(value = "username") String user) {
        return dao.getByUserName(user);
    }

    @PostMapping(path="/updateProfile")
    public ResponseEntity updateInfo(@RequestBody String userJson) {
        User user = parser.deserializeUser(userJson, Deserializers.UserInfo);
        System.out.println(user);
//        dao.saveUser(user);
        Gson gson = new Gson();
        return ResponseEntity.ok()
                .body(gson.toJson(gson.toJson("Update Successful"),String.class));
    }

}
