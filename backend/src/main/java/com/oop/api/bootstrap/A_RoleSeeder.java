package com.oop.api.bootstrap;

import com.oop.api.model.Role;
import com.oop.api.model.RoleEnum;
import com.oop.api.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class A_RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;


    public A_RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] { RoleEnum.CUSTOMER, RoleEnum.TICKETING_OFFICER, RoleEnum.EVENT_MANAGER};
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
            RoleEnum.CUSTOMER, "Default customer role",
            RoleEnum.TICKETING_OFFICER, "Ticketing officer role",
            RoleEnum.EVENT_MANAGER, "Event manager role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();
                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(roleToCreate);
            });
        });
    }
}