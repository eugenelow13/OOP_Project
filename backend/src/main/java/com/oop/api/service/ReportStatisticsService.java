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

import com.oop.api.model.Booking;
import com.oop.api.model.Event;
import com.oop.api.model.EventStatistics;
import com.oop.api.model.Ticket;
import com.oop.api.repository.BookingRepository;
import com.oop.api.repository.EventRepository;

@Service
public class ReportStatisticsService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // To be replaced with the actual constructor that contains the actual variables
    // of ticket, revenue and customer attendance
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

    // Apache POI library is used to generate Excel reports

    public byte[] generateExcelReport() throws IOException {

        Workbook workbook = new XSSFWorkbook();
        // // Create sheets
        Sheet ticketSalesSheet = workbook.createSheet("Events Statistics");
        // // Example for ticketSalesSheet
        List<EventStatistics> eventStatisticsList = getEventStatistics();
        int rowIndex = 0;
        Row headerRow = ticketSalesSheet.createRow(rowIndex++);
        headerRow.createCell(0).setCellValue("Event ID");
        headerRow.createCell(1).setCellValue("Event Date");
        headerRow.createCell(2).setCellValue("Event Name");
        headerRow.createCell(3).setCellValue("Customer Attendance");
        headerRow.createCell(4).setCellValue("Total Tickets Sold");
        headerRow.createCell(5).setCellValue("Ticket Price");
        headerRow.createCell(6).setCellValue("Total Revenue");

        for (EventStatistics ticketSale : eventStatisticsList) {
            Row row = ticketSalesSheet.createRow(rowIndex++);
            // // Assuming TicketSale has fields like id, date, amount
            row.createCell(0).setCellValue(ticketSale.getEventId());
            row.createCell(1).setCellValue(ticketSale.getEventDate());
            row.createCell(2).setCellValue(ticketSale.getEventName());
            row.createCell(3).setCellValue(ticketSale.getCustomerAttendance());
            row.createCell(4).setCellValue(ticketSale.getTotalTicketsSold());
            row.createCell(5).setCellValue(ticketSale.getTicketPrice());
            row.createCell(6).setCellValue(ticketSale.getTotalRevenue());

        }

        // Write the output to a file
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();

    }

    // }
    // //iTextPDF library is used to generate PDF reports
    // public void generatePdfReport(String fileName) throws DocumentException,
    // IOException {
    // Document document = new Document();
    // PdfWriter.getInstance(document, new FileOutputStream(fileName));
    // document.open();

    // // Create tables
    // PdfPTable ticketSalesTable = new PdfPTable(3);
    // PdfPTable revenuesTable = new PdfPTable(3);
    // PdfPTable customerAttendancesTable = new PdfPTable(3);

    // // TODO: Add data to ticketSalesTable, revenuesTable, and
    // customerAttendancesTable
    // // Example for ticketSalesTable
    // for (TicketSale ticketSale : ticketSales) {
    // PdfPCell cell = new PdfPCell(new Phrase(ticketSale.getId()));
    // ticketSalesTable.addCell(cell);
    // ticketSalesTable.addCell(ticketSale.getDate());
    // ticketSalesTable.addCell(ticketSale.getAmount());

    // // Repeat for other fields
    // }

    // // Repeat similar logic for revenues and customerAttendances

    // for(Revenue revenue: revenues) {
    // PdfPCell cell = new PdfPCell(new Phrase(revenue.getId()));
    // revenuesTable.addCell(cell);
    // // Repeat for other fields
    // revenuesTable.addCell(revenue.getDate());
    // revenuesTable.addCell(revenue.getAmount());

    // }
    // for(CustomerAttendance attendance: customerAttendances) {
    // PdfPCell cell = new PdfPCell(new Phrase(attendance.getId()));
    // customerAttendancesTable.addCell(cell);
    // // Repeat for other fields
    // customerAttendancesTable.addCell(attendance.getDate());
    // customerAttendancesTable.addCell(attendance.getAttendance());

    // // Add tables to the document
    // document.add(ticketSalesTable);
    // document.add(revenuesTable);
    // document.add(customerAttendancesTable);

    // document.close();
    // }
    // }
}
