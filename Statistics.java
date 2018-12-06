import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
/**
 * 
 * @author Sean Ly
 * @version 2018-10-28
 *
 */
public class Statistics extends Observation implements DateTimeComparable
{
    static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss z";
    static DateTimeFormatter format;
    private GregorianCalendar utcDateTime;
    private ZonedDateTime zdtDateTime;
    private int numberOfReportingStations;
    private StatsType statType;
    //assuming dateTimeStr is coming from file name
    /**
     * 
     * @param value
     * @param stid
     * @param dateTimeStr
     * @param numberOfValidStations
     * @param inStatType
     */
    public Statistics (double value, String stid, ZonedDateTime dateTime, int numberOfValidStations, StatsType inStatType)
    {       
        super(value, stid);        
        this.zdtDateTime = dateTime;
        this.numberOfReportingStations = numberOfValidStations;
        this.statType = inStatType;
        this.utcDateTime = GregorianCalendar.from(zdtDateTime);
        
    }
    /**
     * 
     * @param value
     * @param stid
     * @param dateTime
     * @param numberOfValidStations
     * @param inStatType
     */
    public Statistics (double value, String stid, GregorianCalendar dateTime, int numberOfValidStations, StatsType inStatType)
    {
        super(value, stid);
        this.utcDateTime = dateTime;
        this.numberOfReportingStations = numberOfValidStations;
        this.statType = inStatType;
        this.zdtDateTime = utcDateTime.toZonedDateTime();
        
    }
    /**
     * 
     * @param dateTimeStr
     * @return
     * @throws ParseException 
     */
    public ZonedDateTime createZDateFromString (String dateTimeStr) throws ParseException
    {
       
        DateFormat df = new SimpleDateFormat (DATE_TIME_FORMAT);
        Date date = df.parse(dateTimeStr);
        
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        ZonedDateTime zdt = calendar.toZonedDateTime();
        return zdt;
        
    }
    /**
     * 
     * @param dateTimeStr
     * @return
     * @throws ParseException 
     */
    public GregorianCalendar createDateFromString (String dateTimeStr) throws ParseException
    {
       DateFormat df = new SimpleDateFormat (DATE_TIME_FORMAT);
       Date date = df.parse(dateTimeStr);
       
       Calendar calendar = new GregorianCalendar();
       calendar.setTime(date);
     
       return (GregorianCalendar) calendar;
       
       
    }
    /**
     * 
     * @param calendar
     * @return
     */
    public String createStringFromDate (GregorianCalendar calendar)
    {
        SimpleDateFormat df = new SimpleDateFormat (DATE_TIME_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
   
                
                return df.format(calendar.getTime());
    }
    /**
     * 
     * @param calendar
     * @return
     */
    public String createStringFromDate (ZonedDateTime calendar)
    {

        return DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).format(calendar);
    }
    /**
     * 
     * @return
     */
    public int getNumberOfReportingStations ()
    {
        return numberOfReportingStations;
    }
    /**
     * 
     * @return
     */
    public String getUTCDateTimeString ()
    {
        return createStringFromDate(utcDateTime);
    }
    /**
     * 
     * @param inDateTime
     * @return
     */
    public boolean newerThan (GregorianCalendar inDateTime)
    {
        return this.utcDateTime.after(inDateTime);
    }
    /**
     * 
     * @param inDateTime
     * @return
     */
    public boolean olderThan (GregorianCalendar inDateTime)
    {
        return this.utcDateTime.before(inDateTime);
        
    }
    /**
     * 
     * @param inDateTime
     * @return
     */
    public boolean sameAs (GregorianCalendar inDateTime)
    {
        return utcDateTime.equals(inDateTime);
    }
    /**
     * Compares the date of the stats object and compares against another date
     */
    public boolean newerThan (ZonedDateTime inDateTime)
    {

        return zdtDateTime.isAfter(inDateTime);
    }
    /**
     * Compares the date of the stats object and compares against another date
     */
    public boolean olderThan (ZonedDateTime inDateTime)
    {

        return zdtDateTime.isBefore(inDateTime);
    }
    /**
     * Compares the date of the stats object and compares against another date
     */
    public boolean sameAs (ZonedDateTime inDateTime)
    {

        return zdtDateTime.isEqual(inDateTime);
    }
    /**
     * @ return String representation of Statistics
     */
    public String toString ()
    {
        return String.format("STID: %s, Value: %s, Valid?: %s Stat Type: %s\n", this.stid, this.value, this.isValid(), this.statType);
    }
    
   /**
    * 
    * @return GregorianCalendar of the date and time for the statistics
    */
    public GregorianCalendar getUtcDateTime ()
    {
        return this.utcDateTime;
    }
    
    /**
     * 
     * @return the StatsType of the statistic
     */
    public StatsType getStatType()
    {
        return statType;
    }
    /**
     * 
     * @return ZonedDateTime of the statistic
     */
    public ZonedDateTime getZdtDateTime()
    {
        return zdtDateTime;
    }

    
}
