package com.react.controller;

import com.react.domain.model.User.Dashboard;
import com.react.domain.model.User.ResponseDTO.*;
import com.react.domain.model.User.UserAccount;
import com.react.domain.repository.DashboardRepository;
import com.react.domain.repository.UserAccountRepository;
import com.react.domain.service.ImageUploadService;
import com.react.domain.service.UserAccountService;
import com.react.infra.security.TokenService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;


@ResponseBody
@Controller
@RestController
@RequestMapping("/")
public class UserAccountController {

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png");
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountController.class);

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    private final UserAccountService userAccountService;
    @Autowired
    DashboardRepository dashboardRepository;
    @Value("${app.path.arquivos.default}")
    private String defaultUploadPath;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ImageUploadService imageUploadService;


    @GetMapping("/users")
    public ResponseEntity<List<UserAccount>> getAllUsers() {
        List<UserAccount> user = userAccountService.getAllUsers();

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserAccount> getUserById(@PathVariable Long id) {
        UserAccount user = userAccountService.getUserById(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO data) {
        var auth = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = manager.authenticate(auth);
        var token = tokenService.generateToken((UserAccount) authentication.getPrincipal());
        return ResponseEntity.ok(new ResponseTokenDTO(token));
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody RegisterDTO data) {
        String standardizedUsername = data.getLogin().toLowerCase();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Dashboard dashboard = new Dashboard();

        UserAccount response = new UserAccount(standardizedUsername, data.email(), data.name(), encryptedPassword, "USERGRAM", dashboard);
        dashboard.setUser(standardizedUsername.intern());

        UserAccount createdUser = userAccountService.create(response);

        this.userAccountRepository.save(createdUser);

        return ResponseEntity.ok(createdUser);
    }


    @PutMapping("/update/profile/{id}")
    public ResponseEntity<UserAccount> update(@PathVariable Long id, @RequestBody @Valid UpdateDTO updateDTO) {

        UserAccount authenticatedUser = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAccount authenticatedAccount = userAccountService.findById(authenticatedUser.getId());

        if (!authenticatedAccount.getId().equals(id)) {
            throw new IllegalArgumentException("Access denied!");
        }

        String standardizedUsername = updateDTO.getLogin().toLowerCase();

        authenticatedAccount.setLogin(standardizedUsername);
        authenticatedAccount.setName(updateDTO.getName());
        authenticatedAccount.setBio(updateDTO.getBio());

        UserAccount update = userAccountService.update(authenticatedAccount);

        return ResponseEntity.ok(update);
    }

    @PutMapping("/update/profile/image/{id}")
    public ResponseEntity<String> updateProfileImage(@PathVariable Long id, @Valid @RequestParam("file") MultipartFile file) {
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
            String filePath = defaultUploadPath + File.separator + fileName;

            String newImagePath = imageUploadService.uploadImage(file, filePath);

            user.setProfileImage(newImagePath);
            userAccountService.update(user);

            return ResponseEntity.ok("Profile image successfully updated!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error!");
        }
    }

    @PutMapping("/update/profile/password/{id}")
    public ResponseEntity<UserAccount> updatePass(@PathVariable Long id, @RequestBody @Valid PasswordDTO passwordDTO) {

        UserAccount authenticatedPassword = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAccount authenticatedUserPass = userAccountService.findById(authenticatedPassword.getId());

        authenticatedUserPass.setPassword(passwordDTO.getPassword());
        authenticatedUserPass.setConfirmPassword(passwordDTO.getConfirmPassword());

        UserAccount updatePass = userAccountService.updatePass(authenticatedUserPass);
        return ResponseEntity.ok(updatePass);
    }


}