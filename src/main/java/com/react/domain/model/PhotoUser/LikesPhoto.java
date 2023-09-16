package com.react.domain.model.PhotoUser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity(name = "tb_likes")
@NoArgsConstructor
@JsonPropertyOrder({ "idPhotoPosted", "idLikes", "idUserLiked", "likedBy", "profileImage" })
public class LikesPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLikes;
    private String likedBy;
    private String profileImage;
    private Long idPhotoPosted;
    private long idUserLiked;


    public Long getIdLikes() {
        return idLikes;
    }
    public void setIdLikes(Long idLikes) {
        this.idLikes = idLikes;
    }

    public Long getIdUserLiked() {
        return idUserLiked;
    }
    public void setIdUserLiked(Long idUserLiked) {
        this.idUserLiked = idUserLiked;
    }

    public String getLikedBy() {
        return likedBy;
    }
    public void setLikedBy(String likedBy) {
        this.likedBy = likedBy;
    }


    public String getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Long getIdPhotoPosted() {
        return idPhotoPosted;
    }
    public void setIdPhotoPosted(Long idPhotoPosted) {
        this.idPhotoPosted = idPhotoPosted;
    }

}