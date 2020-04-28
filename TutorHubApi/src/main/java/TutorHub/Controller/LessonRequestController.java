package TutorHub.Controller;


import TutorHub.Service.Data.LessonRequestDaoService;
import TutorHub.Service.Data.UserDaoService;
import TutorHub.Service.Funds.PaymentProcessor;
import TutorHub.Service.JsonParsing.MyJsonParser;
import TutorHub.model.LessonRequest;
import TutorHub.model.LessonRequestStatus;
import TutorHub.model.User;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


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
    PaymentProcessor paymentProcessor;

    @PostMapping("/sendRequest")
    public ResponseEntity<String> sendRequest(@RequestBody String json) {
        LessonRequest lessonRequest = deserializer.deserializeLesson(json);
        Gson gson = new Gson();
        try {
            paymentProcessor.withdrawFunds(lessonRequest.getStudentID(), lessonRequest.getPricePerHour());
            requestDao.saveLessonRequest(lessonRequest);
        }catch (RuntimeException e){
            return ResponseEntity.accepted()
                    .body(gson.toJson(gson.toJson("Not enough funds"), String.class));
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
        LessonRequest request = requestDao.findById(id);
        paymentProcessor.depositFunds(request.getStudentID(),request.getPricePerHour());
        requestDao.changeStatus(id, LessonRequestStatus.Rejected);
    }

    @PostMapping("/approveRequest/{id}")
    public void approve(@PathVariable(value = "id") int id) {
        LessonRequest request = requestDao.findById(id);
        paymentProcessor.depositFunds(request.getTutorID(),request.getPricePerHour());
        requestDao.changeStatus(id, LessonRequestStatus.Approved);
    }

}
