package com.LandSV.landSV.service;

import com.LandSV.landSV.dto.EditUserRequest;
import com.LandSV.landSV.dto.UserDTO;
import com.LandSV.landSV.dto.UserRegisterRequest;
import com.LandSV.landSV.entity.CustomizedUserDetails;
import com.LandSV.landSV.entity.User;
import com.LandSV.landSV.entity.VerificationCode;
import com.LandSV.landSV.exceptions.DuplicatedException;
import com.LandSV.landSV.exceptions.ResourceNotFoundException;
import com.LandSV.landSV.mapper.UserMapper;
import com.LandSV.landSV.repository.UserRepository;
import com.LandSV.landSV.repository.VerificationCodeRepository;
import com.LandSV.landSV.utils.UserProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final CloudinaryService cloudinaryService;
    private final VerificationCodeRepository verificationCodeRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, CloudinaryService cloudinaryService, VerificationCodeRepository verificationCodeRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.cloudinaryService = cloudinaryService;
        this.verificationCodeRepository = verificationCodeRepository;
    }

    @Transactional
    public UserDTO registerUser (UserRegisterRequest request) throws IOException {
        if(userRepository.existsByUsername(request.getUsername())) throw new DuplicatedException("The username is already in use");
        if(userRepository.existsByEmail(request.getEmail())) throw new DuplicatedException("The email is already in use");
        VerificationCode verificationCode = verificationCodeRepository
                .findByEmailAndCodeIgnoreCase(request.getEmail().trim(), request.getCode().trim())
                .orElseThrow(() -> new ResourceNotFoundException("The verification code is not valid"));

        if (verificationCode.getExpiration().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("The code has expired, start again");

        User user = new User();

        user.setEmail(request.getEmail());

        System.out.println(request.getImage());
        if(request.getImage() != null) {
            String image = cloudinaryService.uploadImage(request.getImage());

            user.setProfileImage(image);
        } else {
            user.setProfileImage(null);
        }

        user.setUsername(request.getUsername());
        user.setProvider(UserProvider.LOCAL);

        user.setPassword(hashPassword(request.getPassword()));

        User savedUser = userRepository.save(user);

        return UserMapper.toUseDTO(savedUser);
    }

    public String hashPassword (String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean passwordMatches (String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }

    public UserDTO editUser (EditUserRequest request) throws Exception {
        User user = userRepository.findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if(user.getProvider() == UserProvider.GOOGLE) throw new IllegalArgumentException("Esta cuenta no se puede editar");
        if(!passwordMatches(request.getPassword(), user.getPassword())) throw new IllegalArgumentException("Contrasena incorrecta");
        if(user.getUsername().equals(request.getUsername())) throw new DuplicatedException("El usuario ingresado es el actual");
        if(userRepository.existsByUsername(request.getUsername())) throw new DuplicatedException("El nombre de usuario no esta disponible");

        if(request.getUsername() != null && !request.getUsername().trim().isEmpty()) {
            user.setUsername(request.getUsername());
        }

        if(request.getImage() != null) {
            try {
                String newImage = cloudinaryService.uploadImage(request.getImage());

                user.setProfileImage(newImage);
            } catch (IOException ex) {
                throw new Exception("Error en la edicion de usuario.");
            }
        }

        User edited = userRepository.save(user);

        return UserMapper.toUseDTO(edited);
    }

    public CustomizedUserDetails loginGoogle (String email, String name, String image) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = new User();
        User saved;

        System.out.println(name);
        System.out.println(email);

        if(optionalUser.isEmpty()) {
            if(userRepository.existsByUsername(name)) throw new DuplicatedException("El username ya esta ocupado");
            user.setUsername(name);
            user.setEmail(email);
            user.setProfileImage(image);
            user.setProvider(UserProvider.GOOGLE);
            saved = userRepository.save(user);
        } else {
            saved = optionalUser.get();
        }

        CustomizedUserDetails userDetails = new CustomizedUserDetails(
                saved.getPassword(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getId().toString(),
                saved.getProfileImage(),
                null
        );

        return userDetails;
    }
}
