package com.oop.api.email;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.oop.api.dto.BookingInfo;
import com.oop.api.dto.TicketInfo;
import com.oop.api.model.Ticket;
import com.oop.api.repository.UserRepository;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(to);
        message.setText(text);

        mailSender.send(message);
    }

    public byte[] getQRCodeStream(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 400, 400);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFFFFFFFF);

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    public void sendEmail(String to, BookingInfo bookingInfo) throws IOException, MessagingException, WriterException{
        String bookingId = String.valueOf(bookingInfo.getId());

        List<TicketInfo> tickets = bookingInfo.getTickets();
        
        String[] ids = tickets.stream().map(ticket -> String.valueOf(ticket.getId())).toArray(String[]::new);

        String subject = "Your " + bookingInfo.getEvent().getName() + " Booking #" + bookingId;
        String text = subject + " has been confirmed";

        for (TicketInfo ticket: tickets) {
            text += "\n Ticket #" + ticket.getId() + ": " + ticket.getNoOfGuests() + " guests";
        }

        sendEmail(to, subject, text, ids);
    }

    public void sendEmail(String to, String subject, String text, String id) throws IOException, MessagingException, WriterException {
        sendEmail(to, subject, text, new String[] { id });
    };

    public void sendEmail(String to, String subject, String text, String[] ids) throws IOException, MessagingException, WriterException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        helper.setFrom(env.getProperty("spring.mail.username"));
        helper.setSubject(subject);
        helper.setText(text);

        for (String id : ids) {
            byte[] pngData = getQRCodeStream(id);
            helper.addAttachment("Ticket #" + id + ".png", new ByteArrayResource(pngData));
        }

        mailSender.send(message);
};
}
