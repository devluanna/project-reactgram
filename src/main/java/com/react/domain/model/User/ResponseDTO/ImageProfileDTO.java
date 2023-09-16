package com.react.domain.model.User.ResponseDTO;

import org.springframework.web.multipart.MultipartFile;

public record ImageProfileDTO(MultipartFile profileImage) {
    public MultipartFile getProfileImage() {
        return profileImage;
    }
}