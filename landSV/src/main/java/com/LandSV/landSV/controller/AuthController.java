package com.LandSV.landSV.controller;

import com.LandSV.landSV.dto.LoginRequest;
import com.LandSV.landSV.dto.TokenRequest;
import com.LandSV.landSV.entity.CustomizedUserDetails;
import com.LandSV.landSV.entity.User;
import com.LandSV.landSV.service.JwtService;
import com.LandSV.landSV.service.MailService;
import com.LandSV.landSV.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.util.Utils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MailService mailService;
    private final UserService userService;

    @Value("${app.google.client_id}")
    private String clientId;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, MailService mailService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.mailService = mailService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        CustomizedUserDetails ud = (CustomizedUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(ud);

        return ResponseEntity.ok().body(Map.of(
                "status", 200,
                "token", token,
                "user", Map.of(
                        "id", ud.getId(),
                        "username", ud.getUsername(),
                        "email", ud.getEmail(),
                        "profileImage", ud.getProfileImage() != null ? ud.getProfileImage() : ""
                )
        ));
    }

    @PostMapping("/google/login")
    public ResponseEntity<?> googleLogin (@RequestBody TokenRequest request) throws GeneralSecurityException, IOException {
        String idTokenString = request.getToken();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                Utils.getDefaultTransport(),
                Utils.getDefaultJsonFactory()
        ).setAudience(Collections.singletonList(clientId)).build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        GoogleIdToken.Payload payload = idToken.getPayload();

        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String picture = (String) payload.get("picture");

        CustomizedUserDetails ud = userService.loginGoogle(email, name, picture);

        String token = jwtService.generateToken(ud);

        return ResponseEntity.ok().body(Map.of(
                "status", 200,
                "token", token,
                "user", Map.of(
                        "id", ud.getId(),
                        "username", ud.getUsername(),
                        "email", ud.getEmail(),
                        "profileImage", ud.getProfileImage() != null ? ud.getProfileImage() : ""
                )
        ));
    }

    @PostMapping("/createVerificationCode")
    public ResponseEntity<?> createCode (@RequestParam String email) throws IOException, MessagingException {
        System.out.println(email);
        mailService.createVerificationCode(email);
        System.out.println("se envio");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", HttpStatus.CREATED,
                "message", "Code sent successfully"
        ));
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCode (@RequestParam String email, @RequestParam String code) throws IOException, MessagingException {
        mailService.verifyCode(email, code);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", HttpStatus.OK,
                "message", "Code verified successfully"
        ));
    }
}
