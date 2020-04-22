package TutorHub.Service.JsonParsing.Desirializers;

import TutorHub.Service.Data.LessonRequestDaoService;
import TutorHub.Service.Data.UserDaoService;
import TutorHub.model.LessonRequest;
import TutorHub.model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class LessonRequestDeserializer extends StdDeserializer<LessonRequest> {

    @Autowired
    LessonRequestDaoService userDao;

    public LessonRequestDeserializer() {
        super(LessonRequest.class);
    }

    @Override
    public LessonRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        LessonRequest result = new LessonRequest();
        result.setDate( Date.valueOf(node.get("date").asText()) );
        result.setMessage( node.get("message").asText() );
        result.setPlace( node.get("place").toString() );
        result.setStudentID( node.get("studentId").asText() );
        result.setTutuorID( node.get("tutorId").asText() );
        result.setPricePerHour( node.get("price").asInt() );
        result.setTime(Time.valueOf(LocalTime.parse(node.get("time").asText())));
        return result;
    }

}
