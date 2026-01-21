package com.LandSV.landSV.service;

import com.LandSV.landSV.entity.VerificationCode;
import com.LandSV.landSV.exceptions.DuplicatedException;
import com.LandSV.landSV.exceptions.ResourceNotFoundException;
import com.LandSV.landSV.repository.UserRepository;
import com.LandSV.landSV.repository.VerificationCodeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class MailService {
    private final VerificationCodeRepository verificationCodeRepository;
    private UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String serveEmail;

    public MailService(VerificationCodeRepository verificationCodeRepository, UserRepository userRepository, JavaMailSender mailSender) {
        this.verificationCodeRepository = verificationCodeRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    public String createCode () {
        Random rmd = new Random();
        int code = 000000 + rmd.nextInt(100000, 999999);

        return String.valueOf(code);
    }

    public void createVerificationCode(String email) {
        try {
            if (userRepository.existsByEmail(email)) {
                throw new DuplicatedException("The email is already registered");
            }

            String code = createCode();
            VerificationCode verificationCode = new VerificationCode(code, email);
            verificationCodeRepository.save(verificationCode);

            String htmlContent = """
                   <html>
                       <body style="font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 20px;">
                           <div style="background-color: white; border-radius: 10px; padding: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                               <h2 style="color: #4CAF50;">¡Hola %s! 👋</h2>
                               <p style="font-size: 16px; color: #333;">Tu código de verificación es <b>%s</b></p>
                               <p style="font-size: 14px; color: gray;">Gracias por usar nuestros servicios.</p>
                           </div>
                       </body>
                   </html>
                   """.formatted(email, code);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setTo(email);
            helper.setText(htmlContent, true);
            helper.setFrom(serveEmail);
            helper.setSubject("Código de verificación");
            mailSender.send(message);

        } catch (DuplicatedException e) {
            throw e;

        } catch (MessagingException e) {
            throw new RuntimeException("Servicio no disponible.", e);

        } catch (DataAccessException e) {
            throw new RuntimeException("Servicio no disponible.", e);

        } catch (Exception e) {
            throw new RuntimeException("Intentelo mas tarde.", e);
        }
    }


    public void verifyCode(String email, String code) {
        String cleanEmail = email.trim().toLowerCase();
        String cleanCode = code.trim();

        VerificationCode verificationCode = verificationCodeRepository
                .findByEmailAndCodeIgnoreCase(cleanEmail, cleanCode)
                .orElseThrow(() -> new ResourceNotFoundException("Code is not valid"));

        if (verificationCode.getExpiration().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("The code has expired");
    }

}
