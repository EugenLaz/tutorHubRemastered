package TutorHub.Controller;


import TutorHub.Service.Data.LessonRequestDaoService;
import TutorHub.Service.Data.UserDaoService;
import TutorHub.Service.JsonParsing.MyJsonParser;
import TutorHub.model.LessonRequest;
import TutorHub.model.LessonRequestStatus;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
public class LessonRequestController {

    protected static final Logger lessonLogger = LogManager.getLogger(MyProfilePageController.class);


    @Autowired
    MyJsonParser deserializer;

    @Autowired
    MyJsonParser parser;

    @Autowired
    LessonRequestDaoService requestDao;

    @Autowired
    UserDaoService userDao;

    @PostMapping("/sendRequest")
    public ResponseEntity<String> sendRequest(@RequestBody String json) {
        LessonRequest lessonRequest = deserializer.deserializeLesson(json);

        Gson gson = new Gson();
        //The test of balance. If users, account doesn't have enough money. We don't create request
        if ( userDao.getByUserName(lessonRequest.getStudentID())
                .getBalance().longValue() < lessonRequest.getPricePerHour())
            return ResponseEntity.accepted().body(gson.toJson(gson.toJson("Not enough money."), String.class));
        else {
            requestDao.saveLessonRequest(lessonRequest);
        }
        lessonLogger.debug(lessonRequest.getStudentID() + "Sended request to " + lessonRequest.getTutorID());

        return ResponseEntity.ok()
                .body(gson.toJson(gson.toJson("Request Sended"), String.class));
    }

    @GetMapping("/loadMyRequests")
    public Object[] loadRequests(@RequestParam(value = "username") String username) {
        return requestDao.findNewByUser(username).toArray();
    }

    @PostMapping("/rejectRequest/{id}")
    public void rejectRequest(@PathVariable(value = "id") int id) {
        requestDao.changeStatus(id, LessonRequestStatus.Rejected);
        System.out.println("rejected");
    }

    @PostMapping("/approveRequest/{id}")
    public void approve(@PathVariable(value = "id") int id) {
        requestDao.changeStatus(id, LessonRequestStatus.Approved);
        System.out.println("approved");
    }


}
