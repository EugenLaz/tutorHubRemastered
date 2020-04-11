package TutorHub.Service.Data.Repository;

import TutorHub.model.LessonRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LessonRequestRepository extends JpaRepository<LessonRequest, Long> {
    @Query("select b from LessonRequest b where (b.tutorID = :username or b.studentID=:username) and b.status='New'")
    List<LessonRequest> findAllByUser(@Param("username") String username);

    @Query("select b from LessonRequest b where b.date = :date and b.studentID= :username and b.status='Approved'")
    List<LessonRequest> findApprovedByDate(@Param("username") String username, @Param("date") Date date);


}
