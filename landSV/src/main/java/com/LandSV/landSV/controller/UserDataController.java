package com.LandSV.landSV.controller;

import com.LandSV.landSV.entity.CustomizedUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/verifyData")
public class UserDataController {
    @GetMapping("/verifyToken")
    public ResponseEntity<?> verifyToken (@AuthenticationPrincipal CustomizedUserDetails ud) {
        return ResponseEntity.ok().body(Map.of(
                "status", 200,
                "user", Map.of(
                        "id", ud.getId(),
                        "username", ud.getUsername(),
                        "email", ud.getEmail(),
                        "profileImage", ud.getProfileImage() != null ? ud.getProfileImage() : ""
                )
        ));
    }
}
