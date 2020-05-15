package TutorHub.Service.Data.Repository;

import TutorHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM users,user_role,roles Where user_role.username = users.username AND user_role.role_id = roles.id AND roles.role_name=\"tutor\"", nativeQuery = true)
    List<User> getAllTutors();

    @Query(value = "SELECT -1 FROM USERS u WHERE u.username= :username " +
            "UNION select 0 FROM users u Where u.email = :email", nativeQuery = true)
    List<Integer> checkIfExists(@Param("username") String name, @Param("email") String email);

}
