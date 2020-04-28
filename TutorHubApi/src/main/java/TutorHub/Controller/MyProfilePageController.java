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
public class MyProfilePageController {

    protected static final Logger profileLogger = LogManager.getLogger(MyProfilePageController.class);


    @Autowired
    UserDaoService dao;

    @Autowired
    MyJsonParser parser;

    @GetMapping(path = "/getUserInfo")
    public User getInfo(@RequestParam(value = "username") String user) {
        return dao.getByUserName(user);
    }

    @GetMapping(path="/updateProfile")
    public ResponseEntity updateInfo(@RequestBody String userJson) {
        User updatedValuesUser = parser.deserializeUser(userJson, UserDeserializers.UserInfo);
        User user = dao.getByUserName(updatedValuesUser.getUsername());
        user.merge(updatedValuesUser);
        dao.saveUser(user);
        Gson gson = new Gson();

        String message = user.getUsername() + "successfully changed his data!";
        profileLogger.info(message);

        return ResponseEntity.ok()
                .body(gson.toJson(gson.toJson("Update Successful"),String.class));
    }


}
