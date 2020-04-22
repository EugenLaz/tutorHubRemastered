package TutorHub.Controller;


import TutorHub.Service.Data.LessonRequestDaoService;
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
    LessonRequestDaoService dao;

    @PostMapping("/sendRequest")
    public ResponseEntity<String> sendRequest(@RequestBody String json){
        System.out.println(json);
        LessonRequest lessonRequest = deserializer.deserializeLesson(json);
        dao.saveLessonRequest(lessonRequest);

        lessonLogger.debug(lessonRequest.getStudentID() + "Sendts request to " + lessonRequest.getTutorID());

        Gson gson = new Gson();
        return ResponseEntity.ok()
                .body(gson.toJson(gson.toJson("Request Sended"),String.class));
    }

    @GetMapping("/loadMyRequests")
    public Object[] loadRequests(@RequestParam(value = "username")String username){
        return dao.findNewByUser(username).toArray();
    }

    @PostMapping("/rejectRequest/{id}")
    public void rejectRequest(@PathVariable(value = "id") int id){
        dao.changeStatus(id, LessonRequestStatus.Rejected);
        System.out.println("rejected");
    }

    @PostMapping("/approveRequest/{id}")
    public void approve(@PathVariable(value = "id") int id){
        dao.changeStatus(id, LessonRequestStatus.Approved);
        System.out.println("approved");
    }


}
