package com.appwineries.Wineries.service.impl;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {

    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String clientSecret;

    @Value("${google.refresh-token}")
    private String refreshToken;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendConfirmationEmail(String userEmail, String reservationDetails) {
        try {
            AccessToken accessToken = getAccessToken();

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Potvrda o odobrenju rezervacije");
            message.setSentDate(new Date());
            message.setText("Vaša rezervacija je uspješno odobrena. Detalji rezervacije:\n\n" + reservationDetails);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", fromEmail, accessToken.getTokenValue());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("E-mail poslan na: " + userEmail);

        } catch (MessagingException | IOException e) {
            System.out.println("Greška pri slanju e-maila: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private AccessToken getAccessToken() throws IOException {
        UserCredentials credentials = UserCredentials.newBuilder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken)
                .build();

        credentials.refresh();
        System.out.println("Access Token: " + credentials.getAccessToken().getTokenValue());

        return credentials.getAccessToken();
    }
}
