// package com.oop.api.bootstrap;

// import com.oop.api.service.EventManagerService;
// import com.oop.api.model.EventManager;
// import com.oop.api.repository.EventManagerRepository;
// import com.oop.api.repository.RoleRepository;
// import com.oop.api.model.Role;
// import com.oop.api.model.RoleEnum;
// import com.oop.api.dto.RegisterUserDTO;

// import java.util.*;

// import org.springframework.context.ApplicationListener;
// import org.springframework.context.event.ContextRefreshedEvent;
// import org.springframework.security.crypto.password.PasswordEncoder;

// public class B_EventManagerSeeder implements ApplicationListener<ContextRefreshedEvent>  {
    
//     private final EventManagerService eventManagerService;
//     private final EventManagerRepository eventManagerRepository;
//     private final RoleRepository roleRepository;
//     private final PasswordEncoder passwordEncoder;

//     public B_EventManagerSeeder(
//         EventManagerService eventManagerService,
//         EventManagerRepository eventManagerRepository,
//         RoleRepository roleRepository, 
//         PasswordEncoder passwordEncoder
//         ) {
//         this.eventManagerService = eventManagerService;
//         this.eventManagerRepository = eventManagerRepository;
//         this.roleRepository = roleRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Override
//     public void onApplicationEvent(ContextRefreshedEvent event) {

//         // Create Event Manager 1
//         RegisterUserDTO eventManagerDTO1 = new RegisterUserDTO();
//         eventManagerDTO1.setFullName("Event Manager 1");
//         eventManagerDTO1.setEmail("eventmanager1@example.com");
//         eventManagerDTO1.setPassword("password1");

//         Optional<EventManager> optionalUser = eventManagerRepository.findByEmail(eventManagerDTO1.getEmail());

//         if (optionalUser.isPresent()) {
//             System.out.println("Username is already present");
//             return;
//         } 

//         // var eventManager = new EventManager();
//         // eventManager.setFullName(eventManagerDTO1.getFullName());
//         // eventManager.setEmail(eventManagerDTO1.getEmail());
//         // eventManager.setPassword(passwordEncoder.encode(eventManagerDTO1.getPassword()));
//         // Set<Role> roles = new HashSet<>();
//         // roles.add(optionalRole.get());
//         // eventManager.setRoles(roles);

//         // eventManagerRepository.save(eventManager);
//         eventManagerService.createEventManager(eventManagerDTO1);
//         System.out.println("Event Manager created successfully");

//     }

// }
