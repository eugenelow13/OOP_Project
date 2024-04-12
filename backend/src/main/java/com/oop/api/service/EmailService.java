package com.oop.api.service;

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

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * This class provides functionality for sending emails and generating QR codes for ticket bookings.
 */
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    /**
     * Sends an email to the specified recipient with the given subject and text.
     *
     * @param to      the email address of the recipient
     * @param subject the subject of the email
     * @param text    the content of the email
     */
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(to);
        message.setText(text);

        mailSender.send(message);
    }

    /**
     * Generates a QR code image as a byte array.
     *
     * @param text the text to be encoded in the QR code
     * @return a byte array representing the QR code image
     * @throws WriterException if there is an error encoding the text into a QR code
     * @throws IOException if there is an error writing the QR code image to a stream
     */
    public byte[] getQRCodeStream(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 400, 400);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig(0xFF000002, 0xFFFFFFFF);

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream, con);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    /**
     * Sends an email to the specified recipient with the booking information.
     *
     * @param to The email address of the recipient.
     * @param bookingInfo The booking information.
     * @throws IOException If an I/O error occurs while sending the email.
     * @throws MessagingException If there is an error in the underlying messaging system.
     * @throws WriterException If there is an error in generating the email content.
     */
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

    /**
     * Sends an email to the specified recipient with the given subject and text.
     * 
     * @param to      the email address of the recipient
     * @param subject the subject of the email
     * @param text    the content of the email
     * @param id      the identifier associated with the email
     * @throws IOException        if an I/O error occurs while sending the email
     * @throws MessagingException if there is an error in the underlying messaging system
     * @throws WriterException    if there is an error in generating the email content
     */
    public void sendEmail(String to, String subject, String text, String id) throws IOException, MessagingException, WriterException {
        sendEmail(to, subject, text, new String[] { id });
    };

    /**
     * Sends an email with the specified details and attachments.
     *
     * @param to      the recipient's email address
     * @param subject the subject of the email
     * @param text    the content of the email
     * @param ids     an array of ticket IDs to generate QR codes and attach to the email
     * @throws IOException         if an I/O error occurs while generating the QR codes
     * @throws MessagingException  if there is an error in the underlying messaging system
     * @throws WriterException     if there is an error while generating the QR codes
     */
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
