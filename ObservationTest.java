import static org.junit.Assert.*;
import org.junit.Test;
/**
 * This Class tests the functionality of the public methods contained in the
 * Observation Class. It also implicitly tests AbstractObservation
 * 
 * @author Sean Ly
 * @version 2018-10-19
 */
public class ObservationTest
{
    Observation validTester = new Observation (10.0, "TEST valid");
    Observation invalidTester = new Observation (-900, "Test invalid");
    /**
     * This test explicitly tests the Observation Constructor and implicitly tests the getters and isValid of AbstractObservation
     */
    @Test
    public void testObservationConstructor()
    {  
        double expectedValueValid = 10.0;
        String expectedStid = "TEST valid";
        
        
        
        assertEquals(expectedStid, validTester.getStid());
        assertEquals(expectedValueValid, validTester.getValue(),0.01);
        assertEquals(true, validTester.isValid());
        assertEquals(false, invalidTester.isValid());
        
    }
    
    /**
     * This test tests the toString method of Observation
     */
    @Test
    public void testToString ()
    {
        String expected = "TEST valid 10.00";
        
        assertEquals(expected, validTester.toString());
    }
  

}
