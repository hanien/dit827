import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestingSetup{
    
    @Test
    public void PreminTest() {

        int x = 3;
        // assert statements
        assertEquals(0, x*0, "10 x 0 must be 0");
        assertEquals((0==x)==false);
    }
}