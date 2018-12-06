import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This Class tests the functionality of the public methods contained in the
 * MapData Class.
 * 
 * @author Sean Ly
 * @version 2018-10-28
 */

public class MapDataTest
{
    final int YEAR = 2018;
    final int MONTH = 8;
    final int DAY = 1;
    final int HOUR = 7;
    final int MINUTE = 0;
    String mesonet = "Mesonet";
    final String directory = "data";

    MapData mapTester = new MapData(YEAR, MONTH, DAY, HOUR, MINUTE, directory);

    /**
     * 
     * @throws IOException
     */
    @Before
    public void setUp() throws IOException
    {
        mapTester.parseFile();
        
    }
    
   
    
    /**
     * Testing creation of file name, implicitly tests constructor as well.
     */
    @Test
    public void testCreateFileName()
    {
     String expectedFileName = "data/201808010700.mdf";
     
     assertEquals(expectedFileName, mapTester.createFileName(2018, 8, 1, 7, 0, "data"));
    }
    /**
     * 
     */
    @Test
    public void testGetIndexOf()
    {   Integer expected = 0;
    
       assertEquals(expected, mapTester.getIndexOf("STID"));
    }
    /**
     * Tests the toString method in MapData. Implicitly tests calculateStatistics, parse file, and prepareDatacatalog
     */
    @Test
    public void testToString ()
    {
        String expected = "===================================================\n" + 
                "===2018-09-01 07:00 CDT===\n" + 
                "===================================================\n" + 
                "Maximum Air Temperature [1.5m] = 21.7 C at MEDI\n" + 
                "Minimum Air Temperature [1.5m] = 13.8 C at EVAX\n" + 
                "Average Air Temperature [1.5m] = 18.0 C at Mesonet\n" + 
                "===================================================\n" + 
                "===================================================\n" + 
                "Maximum Air Temperature [9.0m] = 23.3 C at MARE\n" + 
                "Minimum Air Temperature [9.0m] = 15.8 C at COOK\n" + 
                "Average Air Temperature [9.0m] = 19.7 C at Mesonet\n" + 
                "===================================================\n" + 
                "===================================================\n" + 
                "Maximum Solar Radiation [1.5m] = 0.0 W/m^2 at ACME\n" + 
                "Minimum Solar Radiation [1.5m] = 0.0 W/m^2 at ACME\n" + 
                "Average Solar Radiation [1.5m] = 0.0 W/m^2 at Mesonet\n" + 
                "===================================================\n";
        
       assertEquals("String output incorrect",expected, mapTester.toString());
    }

}
