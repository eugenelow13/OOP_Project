package com.oop.api.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.oop.api.model.Booking;
import com.oop.api.model.Event;
import com.oop.api.model.EventStatistics;
import com.oop.api.model.Ticket;
import com.oop.api.repository.BookingRepository;
import com.oop.api.repository.EventRepository;

/**
 * This class provides functionality to generate event statistics and generate reports in Excel and PDF formats.
 * It retrieves event data from the EventRepository and BookingRepository, calculates statistics such as total tickets sold,
 * customer attendance, and total revenue, and generates reports using Apache POI library for Excel reports and iTextPDF library for PDF reports.
 */

@Service
public class ReportStatisticsService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Retrieves the statistics for all events.
     *
     * @return a list of EventStatistics objects representing the statistics for each event
     */

    public List<EventStatistics> getEventStatistics() {
        List<EventStatistics> eventStatisticsList = new ArrayList<EventStatistics>();
        Iterable<Event> events = eventRepository.findAll();
        for (Event event : events) {
            int eventId = event.getId();
            String eventName = event.getName();

            Iterable<Booking> bookings = bookingRepository.findByEventId(eventId);
            Stream<Booking> bookingStream = StreamSupport.stream(bookings.spliterator(), false);

            List<Ticket> tickets = bookingStream
                    .map(booking -> booking.getTickets())
                    .reduce(new ArrayList<Ticket>(), (a, b) -> {
                        a.addAll(b);
                        return a;
                    });

            int totalTicketsSold = 0;
            int customerAttendance = 0;
            for (Ticket ticket : tickets) {
                totalTicketsSold++;
                if (ticket.getIsAdmitted()) {
                    customerAttendance += ticket.getNoOfGuests() + 1;
                }
            }

            double totalRevenue = totalTicketsSold * event.getTicketPrice();
            LocalDateTime eventDate = event.getDate();
            double ticketPrice = event.getTicketPrice();
            EventStatistics eventStatistics = new EventStatistics(eventId, eventName, totalTicketsSold, totalRevenue,
                    eventDate, customerAttendance, ticketPrice);
            eventStatisticsList.add(eventStatistics);

        }

        return eventStatisticsList;
    }

    /**
     * Generates an Excel report containing event statistics.
     *
     * @return a byte array representing the generated Excel report
     * @throws IOException if an I/O error occurs while generating the report
     */

    public byte[] generateExcelReport() throws IOException {

        Workbook workbook = new XSSFWorkbook();
        // // Create sheets
        Sheet ticketSalesSheet = workbook.createSheet("Events Statistics");
        // // Example for ticketSalesSheet
        List<EventStatistics> allEventsStatistics = getEventStatistics();
        int rowIndex = 0;
        Row headerRow = ticketSalesSheet.createRow(rowIndex++);
        headerRow.createCell(0).setCellValue("Event ID");
        headerRow.createCell(1).setCellValue("Event Date");
        headerRow.createCell(2).setCellValue("Event Name");
        headerRow.createCell(3).setCellValue("Customer Attendance");
        headerRow.createCell(4).setCellValue("Total Tickets Sold");
        headerRow.createCell(5).setCellValue("Ticket Price");
        headerRow.createCell(6).setCellValue("Total Revenue");

        for (EventStatistics eventStatistics : allEventsStatistics) {
            Row row = ticketSalesSheet.createRow(rowIndex++);
            // // Assuming TicketSale has fields like id, date, amount
            row.createCell(0).setCellValue(eventStatistics.getEventId());
            row.createCell(1).setCellValue(eventStatistics.getEventDate());
            row.createCell(2).setCellValue(eventStatistics.getEventName());
            row.createCell(3).setCellValue(eventStatistics.getCustomerAttendance());
            row.createCell(4).setCellValue(eventStatistics.getTotalTicketsSold());
            row.createCell(5).setCellValue(eventStatistics.getTicketPrice());
            row.createCell(6).setCellValue(eventStatistics.getTotalRevenue());

        }

        // Write the output to a file
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();

    }

    // //iTextPDF library is used to generate PDF reports
    /**
     * Generates a PDF report containing event statistics.
     * 
     * @return the byte array representation of the generated PDF report
     * @throws DocumentException if there is an error in creating the PDF document
     * @throws IOException if there is an error in reading or writing the PDF document
     */
    
    public byte[] generatePdfReport() throws DocumentException, IOException {
        
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();
        
        Paragraph head = new Paragraph("Event Statistics", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD));

        // make head align center and add spacing below
        head.setAlignment(Paragraph.ALIGN_CENTER);
        head.setSpacingAfter(20);

        document.add(head);

        // Create tables
        List<EventStatistics> allEventsStatistics = getEventStatistics();
        PdfPTable eventStatisticsTable = new PdfPTable(7);

        eventStatisticsTable.setWidthPercentage(90);

        eventStatisticsTable.addCell("Event ID");
        eventStatisticsTable.addCell("Event Date");
        eventStatisticsTable.addCell("Event Name");
        eventStatisticsTable.addCell("Customer Attendance");
        eventStatisticsTable.addCell("Total Tickets Sold");
        eventStatisticsTable.addCell("Ticket Price");
        eventStatisticsTable.addCell("Total Revenue");

        for (EventStatistics eventStatistics : allEventsStatistics) {

            eventStatisticsTable.addCell(String.valueOf(eventStatistics.getEventId()));
            eventStatisticsTable.addCell(eventStatistics.getEventDate().toString());
            eventStatisticsTable.addCell(eventStatistics.getEventName());
            eventStatisticsTable.addCell(String.valueOf(eventStatistics.getCustomerAttendance()));
            eventStatisticsTable.addCell(String.valueOf(eventStatistics.getTotalTicketsSold()));
            eventStatisticsTable.addCell(String.valueOf(eventStatistics.getTicketPrice()));
            eventStatisticsTable.addCell(String.valueOf(eventStatistics.getTotalRevenue()));
        }
        // Add tables to the document
        document.add(eventStatisticsTable);

        document.close();

        return out.toByteArray();
    }
}
