package entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testUser() {
        User user = new User(1L, "John Doe", "john@mail.com");

        assertEquals(user.email(), "john@mail.com");
    }
}
