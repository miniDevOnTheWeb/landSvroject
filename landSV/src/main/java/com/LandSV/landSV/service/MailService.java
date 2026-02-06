package com.LandSV.landSV.service;

import com.LandSV.landSV.entity.VerificationCode;
import com.LandSV.landSV.exceptions.DuplicatedException;
import com.LandSV.landSV.exceptions.ResourceNotFoundException;
import com.LandSV.landSV.repository.UserRepository;
import com.LandSV.landSV.repository.VerificationCodeRepository;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class MailService {
    private final WebClient webClient;
    private final VerificationCodeRepository verificationCodeRepository;
    private final UserRepository userRepository;

    @Value("${brevo.api.key}")
    private String apiKey;

    public MailService(
            VerificationCodeRepository verificationCodeRepository,
            UserRepository userRepository
    ) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.brevo.com/v3")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        this.verificationCodeRepository = verificationCodeRepository;
        this.userRepository = userRepository;
    }
    public String createCode () {
        Random rmd = new Random();
        int code = 000000 + rmd.nextInt(100000, 999999);

        return String.valueOf(code);
    }

    private void sendBrevoEmail(String to, String subject, String html) {

        Map<String, Object> body = Map.of(
                "sender", Map.of(
                        "name", "LandSV",
                        "email", "noreply@landsv.app"
                ),
                "to", List.of(Map.of("email", to)),
                "subject", subject,
                "htmlContent", html
        );

        webClient.post()
                .uri("https://api.brevo.com/v3/smtp/email")
                .header("api-key", apiKey)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(resp -> System.out.println("Email enviado OK → " + resp))
                .doOnError(err -> System.err.println("Error enviando email → " + err.getMessage()))
                .block();
    }

    public void createVerificationCode(String email) {

        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedException("The email is already registered");
        }

        System.out.println("Email recibido: " + email);

        String code = createCode();
        System.out.println("Código generado: " + code);

        VerificationCode verificationCode = new VerificationCode(code, email);
        verificationCodeRepository.save(verificationCode);

        String htmlContent = """
                <html>
                    <body style="font-family: Arial, sans-serif; background-color: #f5f5f5; padding: 20px;">
                        <div style="background-color: white; border-radius: 10px; padding: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                            <h2 style="color: #4CAF50;">¡Hola! 👋</h2>
                            <p style="font-size: 16px; color: #333;">
                                Tu código de verificación es:
                                <b style="font-size: 20px;">%s</b>
                            </p>
                            <p style="font-size: 14px; color: gray;">Gracias por usar LandSV.</p>
                        </div>
                    </body>
                </html>
                """.formatted(code);

        sendBrevoEmail(email, "Código de verificación", htmlContent);
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
