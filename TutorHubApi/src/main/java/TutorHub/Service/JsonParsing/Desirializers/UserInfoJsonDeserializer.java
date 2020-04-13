package TutorHub.Service.JsonParsing.Desirializers;

import TutorHub.Service.Data.UserDaoService;
import TutorHub.model.User;
import TutorHub.model.UserRole;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

public class UserInfoJsonDeserializer extends StdDeserializer<User> {

    @Autowired
    UserDaoService userDao;

    public UserInfoJsonDeserializer() {
        super(User.class);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        User result = new User();
        System.out.println(node.get("username").asText());
        result.setUsername(node.get("username").asText());
        result.setName( node.get("name").asText() );
        result.setEmail( node.get("email").asText() );
        result.setPersonalInfo(node.get("personalInfo").asText() );
        return result;
    }
}
