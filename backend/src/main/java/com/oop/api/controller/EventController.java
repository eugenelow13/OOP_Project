package com.oop.api.controller;

import static com.oop.api.util.ResponseHandler.generateResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.itextpdf.text.DocumentException;
import com.oop.api.email.EmailService;
import com.oop.api.model.Event;
import com.oop.api.service.EventService;
import com.oop.api.service.ReportStatisticsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

/**
 * The EventController class is responsible for handling HTTP requests related to events.
 * It provides endpoints for retrieving, adding, updating, and canceling events, as well as generating event statistics and reports.
 */

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReportStatisticsService reportStatisticsService;

    private HashSet<String> patchableFields = new HashSet<>(Arrays.asList("name", "type", "venue", "date", "ticketPrice", "cancellationFee", "ticketsAvailable", "customerAttendance", "eventStatus"));

    @GetMapping(path = "")
    public @ResponseBody ResponseEntity<Object> getAllEvents() {
        Iterable<Event> events = eventService.getAllEvents();
        return generateResponse(events);
    }

    @GetMapping(path = "/{eventId}")
    public @ResponseBody ResponseEntity<Object> getEvent(@PathVariable Integer eventId) {
        Optional<Event> event = eventService.getEventById(eventId);

        if (event.isEmpty())
            throw new EntityNotFoundException("Event not found");

        return generateResponse(event.get());
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> addNewEvent(@Valid @RequestBody Event event) {
        eventService.addNewEvent(event);
        return generateResponse("Event is successfully created", (Object) event, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<Object> updateEvent(@PathVariable Integer eventId, @Valid @RequestBody Event updatedEventWithoutId) {
        updatedEventWithoutId.setId(eventId);
        Event updatedEvent = updatedEventWithoutId;

        Event result = eventService.updateEvent(updatedEvent);
        return generateResponse("Event is successfully updated", result);
    }

    @PutMapping(path = "/{eventId}/cancel")
    public ResponseEntity<Object> cancelEvent(@PathVariable Integer eventId) {
        Event cancelledEvent = eventService.cancelEvent(eventId);
        return generateResponse("Event is successfully cancelled", cancelledEvent);
    }

    /**
     * Updates an existing event with the provided patch data.
     *
     * @param eventId    The ID of the event to be updated.
     * @param eventPatch A map containing the fields to be updated and their new values.
     * @return A ResponseEntity containing the updated event object.
     * @throws EntityNotFoundException     If the event with the specified ID is not found.
     * @throws IllegalArgumentException    If invalid fields are provided in the request body.
     */

    @PatchMapping("/{eventId}")
    public ResponseEntity<Object> patchEvent(@PathVariable Integer eventId, @RequestBody Map<String, Object> eventPatch) {
        Event event = eventService.getEventById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
    
        if (!isProvidedFieldsPatchable(eventPatch))
            throw new IllegalArgumentException("Invalid fields provided in request body");

        mapper.map(eventPatch, event);
    
        return updateEvent(event.getId(), event);
    }

    /**
     * Checks if the provided fields in the event patch are patchable.
     *
     * @param eventPatch a map containing the fields to be patched
     * @return true if all the provided fields are patchable, false otherwise
     */
    private boolean isProvidedFieldsPatchable(Map<String, Object> eventPatch) {
        return eventPatch.keySet().stream().allMatch(k -> patchableFields.contains(k));
    }

    @GetMapping(path = "/statistics")
    public ResponseEntity<Object> getEventStatistics() {
        return generateResponse(reportStatisticsService.getEventStatistics());
    }

    /**
     * Generates a report in the specified format.
     *
     * @param type the type of the report (e.g., "pdf", "excel")
     * @return a ResponseEntity containing the generated report as a byte array
     * @throws IOException if an I/O error occurs while generating the report
     * @throws DocumentException if an error occurs while generating a PDF report
     */
    
    @GetMapping(path = "/statistics/export")
    public ResponseEntity<byte[]> generateReport(@RequestParam String type) throws IOException, DocumentException {
        if (type.equals("pdf")) {
            byte[] data = reportStatisticsService.generatePdfReport();
            return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(data);
        }
        
        byte[] data = reportStatisticsService.generateExcelReport();
        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(data);
    }
}
