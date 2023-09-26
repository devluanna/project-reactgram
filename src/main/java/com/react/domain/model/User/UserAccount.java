package com.react.domain.model.User;


import com.react.domain.model.PhotoUser.PhotoPosted;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "tb_user_account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String profileImage;
    private String bio;
    private UserRole role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhotoPosted> photos = new ArrayList<>();

    public UserAccount(String login, String email, String name, String password, String role, Dashboard dashboard){
        this.login = login;
        this.email = email;
        this.name = name;
        this.password = password;
        this.dashboard = dashboard;
        this.role = UserRole.valueOf(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USERGRAM"));
    }

    public Long getId() { return id; }
    public void setId (Long id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getBio() {return bio;}
    public void setBio(String bio) { this.bio = bio;}

    public String getProfileImage() {return profileImage;}
    public void setProfileImage(String profileImage) { this.profileImage = profileImage;}

    public Dashboard getDashboard() {
        return dashboard;
    }

    // Security Details //
    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<PhotoPosted> getPhotos() {
        return photos;
    }
    public void setPhotos(List<PhotoPosted> photos) {
        this.photos = photos;
    }
}
