import java.text.ParseException;
import java.util.GregorianCalendar;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 * This Class tests the functionality of the public methods contained in the
 * Statistics Class.
 * 
 * @author Sean Ly
 * @version 2018-10-28
 */
public class StatisticsTest
{
    String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss z";
   
    GregorianCalendar dateTime = new GregorianCalendar(2018 , 1, 1, 1, 1, 0);
    GregorianCalendar otherDateTime = new GregorianCalendar (2017, 1, 1, 1, 1, 0);
    Statistics testStat = new Statistics (10.0, "TEST", dateTime, 100,StatsType.MAXIMUM);
    Statistics otherTestStat = new Statistics(15, "OtherTest", otherDateTime, 100, StatsType.AVERAGE);
    
   
    /**
     * 
     */
    @Test
    public void testGetUTCDateTimeString()
    {
        String expected = "2018-02-01T07:01:00 UTC";
        
        assertEquals(expected, testStat.getUTCDateTimeString());
    }
    /**
     * 
     */
    @Test
    public void testNewerThan()
    {
        assertTrue( testStat.newerThan(otherTestStat.getUtcDateTime()));
        assertFalse( otherTestStat.newerThan(testStat.getUtcDateTime()));
    }
    /**
     * 
     */
    @Test
    public void testOlderThan()
    {   
        assertFalse( testStat.olderThan(otherTestStat.getUtcDateTime()));
        assertTrue( otherTestStat.olderThan(testStat.getUtcDateTime()));
    }
    
    /**
     * test same as for gregorian calendar types
     */
    @Test
    public void testSameAs()
    {
        assertFalse( testStat.sameAs(otherTestStat.getUtcDateTime()));
        assertTrue(testStat.sameAs(testStat.getUtcDateTime()));
    }
    /**
     * test to string method
     */
    @Test
    public void testToString()
    {
        //String.format("STID: %s, Value: %s, Valid?: %s Stat Type: %s\n", this.stid, this.value, this.isValid(), this.statType);
        String expected = "STID: TEST, Value: 10.0, Valid?: true Stat Type: MAXIMUM\n";
        assertEquals(expected, testStat.toString());
    }
    /**
     * Test get number of reporting stations
     */
    @Test
    public void testgetNumberOfReportingStations ()
    {
        int expected = 100;
        assertEquals (expected, testStat.getNumberOfReportingStations());
    }
        /**
     * 
     */
    @Test
    public void testCreateStringFromDateGreg ()
    {   String expected = "2018-02-01T07:01:00 UTC";
        assertEquals(expected,testStat.createStringFromDate(testStat.getUtcDateTime()));
    }
    /**
     * Test Create string from z dates
     * @throws ParseException 
     * 
     */
    @Test
    public void testCreateStringFromDateZ () throws ParseException
    {
        String expected ="2018-02-01T01:01:00 CST";
        Statistics zTestStat;
            
        String zString = testStat.createStringFromDate(testStat.getUtcDateTime());
        zTestStat = new Statistics (10.0, "TEST", testStat.createZDateFromString(zString), 100,StatsType.MAXIMUM);
        
        
        assertEquals(expected,zTestStat.createStringFromDate(zTestStat.getZdtDateTime()));
        
    }
    /**
     * Test create date from string for gregorian calendar
     * @throws ParseException 
     * 
     */
    @Test
    public void testCreateDateFromString () throws ParseException
    {
       String date = testStat.createStringFromDate(testStat.getUtcDateTime());
       GregorianCalendar expected = testStat.createDateFromString(date);
       
       assertEquals(expected, testStat.getUtcDateTime());
        
    }
    /**
     * test older than for zdates
     * @throws ParseException
     */
    @Test
    public void testZOlderThan () throws ParseException
    {
        Statistics zTestStat;
        Statistics otherZTestStat;
        
        String zString = testStat.createStringFromDate(testStat.getUtcDateTime());
        String zStringOther = otherTestStat.createStringFromDate(otherTestStat.getUtcDateTime());
        zTestStat = new Statistics (10.0, "TEST", testStat.createZDateFromString(zString), 100,StatsType.MAXIMUM);
        otherZTestStat = zTestStat = new Statistics (10.0, "TEST", otherTestStat.createZDateFromString(zStringOther), 100,StatsType.MAXIMUM);
        
        assertFalse(zTestStat.olderThan(otherZTestStat.getZdtDateTime()));
        
    }
    /**
     * Test newerThan for z dates
     * @throws ParseException
     */
    @Test
    public void testZNewerThan() throws ParseException
    {   
        Statistics zTestStat;
        Statistics otherZTestStat;
        
        String zString = testStat.createStringFromDate(testStat.getUtcDateTime());
        String zStringOther = otherTestStat.createStringFromDate(otherTestStat.getUtcDateTime());
        zTestStat = new Statistics (10.0, "TEST", testStat.createZDateFromString(zString), 100,StatsType.MAXIMUM);
        otherZTestStat = zTestStat = new Statistics (10.0, "TEST", otherTestStat.createZDateFromString(zStringOther), 100,StatsType.MAXIMUM);
        
        assertFalse(zTestStat.newerThan(otherZTestStat.getZdtDateTime()));
    }
    /**
     * Test same as for zonedDateTime 
     * @throws ParseException
     */
    @Test
    public void testZSameAs() throws ParseException
    {
        Statistics zTestStat;
        Statistics otherZTestStat;
        
        String zString = testStat.createStringFromDate(testStat.getUtcDateTime());
        String zStringOther = otherTestStat.createStringFromDate(otherTestStat.getUtcDateTime());
        zTestStat = new Statistics (10.0, "TEST", testStat.createZDateFromString(zString), 100,StatsType.MAXIMUM);
        otherZTestStat = zTestStat = new Statistics (10.0, "TEST", otherTestStat.createZDateFromString(zStringOther), 100,StatsType.MAXIMUM);
        
        assertTrue(zTestStat.sameAs(otherZTestStat.getZdtDateTime()));
    }
    
    
}
