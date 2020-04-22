package TutorHub.model;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "requests")
public class LessonRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long requestID;
    private long pricePerHour;
    private String place;
    private Date date;
    private Time time;

    @Override
    public String toString() {
        return "LessonRequest{" +
                "requestID=" + requestID +
                ", pricePerHour=" + pricePerHour +
                ", place='" + place + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", message='" + message + '\'' +
                ", studentID='" + studentID + '\'' +
                ", tutorID='" + tutorID + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    private String message;
    private String studentID;
    private String tutorID;
    private String status;


    public LessonRequest() {
        status = LessonRequestStatus.New.toString();
    }


    public String getTutorID() {
        return tutorID;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setStatus(LessonRequestStatus status) {
        this.status = status.toString();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public long getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(long pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getTutuorID() {
        return tutorID;
    }

    public void setTutuorID(String tutuorID) {
        this.tutorID = tutuorID;
    }
}
