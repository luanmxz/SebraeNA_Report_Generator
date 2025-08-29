package com.luanmarcene.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.exception.UncheckedException;

import io.github.cdimascio.dotenv.Dotenv;

public class EmailService {

    private final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private final String username = dotenv.get("GMAIL_USERNAME");
    private final String appPassword = dotenv.get("GMAIL_APP_PASSWORD");
    private final String toUserEmail = dotenv.get("GMAIL_TOUSER");

    public void sendReportEmail() throws IOException {

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, appPassword);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));

            Address[] toUser = InternetAddress.parse(toUserEmail);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(dotenv.get("SUBJECT"));

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(dotenv.get("EMAIL_BODY"));

            Optional<Path> latestFile = null;
            try (Stream<Path> paths = Files.walk(Paths.get("report-generator/out"))) {
                latestFile = paths
                        .filter(Files::isRegularFile)
                        .max(Comparator.comparing(p -> {
                            try {
                                return Files.getLastModifiedTime(p);
                            } catch (IOException e) {
                                throw new UncheckedException(e);
                            }
                        }));

                if (latestFile.isPresent()) {
                    System.out.println("Path: " + latestFile.get());
                }
            } catch (IOException e) {
                System.err.println("Erro: " + e.getMessage());
            }

            MimeBodyPart attachBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(latestFile.get().toFile());
            attachBodyPart.setDataHandler(new DataHandler(source));
            attachBodyPart.setFileName(latestFile.get().getFileName().toString());

            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(attachBodyPart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
