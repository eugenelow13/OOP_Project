package com.oop.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.api.model.Booking;
import com.oop.api.model.Event;
import com.oop.api.model.EventStatistics;
import com.oop.api.model.Ticket;
import com.oop.api.repository.BookingRepository;
import com.oop.api.repository.EventRepository;

@Service
public class ReportStatisticsService
 {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    //To be replaced with the actual constructor that contains the actual variables of ticket, revenue and customer attendance
    public List<EventStatistics> getEventStatistics()
    {
        List<EventStatistics> eventStatisticsList = new ArrayList<EventStatistics>();
        Iterable<Event> events = eventRepository.findAll();
        for(Event event: events)
        {
            int eventId = event.getId();
            String eventName = event.getName();
            

            Iterable<Booking> bookings = bookingRepository.findByEventId(eventId);
            Stream<Booking> bookingStream = StreamSupport.stream(bookings.spliterator(), false);

            List<Ticket> tickets = bookingStream
                .map(booking -> booking.getTickets())
                .reduce(new ArrayList<Ticket>(), (a, b) -> { a.addAll(b); return a;});

            int totalTicketsSold = 0;
            int customerAttendance = 0;
            for(Ticket ticket: tickets)
            {
                totalTicketsSold ++;
                if(ticket.getIsAdmitted())
                {
                    customerAttendance += ticket.getNoOfGuests() + 1;
                }
            }
           
            double totalRevenue = totalTicketsSold * event.getTicketPrice();
            LocalDateTime eventDate = event.getDate();
            double ticketPrice = event.getTicketPrice();
            EventStatistics eventStatistics = new EventStatistics(eventId, eventName, totalTicketsSold, totalRevenue, eventDate, customerAttendance, ticketPrice);
            eventStatisticsList.add(eventStatistics);

        }

        return eventStatisticsList;
    }


    //Apache POI library is used to generate Excel reports
    // public void generateExcelReport(String fileName) throws IOException {
    //     Workbook workbook = new XSSFWorkbook();
    
    //     // Create sheets
    //     Sheet ticketSalesSheet = workbook.createSheet("Ticket Sales");
    //     Sheet revenuesSheet = workbook.createSheet("Revenues");
    //     Sheet customerAttendancesSheet = workbook.createSheet("Customer Attendances");
    
    //     // TODO: Add data to ticketSalesSheet, revenuesSheet, and customerAttendancesSheet
    //     // Example for ticketSalesSheet
    //     int rowIndex = 0;
    //     for (TicketSale ticketSale : ticketSales) {
    //         Row row = ticketSalesSheet.createRow(rowIndex++);
    //         // Assuming TicketSale has fields like id, date, amount
    //         row.createCell(0).setCellValue(ticketSale.getId());
    //         row.createCell(1).setCellValue(ticketSale.getDate());
    //         row.createCell(2).setCellValue(ticketSale.getAmount());
    //     }
    
    //     // Repeat similar logic for revenues and customerAttendances

    //     for (Revenue revenue: revenues) {
    //         Row row = revenuesSheet.createRow(rowIndex++);
    //         // Assuming TicketSale has fields like id, date, amount
    //         row.createCell(0).setCellValue(revenue.getId());
    //         row.createCell(1).setCellValue(revenue.getDate());
    //         row.createCell(2).setCellValue(revenue.getAmount());
    //     }

    //     for(CustomerAttendance attendance: customerAttendances) {
    //         Row row = customerAttendancesSheet.createRow(rowIndex++);
    //         // Assuming TicketSale has fields like id, date, amount
    //         row.createCell(0).setCellValue(attendance.getId());
    //         row.createCell(1).setCellValue(attendance.getDate());
    //         row.createCell(2).setCellValue(attendance.getAmount());
    //     }
    
    //     // Write the output to a file
    //     try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
    //         workbook.write(fileOut);
    //     }
    //     workbook.close();
    // }
    // //iTextPDF library is used to generate PDF reports
    // public void generatePdfReport(String fileName) throws DocumentException, IOException {
    //     Document document = new Document();
    //     PdfWriter.getInstance(document, new FileOutputStream(fileName));
    //     document.open();
    
    //     // Create tables
    //     PdfPTable ticketSalesTable = new PdfPTable(3); 
    //     PdfPTable revenuesTable = new PdfPTable(3); 
    //     PdfPTable customerAttendancesTable = new PdfPTable(3); 
    
    //     // TODO: Add data to ticketSalesTable, revenuesTable, and customerAttendancesTable
    //     // Example for ticketSalesTable
    //     for (TicketSale ticketSale : ticketSales) {
    //         PdfPCell cell = new PdfPCell(new Phrase(ticketSale.getId()));
    //         ticketSalesTable.addCell(cell);
    //         ticketSalesTable.addCell(ticketSale.getDate());
    //         ticketSalesTable.addCell(ticketSale.getAmount());

    //         // Repeat for other fields
    //     }
    
    //     // Repeat similar logic for revenues and customerAttendances

    //     for(Revenue revenue: revenues) {
    //         PdfPCell cell = new PdfPCell(new Phrase(revenue.getId()));
    //         revenuesTable.addCell(cell);
    //         // Repeat for other fields
    //         revenuesTable.addCell(revenue.getDate());
    //         revenuesTable.addCell(revenue.getAmount());

    //     }
    //     for(CustomerAttendance attendance: customerAttendances) {
    //         PdfPCell cell = new PdfPCell(new Phrase(attendance.getId()));
    //         customerAttendancesTable.addCell(cell);
    //         // Repeat for other fields
    //         customerAttendancesTable.addCell(attendance.getDate());
    //         customerAttendancesTable.addCell(attendance.getAttendance());
    
    //     // Add tables to the document
    //     document.add(ticketSalesTable);
    //     document.add(revenuesTable);
    //     document.add(customerAttendancesTable);
    
    //     document.close();
    // }
// }
}
