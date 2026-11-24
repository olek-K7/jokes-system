package app.dev.jokes_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

// Getter + Setter from lombok
@Getter
@Setter
@NoArgsConstructor
// Tell Spring that this a JPA entity and we'll be mapped to the "app_users" table
@Entity
@Table(name="app_users")
public class AppUser{
    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private boolean locked;

    // Lombok annotation to exclude this field from toString method
    // Including it could cause recursion or bcs of an issue with lazy data access
    @ToString.Exclude
    // CascadeType.REMOVE - means if the User is deleted, also delete its jokes
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "appUser")
    // @JSONIgnore
    private List<Joke> jokes;

    // Constructor
    public AppUser(String email, String firstname, String lastname, String password, UserRole role, boolean locked){
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
        this.locked = locked;
    }
}
