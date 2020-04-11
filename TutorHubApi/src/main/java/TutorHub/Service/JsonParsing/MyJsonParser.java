package TutorHub.Service.JsonParsing;

import TutorHub.Service.JsonParsing.Desirializers.UserInfoJsonDeserializer;
import TutorHub.Service.JsonParsing.Desirializers.UserJsonDeserializer;
import TutorHub.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
public class MyJsonParser {

    public User deserializeUser(String json, Deserializers deserializerType){
        User user=null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            StdDeserializer<User> deserializer =  deserializerType.getType().getDeclaredConstructor().newInstance();
            module.addDeserializer(User.class, deserializer);
            mapper.registerModule(module);
            user = mapper.readValue(json, User.class);
        } catch (JsonProcessingException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return user;
    }



}
