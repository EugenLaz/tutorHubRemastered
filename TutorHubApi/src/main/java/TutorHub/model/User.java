package TutorHub.model;


import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal balance;
    private String personalInfo;
    private int rating;
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
        this.rating = 0;
        this.balance = BigDecimal.ZERO;
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
                ", balance=" + balance +
                ", personalInfo='" + personalInfo + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public void merge(User updatingValues) {
        this.setUsername(updatingValues.getUsername());
        this.setPassword(updatingValues.getPassword());
        this.setEmail(updatingValues.getEmail());
        this.setPersonalInfo(updatingValues.getPersonalInfo());
        this.setName(updatingValues.getName());
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null)
            this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null)
            this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null)
            this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null)
            this.password = password;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        if (personalInfo != null)
            this.personalInfo = personalInfo;
    }

    public void updateBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRole(UserRole userRole) {
        if (userRole != null)
            this.userRoles.add(userRole);
    }
}
