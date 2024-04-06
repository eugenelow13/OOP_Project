import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor; 

@Getter @Setter @AllArgsConstructor
public class EventStatistics {
    private int eventId;
    private String eventName;
    private int totalTicketsSold;
    private double totalRevenue;
    private LocalDateTime eventDate;
    private int customerAttendance;
    private double ticketPrice;

    
    
}
