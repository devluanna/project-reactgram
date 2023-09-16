package com.react.controller;

import com.react.domain.model.PhotoUser.*;

import com.react.domain.model.User.Dashboard;
import com.react.domain.model.User.UserAccount;
import com.react.domain.repository.*;
import com.react.domain.service.DashboardService;
import com.react.domain.service.ImageUploadService;
import com.react.domain.service.PhotoService;
import com.react.domain.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Controller
@ResponseBody
@RestController
@RequestMapping("/")
public class PhotoController {
    private final UserAccountService userAccountService;

    @Autowired
    public PhotoController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Value("${app.path.arquivos.default}")
    private String dashboardUploadPath;
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png");
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private PhotoPostedRepository photoPostedRepository;
    @Autowired
    DashboardRepository dashboardRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private TagsRepository tagsRepository;


    @Autowired
    private Dashboard dashboard;


    @GetMapping("/photos/{idPhotoPosted}")
    public ResponseEntity<PhotoPostedWithComments> getPhotoWithComments(@PathVariable Long idPhotoPosted) {
        PhotoPosted photoPosted = photoService.findByIdPhotoPosted(idPhotoPosted);

        if (photoPosted != null) {
            List<CommentsPhoto> comments = commentsRepository.findByIdPhotoPosted(idPhotoPosted);

            PhotoPostedWithComments photoWithComments = new PhotoPostedWithComments();
            photoWithComments.setPhotoPosted(photoPosted);
            photoWithComments.setComments(comments);

            return ResponseEntity.ok(photoWithComments);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/photoslikes/{idPhotoPosted}")
    public ResponseEntity<PhotoPostedWithLikes> getPhotoWithLikes(@PathVariable Long idPhotoPosted) {
        PhotoPosted photoPosted = photoService.findByIdPhotoPosted(idPhotoPosted);

        if (photoPosted != null) {
            List<LikesPhoto> likes = likesRepository.findByIdPhotoPosted(idPhotoPosted);


            PhotoPostedWithLikes photoPostedWithLikes = new PhotoPostedWithLikes();
            photoPostedWithLikes.setPhotoPosted(photoPosted);
            photoPostedWithLikes.setLikes(likes);

            return ResponseEntity.ok(photoPostedWithLikes);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/photostags/{idPhotoPosted}")
    public ResponseEntity<PhotoWithTags> getPhotoWithTags(@PathVariable Long idPhotoPosted) {
        PhotoPosted photoPosted = photoService.findByIdPhotoPosted(idPhotoPosted);

        if (photoPosted != null) {
            List<TagsPhoto> tags = tagsRepository.findByIdPhotoPosted(idPhotoPosted);


            PhotoWithTags photoWithTags = new PhotoWithTags();
            photoWithTags.setPhotoPosted(photoPosted);
            photoWithTags.setTags(tags);

            return ResponseEntity.ok(photoWithTags);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


    @GetMapping("/dashboard/{idDashboard}")
    public ResponseEntity<Dashboard> getDashboard(@PathVariable Long idDashboard, UserAccount userAccount) {
        Dashboard dashboard = dashboardService.findByIdDashboard(idDashboard);

        if (dashboard != null) {
            String user = dashboard.getUser();

            if (user != null) {
                String username = user.intern();
                List<PhotoPosted> photos = photoPostedRepository.findByIdDashboard(idDashboard);
                dashboard.setPhotos(photos);
                dashboard.setUser(username);
            }

            return ResponseEntity.ok(dashboard);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // *** POSTS *** \\

    @PostMapping("/{idPhotoPosted}/likes")
    public ResponseEntity addLikeToPhoto(@PathVariable Long idPhotoPosted, @Valid Long idLikes) {
        UserAccount authenticatedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAccount user = userAccountService.findById(authenticatedUser.getId());

        Long photoId = idPhotoPosted;
        Optional<PhotoPosted> optionalPhoto = photoPostedRepository.findByIdPhotoPosted(photoId);

        if (optionalPhoto.isPresent()) {
            PhotoPosted photo = optionalPhoto.get();


            LikesPhoto likes = new LikesPhoto();
            likes.setIdPhotoPosted(photo.getIdPhotoPosted());
            likes.setLikedBy(user.getUsername());
            likes.setIdUserLiked(user.getId());
            likes.setProfileImage(user.getProfileImage());
            photo.addLike(likes);

            likesRepository.save(likes);
            return ResponseEntity.ok(likes);

        } else {
            return ResponseEntity.status(500).body("Photo not found");
        }

    }

    @PostMapping("/addtags/{idPhotoPosted}")
    public ResponseEntity createTag(@PathVariable Long idPhotoPosted, @RequestBody @Valid String name, Long idTag) {
        try {
            UserAccount authenticatedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserAccount user = userAccountService.findById(authenticatedUser.getId());

            Long photoId = idPhotoPosted;
            Optional<PhotoPosted> optionalPhoto = photoPostedRepository.findByIdPhotoPosted(photoId);

            if (optionalPhoto.isPresent()) {
                PhotoPosted photo = optionalPhoto.get();

                TagsPhoto tags = new TagsPhoto();
                //tags.setTagName(tags.getTagName());
                tags.setTagName(name);
                tags.setIdPhotoPosted(photo.getIdPhotoPosted());
                photo.addTags(tags);

                TagsPhoto savedTag = this.tagsRepository.save(tags);

                return ResponseEntity.ok(savedTag);

            } else {
                return ResponseEntity.status(404).body("Photo not found");}

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error!");
        }
    }

    @PostMapping("/{idPhotoPosted}/comments")
    public ResponseEntity addCommentToPhoto(@PathVariable Long idPhotoPosted, @RequestBody @Valid String commentBox, Long idComment) {
        try {
            UserAccount authenticatedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserAccount user = userAccountService.findById(authenticatedUser.getId());


            Long photoId = idPhotoPosted;
            Optional<PhotoPosted> optionalPhoto = photoPostedRepository.findByIdPhotoPosted(photoId);

            if (optionalPhoto.isPresent()) {
                PhotoPosted photo = optionalPhoto.get();

                CommentsPhoto comments = new CommentsPhoto();
                comments.setIdDashboard(photo.getIdDashboard());
                comments.setIdPhotoPosted(photo.getIdPhotoPosted());
                comments.setIdOwnerPhoto(photo.getIdOwnerPhoto());
                comments.setUploadedBy(photo.getUploadedBy());
                comments.setProfileImage(user.getProfileImage());
                comments.setIdUserCommented(user.getId());
                comments.setCommentedBy(user.getUsername());
                comments.setCommentBox(commentBox);
                photo.addComment(comments);

                commentsRepository.save(comments);
                return ResponseEntity.ok(comments);

            } else {
                return ResponseEntity.status(404).body("Photo not found");}

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error!");
        }


    }

    @PostMapping("/upload/{id}")
    public ResponseEntity uploadToDashboard(@PathVariable Long id, @Valid @RequestParam("file") MultipartFile file, @RequestParam("description")
    String description, Long idPhotoPosted) {
        try {
            UserAccount authenticatedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserAccount user = userAccountService.findById(authenticatedUser.getId());

            if (!user.getId().equals(id)) {
                throw new IllegalArgumentException("Access denied!");
            }


            if (!ALLOWED_IMAGE_TYPES.contains(file.getContentType())) {
                return ResponseEntity.status(400).body("Unsupported file type");
            }

            String fileName = file.getOriginalFilename();
            String filePath = dashboardUploadPath + File.separator + fileName;

            String newImagePath = imageUploadService.uploadImage(file, filePath);
            Dashboard dashboard = user.getDashboard();

            PhotoPosted responseDTO = new PhotoPosted(newImagePath);
            dashboard.addPhoto(responseDTO);

            PhotoPosted photo = new PhotoPosted(newImagePath);
            photo.setIdDashboard(user.getDashboard().getIdDashboard());
            photo.setIdOwnerPhoto(user.getId());
            photo.setProfileImage(user.getProfileImage());
            photo.setUploadedBy(user.getUsername());
            photo.getImagePath();
            photo.setImagePath(newImagePath);
            photo.setDescription(description);


            photoPostedRepository.save(photo);
            return ResponseEntity.ok(photo);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error!");
        }
    }

    // ** UPDATES ** \\

    @PutMapping("/update/photo/{idPhotoPosted}")
    public ResponseEntity updatePhoto(@PathVariable @Valid Long idPhotoPosted, String description) {
        try {
            UserAccount authenticatedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserAccount user = userAccountService.findById(authenticatedUser.getId());

            Long photoId = idPhotoPosted;
            Optional<PhotoPosted> optionalPhoto = photoPostedRepository.findByIdPhotoPosted(photoId);

            if (optionalPhoto.isPresent()) {
                PhotoPosted photo = optionalPhoto.get();

                photo.setDescription(description);
                PhotoPosted update = photoService.update(photo);

                return ResponseEntity.ok(update);
            } else {
                return ResponseEntity.status(404).body("Photo not found");}

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Photo not found.");
        }
    }

    // * DELETES * \\
    @DeleteMapping("/photo/{idPhotoPosted}")
    public ResponseEntity deletePhoto(@PathVariable @Valid Long idPhotoPosted) {
        try {
            UserAccount authenticatedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserAccount user = userAccountService.findById(authenticatedUser.getId());

            PhotoPosted photo = photoService.findByIdPhotoPosted(idPhotoPosted);

            if (authenticatedUser.getId().equals(photo.getIdPhotoPosted())) {
                photoService.deletePhoto(photo);
                return ResponseEntity.ok("The photo has been successfully deleted!");
            } else {
                return ResponseEntity.status(403).body("Unauthorized to delete this photo.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Photo not found.");
        }
    }

    @DeleteMapping("/commentdel/{idComment}")
    public ResponseEntity deleteComment(@PathVariable @Valid Long idComment) {
        try {
            UserAccount authenticatedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserAccount user = userAccountService.findById(authenticatedUser.getId());

            CommentsPhoto comments = photoService.findByIdComment(idComment);

            if (authenticatedUser.getId().equals(comments.getIdUserCommented()) || authenticatedUser.getId().equals(comments.getIdPhotoPosted())) {
                photoService.deleteComment(comments);
                return ResponseEntity.ok("The comment has been successfully deleted!");
            } else {
                return ResponseEntity.status(403).body("Unauthorized to delete this comment.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Comment not found.");
        }

    }

    @DeleteMapping("/likesdel/{idLikes}")
    public ResponseEntity deleteLikes(@PathVariable @Valid Long idLikes) {
        try {
            UserAccount authenticatedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserAccount user = userAccountService.findById(authenticatedUser.getId());

            LikesPhoto likes = photoService.findByIdLikes(idLikes);

            if (authenticatedUser.getId().equals(likes.getLikedBy())){
                photoService.deleteLikes(likes);
                return ResponseEntity.ok("The like has been successfully deleted!");
            } else {
                return ResponseEntity.status(403).body("Unauthorized to delete this like.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Likes not found.");
        }
    }
}