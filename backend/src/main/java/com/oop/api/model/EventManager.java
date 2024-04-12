package com.oop.api.model;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an event manager who can create and manage events.
 */
@Entity
@Getter @Setter
public class EventManager extends User {

}