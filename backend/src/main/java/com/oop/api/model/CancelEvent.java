package com.oop.api.model;

import java.time.LocalDateTime;
import java.util.List;

import com.oop.api.model.CancelEvent;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter @Setter
public class CancelEvent extends Event {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer cancelEventId;  

    private String eventStatus; 
    public CancelEvent() {
        super();
    }


    public void cancelEvent() {
        this.setCancellationFee(0);
        this.updateTicketsAvailable(0);
    }
    
}
