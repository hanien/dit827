import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestingSetup{
    
    @Test
    public void PreminTest() {

        // assert statements
        assertEquals(0, 10*0, "10 x 0 must be 0");
        assertFalse(0==3);
    }
}