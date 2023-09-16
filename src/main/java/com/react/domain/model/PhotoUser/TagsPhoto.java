package com.react.domain.model.PhotoUser;

import jakarta.persistence.*;


@Entity(name = "tb_tags")
public class TagsPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTag;
    private String name;
    private Long idPhotoPosted;


    public Long getIdTag() { return idTag; }
    public void setIdTag (Long idTag) { this.idTag = idTag;}

    public String getTagName() {return name;}
    public void setTagName(String name) { this.name = name;}

    public Long getIdPhotoPosted() {
        return idPhotoPosted;
    }
    public void setIdPhotoPosted(Long idPhotoPosted) {
        this.idPhotoPosted = idPhotoPosted;
    }
}