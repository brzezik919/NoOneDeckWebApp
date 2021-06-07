package io.github.brzezik919.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String nickname;
    private String email;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_team")
    private Team team;
    @Column(name = "status_team")
    private boolean status;
    @Column(nullable = true, length = 64)
    private String avatar;

    public User() {
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getLogin() {return login;}

    public void setLogin(String login) {this.login = login;}

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Transient
    public String getPhotosImagePath() {
        if (avatar == null) return null;
        return "/photos/" + id + "/" + avatar;
    }
}