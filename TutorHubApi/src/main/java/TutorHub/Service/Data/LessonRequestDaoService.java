package TutorHub.Service.Data;

import TutorHub.model.LessonRequest;
import TutorHub.model.LessonRequestStatus;
import TutorHub.model.User;

import java.util.Date;
import java.util.List;


public interface LessonRequestDaoService {
    List<LessonRequest> findNewByUser(String username);

    void saveLessonRequest(LessonRequest user);

    void changeStatus(long id, LessonRequestStatus status);

    LessonRequest findById(long id);

    List<LessonRequest> findAllApprovedByDate(String username, Date date);
}
