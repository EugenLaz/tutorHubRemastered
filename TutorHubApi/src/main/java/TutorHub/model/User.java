package TutorHub.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String email;
    private String name;
    private String password;
    private String grp;
    private BigInteger balance;
    private String personalInfo;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
    }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> userRoles = new HashSet<>();



    public User() {
        userRoles.add(new UserRole("default"));
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userRoles, user.userRoles) &&
                Objects.equals(email, user.email) &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(grp, user.grp) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "userRoles=" + userRoles +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", grp='" + grp + '\'' +
                ", balance=" + balance +
                ", personalInfo='" + personalInfo + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(UserRole userRole) {
        this.userRoles.add(userRole);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return grp;
    }

    public void setGroup(String group) {
        this.grp = group;
    }
}
