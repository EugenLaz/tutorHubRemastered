package TutorHub.Configuration.Security;

import TutorHub.Service.Data.Repository.UserRepository;
import TutorHub.Service.Data.UserDaoService;
import TutorHub.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private Converter<User, UserDetails> userToDetailsConverter;

    @Autowired UserDaoService userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userToDetailsConverter.convert(userDao.getByUserName(username));
    }
}
