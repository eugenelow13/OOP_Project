// package com.oop.api.controller;

// import static com.oop.api.util.ResponseHandler.generateResponse;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;

// import com.oop.api.model.TicketingOfficer;
// import com.oop.api.service.TicketingOfficerService;

// import jakarta.persistence.EntityNotFoundException;
// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/ticketing_officers")
// public class TicketingOfficerController {

//     @Autowired
//     private TicketingOfficerService eventManagerService;

//     @GetMapping(path = "")
//     public @ResponseBody ResponseEntity<Object> getAllTicketingOfficers() {
//         Iterable<TicketingOfficer> eventManagers = eventManagerService.getAllTicketingOfficers();
//         return generateResponse(eventManagers);
//     }

//     @GetMapping(path = "/{username}")
//     public @ResponseBody Optional<TicketingOfficer> getTicketingOfficer(@PathVariable String email) {
//         Optional<TicketingOfficer> eventManager = ticketingOfficerService.getTicketingOfficerByEmail(email);

//         if (eventManager.isEmpty())
//             throw new EntityNotFoundException("Event Manager not found");

//         return eventManager;
//     }

//     @PostMapping(path = "")
//     public ResponseEntity<Object> addNewEventManager(@Valid @RequestBody EventManager eventManager) {
//         eventManagerService.addNewEventManager(eventManager);

//         return generateResponse("Created", (Object) eventManager);
//     }
    
    
// }
