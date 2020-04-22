package TutorHub.Service.JsonParsing;

import TutorHub.Service.JsonParsing.Desirializers.UserInfoJsonDeserializer;
import TutorHub.Service.JsonParsing.Desirializers.UserJsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public enum UserDeserializers {
    UserInfo(UserInfoJsonDeserializer.class),
    UserCredentials(UserJsonDeserializer.class);

    private final Class<? extends StdDeserializer> type;
    private UserDeserializers(Class<? extends StdDeserializer> type) {
        this.type = type;
    }

    public Class<? extends StdDeserializer> getType(){
        return this.type;
    }
}
