package TutorHub.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class UserRole {
    @Column(name = "role_name", unique = true)
    String roleName;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany(mappedBy = "userRoles")
    private Set<User> roles = new HashSet<>();

    public UserRole(String role) {
        roleName = role;
    }

    public UserRole() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return roleName.equals(userRole.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

    @Override
    public String toString() {
        return roleName;
    }

}
