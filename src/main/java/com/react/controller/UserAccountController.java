package com.react.controller;

import com.react.domain.model.PhotoUser.PhotoPosted;
import com.react.domain.model.User.Dashboard;
import com.react.domain.model.User.ResponseDTO.*;
import com.react.domain.model.User.UserAccount;
import com.react.domain.repository.DashboardRepository;
import com.react.domain.repository.PhotoPostedRepository;
import com.react.domain.repository.UserAccountRepository;
import com.react.domain.service.DashboardService;
import com.react.domain.service.ImageUploadService;
import com.react.domain.service.PhotoService;
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
import org.springframework.security.core.userdetails.User;
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
    @Autowired
    private PhotoPostedRepository photoPostedRepository;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private PhotoService photoService;


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

        //Dashboard dashPhotos = dashboardService.findByIdDashboard(id);
        UserAccount user = userAccountService.getUserById(id);

        if (user == null) {


            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO data, UserAccount userAccount, Dashboard dashboard) {
        var auth = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = manager.authenticate(auth);
        var token = tokenService.generateToken((UserAccount) authentication.getPrincipal());

        UserAccount authenticatedUser = (UserAccount) authentication.getPrincipal();
        Long userId = authenticatedUser.getId();

        Long dashboardId = authenticatedUser.getDashboard().getIdDashboard();

       // var userId = ((UserAccount) authentication.getPrincipal()).getId();

       return ResponseEntity.ok(new ResponseTokenDTO(dashboardId, userId, token));

    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody RegisterDTO data) {
        String standardizedUsername = data.getLogin().toLowerCase();
        String password = data.getPassword();

        // Validar a senha
        if (!userAccountService.isValidPassword(password)) {
            return ResponseEntity.badRequest().body("The password must be at least 8 characters long, including uppercase letters, lowercase letters, numbers and special characters.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        Dashboard dashboard = new Dashboard();

        UserAccount response = new UserAccount(standardizedUsername, data.email(), data.name(), encryptedPassword, "USERGRAM", dashboard);
        dashboard.setUser(standardizedUsername.intern());

        UserAccount createdUser = userAccountService.create(response);

        this.userAccountRepository.save(createdUser);

        return ResponseEntity.ok(createdUser);
    }


    @PutMapping("/update/profile/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid UpdateDTO updateDTO) {
        try {
        UserAccount user = userAccountService.findById(id);

        if (!user.getId().equals(id)) {
            throw new IllegalArgumentException("Access denied!");
        }

        String standardizedUsername = updateDTO.getLogin().toLowerCase();

        user.setLogin(standardizedUsername);
        user.setName(updateDTO.getName());
        user.setBio(updateDTO.getBio());

        UserAccount update = userAccountService.update(user);

        return ResponseEntity.ok(update);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal server error!");
        }
    }

    @PutMapping("/update/profile/image/{id}")
    public ResponseEntity<String> updateProfileImage(@PathVariable Long id, @Valid @RequestParam("file") MultipartFile file) {
        try {
                UserAccount user = userAccountService.findById(id);


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

        UserAccount user = userAccountService.findById(id);

        user.setPassword(passwordDTO.getPassword());
        user.setConfirmPassword(passwordDTO.getConfirmPassword());

        UserAccount updatePass = userAccountService.updatePass(user);
        return ResponseEntity.ok(updatePass);
    }


}