package com.react.domain.model.User;

import com.react.domain.model.PhotoUser.PhotoPosted;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Entity(name = "tb_dashboard")
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDashboard;
    private String login;
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhotoPosted> photos = new ArrayList<>();

    public Dashboard() {
        this.login = login;
        this.idDashboard = idDashboard;}

    public String getUser() {
        return login;
    }
    public void setUser(String login) {
        this.login = login;
    }


    public Long getIdDashboard() { return idDashboard; }
    public void setIdDashboard(Long idDashboard) {
        this.idDashboard = idDashboard;
    }

    public void addPhoto(PhotoPosted photo) {
        photos.add(photo);
    }
    public List<PhotoPosted> getPhotos() {
        return photos;
    }
    public void setPhotos(List<PhotoPosted> photos) {
        this.photos = photos;
    }
}