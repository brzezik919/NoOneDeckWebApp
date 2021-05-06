package io.github.brzezik919.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Login's must be not empty")
    private String login;
    private String role;
    @NotBlank(message = "Password's must be not empty")
    private String password;
    private String teamId;

    public User() {
    }

    public int getId() {return id;}

    void setId(int id) {this.id = id;}

    public String getLogin() {return login;}

    void setLogin(String login) {this.login = login;}

    public String getRole() {
        return role;
    }

    void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {return password;}

    void setPassword(String password) {this.password = password;}

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
