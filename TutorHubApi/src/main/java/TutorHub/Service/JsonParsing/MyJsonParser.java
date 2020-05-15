package TutorHub.Service.JsonParsing;

import TutorHub.Service.JsonParsing.Desirializers.LessonRequestDeserializer;
import TutorHub.model.LessonRequest;
import TutorHub.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class MyJsonParser {


    //When deserializing info to update and registation credentials we need different deserializers
    public User deserializeUser(String json, UserDeserializers deserializerType) {
        User user = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            StdDeserializer<User> deserializer = deserializerType.getType().getDeclaredConstructor().newInstance();
            module.addDeserializer(User.class, deserializer);
            mapper.registerModule(module);
            user = mapper.readValue(json, User.class);
        } catch (JsonProcessingException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return user;
    }

    public LessonRequest deserializeLesson(String json) {
        LessonRequest lessonRequest = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            StdDeserializer<LessonRequest> deserializer = new LessonRequestDeserializer();
            module.addDeserializer(LessonRequest.class, deserializer);
            mapper.registerModule(module);
            lessonRequest = mapper.readValue(json, LessonRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return lessonRequest;
    }

}
