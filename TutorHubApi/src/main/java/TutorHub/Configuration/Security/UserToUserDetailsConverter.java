package TutorHub.Configuration.Security;

import TutorHub.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDetailsConverter implements Converter<User, UserDetails> {
    @Override
    public UserDetails convert(User user) {
        MyUserDetails userDetails = new MyUserDetails();
        if (user != null) {
            userDetails.setUsername(user.getUsername());
            userDetails.setPassword(user.getPassword());
        }
        return userDetails;
    }

}
