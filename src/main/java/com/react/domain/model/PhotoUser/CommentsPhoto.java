package com.react.domain.model.PhotoUser;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "tb_comments")
@NoArgsConstructor
@JsonPropertyOrder({ "idDashboard", "idPhotoPosted", "idOwnerPhoto", "uploadedBy", "idComment", "idUserCommented", "commentedBy", "profileImage", "commentBox" })
public class CommentsPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment;
    private Long idDashboard;
    private Long idPhotoPosted;
    private String uploadedBy;
    private String commentedBy;
    private Long id;
    private String profileImage;
    private String commentBox;
    private Long idOwnerPhoto;


    public String getCommentBox() {
        return commentBox;
    }
    public void setCommentBox(String commentBox) {
        this.commentBox = commentBox;
    }

    public Long getIdUserCommented() { return id; }
    public void setIdUserCommented(Long id) { this.id = id; }

    public Long getIdOwnerPhoto() { return idOwnerPhoto; }
    public void setIdOwnerPhoto (Long idOwnerPhoto) { this.idOwnerPhoto = idOwnerPhoto; }

    public Long getIdDashboard() {
        return idDashboard;
    }
    public void setIdDashboard(Long idDashboard) {
        this.idDashboard = idDashboard;
    }

    public Long getIdPhotoPosted() {
        return idPhotoPosted;
    }
    public void setIdPhotoPosted(Long idPhotoPosted) {
        this.idPhotoPosted = idPhotoPosted;
    }

    public Long getIdComment() {
        return idComment;
    }
    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }
    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getCommentedBy() {
        return commentedBy;}
    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }

    public String getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}