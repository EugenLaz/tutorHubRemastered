package TutorHub.Service.JsonParsing.Desirializers;

import TutorHub.model.User;
import TutorHub.model.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserJsonDeserializer extends StdDeserializer<User> {

    BCryptPasswordEncoder encoder;

    public UserJsonDeserializer() {
        super(User.class);
    }

    @Override
    public User deserialize(com.fasterxml.jackson.core.JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        encoder=new BCryptPasswordEncoder();
        User result =new User();
        result.setUsername( node.get("username").asText() );
        result.setEmail( node.get("email").asText() );
        result.setPassword( encoder.encode(node.get("password").asText()) );
        result.setRole(new UserRole( node.get("role").asText() ));
        return result;
    }



}
