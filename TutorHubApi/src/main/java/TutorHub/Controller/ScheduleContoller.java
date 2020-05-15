package TutorHub.Controller;

import TutorHub.Service.Data.LessonRequestDaoService;
import TutorHub.Service.Data.UserDaoService;
import TutorHub.model.LessonRequest;
import TutorHub.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class ScheduleContoller {

    @Autowired
    LessonRequestDaoService lessonDao;

    @Autowired
    UserDaoService userDao;

    @GetMapping(value = "/laodSchedule")
    public Object[] getLessonsInfo(@RequestParam String username){
        List<Response> result = new ArrayList<>();
        String message;
         lessonDao.findAllApprovedAffterDate(username, Date.valueOf(LocalDate.now()))
                 .forEach(entity -> result.add(new Response(
                         generateResponse(username, entity),
                         LocalDateTime.of(entity.getDate().toLocalDate()
                         ,entity.getTime().toLocalTime()))));
         return result.toArray();
    }

    private String generateResponse(String username, LessonRequest request){
        User user = userDao.getByUserName(username);
        StringBuilder message = new StringBuilder("A lesson with ");

        if (user.getUserRoles().contains("tutor")) {
            message.append( userDao.getByUserName(request.getStudentID()).getName() );
        }
        else{
            message.append( userDao.getByUserName(request.getTutorID()) .getName());
        }
        System.out.println(message.toString());
        return message.toString();
    }

    class Response{
        public String getTitle() {
            return title;
        }

        public LocalDateTime getStart() {
            return start;
        }

        Response(String title, LocalDateTime start){
            this.title = title;
            this.start = start;
        }
        String title;
        LocalDateTime start;
    }
}
