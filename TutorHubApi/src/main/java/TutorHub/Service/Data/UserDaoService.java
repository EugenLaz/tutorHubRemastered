package TutorHub.Service.Data;


import TutorHub.model.User;

import java.util.List;

public interface UserDaoService {
    User getByUserName(String username);

    List<User> getAll();

    boolean saveUser(User user);

    boolean deleteUser(String username);

    List<User> getAllTutors();

    Integer checkIfUnique(User user);


}
