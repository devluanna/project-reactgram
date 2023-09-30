package com.react.domain.model.PhotoUser;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.util.*;


@Entity(name = "tb_photoposted")
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "idDashboard", "idPhotoPosted", "imagePath", "description", "idOwnerPhoto", "imageProfile", "uploadedBy", "creationDate" })
public class PhotoPosted {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPhotoPosted;
    private Long idOwnerPhoto;
    private String uploadedBy;
    private Long idDashboard;
    private String imagePath;
    private String description;
    private String profileImage;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CommentsPhoto> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LikesPhoto> likes = new ArrayList<>();


    public PhotoPosted(String imagePath) {
        this.imagePath = imagePath;
    }
    public void setImagePath(String newImagePath) {
        this.imagePath = imagePath;
    }
    public String getImagePath() {
        return imagePath;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdPhotoPosted() {
        return idPhotoPosted;
    }
    public void setIdPhotoPosted(Long idPhotoPosted) {
        this.idPhotoPosted = idPhotoPosted;
    }

    public Long getIdOwnerPhoto() { return idOwnerPhoto; }
    public void setIdOwnerPhoto (Long idOwnerPhoto) { this.idOwnerPhoto = idOwnerPhoto; }

    public String getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }
    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Long getIdDashboard() {
        return idDashboard;
    }
    public void setIdDashboard(Long idDashboard) {
        this.idDashboard = idDashboard;
    }

    public void addComment(CommentsPhoto comment) {
        comments.add(comment);
    }
    public List<CommentsPhoto> getComments() {
        return comments;
    }
    public void setComments(List<CommentsPhoto> comments) {
        this.comments = comments;
    }

    public void addLike(LikesPhoto like) {
        likes.add(like);
    }
    public List<LikesPhoto> getLikes() {
        return likes;
    }
    public void setLikes(List<LikesPhoto> likes) {
        this.likes = likes;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreationDate() {
        return creationDate;}
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}