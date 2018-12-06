import java.util.ArrayList;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
/**
 * Mapdata for holding weather data from mesonet files
 * @author Sean Ly
 *@version 2018-10-28
 */
public class MapData
{     
    //private static final int NUMBER_OF_MISSING_OBSERVATIONS = 10;
    private Integer numberOfStations = null;
    private String TA9M = "TA9M";
    private String TAIR = "TAIR";
    private String SRAD = "SRAD";
    private String STID = "STID";
    private String PRES = "PRES";
    private String WSPD = "WSPD";
    private String MESONET = "Mesonet";
    private String fileName;
    private GregorianCalendar utcDateTime;
    private HashMap<String, ArrayList<Observation>> dataCatalog = new HashMap<String, ArrayList<Observation>>();
    private EnumMap<StatsType, TreeMap<String, Statistics> > statistics = new EnumMap <StatsType, TreeMap <String, Statistics>>(StatsType.class);
    private TreeMap<String, Integer> paramPositions = new TreeMap<String, Integer>();
    /**
     * Creates map data given the directory of the data files and the base time of the file
     * @param year Year in which observations were taken
     * @param month Month in which observations were taken
     * @param day Day in which observations were taken
     * @param hour Hour in which observations were taken
     * @param minute Minute in which observations were taken
     * @param directory Directory of the data files
     */
    public MapData (int year, int month, int day, int hour, int minute, String directory)
    {
        this.numberOfStations = 0;
        
        this.fileName = createFileName(year, month, day, hour, minute, directory);
        this.utcDateTime = new GregorianCalendar(year, month, day, hour, minute, 0);
        this.prepareDataCatalog();
        
    }
    /**
     * 
     */
    public MapData(String fullFilePath, String justFileName)
    {
        this.fileName = fullFilePath;
        this.numberOfStations = 0;
        this.prepareDataCatalog();
        int year, month, day, hour, minute;
        
        year = Integer.parseInt(justFileName.substring(0, 4));
        month = Integer.parseInt(justFileName.substring(4,6));
        day = Integer.parseInt(justFileName.substring(6,8));
        hour = Integer.parseInt(justFileName.substring(8,10));
        minute = Integer.parseInt(justFileName.substring(10,12));
        
        
        this.utcDateTime = new GregorianCalendar(year, month, day, hour, minute, 0 );
        
        
    }
    /**
     * Creates the file name with given information from user.
     * * @param year Year in which observations were taken
     * @param month Month in which observations were taken
     * @param day Day in which observations were taken
     * @param hour Hour in which observations were taken
     * @param minute Minute in which observations were taken
     * @param directory Directory of the data files
     * @return Returns file path to mdf
     */
    public String createFileName (int year, int month, int day, int hour, int minute, String directory)
    {
        return String.format("%s/%04d%02d%02d%02d%02d.mdf",directory, year, month, day, hour, minute);
    }
    /**
     * Pulls column titles out of a string and associates them with column numbers. used with treemap
     * @param inParamStr String containing column titles
     */
        private void parseParamHeader (String inParamStr)
    {
            String [] params = new String [30];
            params = inParamStr.split("\\s+");
            //iterate through storage array, trim out white space, then associate key with position.
            for (int index = 0; index < params.length; ++index)
            {
                params[index] = params[index].trim();
                paramPositions.put(params[index], index);
                
            }
            
            
            
    }
        /**
         * 
         * @param inParamStr
         * @return
         */
        public Integer getIndexOf (String inParamStr)
        {
            return paramPositions.get(inParamStr);
        }
        /**
         * Calculates all statistics for all param headings
         */
        private void calculateAllStatistics()
        {
            double total;
            int numberOfObservations;
            double average;
            double min;
            double max;
            String stidMax;
            String stidMin;
            
            //for each param (which are the keys) that is being assessed, loop through and calculate statistics
            for (String key: dataCatalog.keySet())
            {
                total = 0;
                numberOfObservations = 0;
                min = Double.MAX_VALUE;
                max = -Double.MAX_VALUE;
                average = 0;
                stidMax = "Failed Assignment";
                stidMin = "Failed Assignment";                
                
                
                // now iterate over arraylist of observations and calculate values
                for (Observation obs: dataCatalog.get(key))
                {   
                    //if the observation is valid, then include it in statistical analysis
                    if (obs.isValid())
                    {   
                        //increment number of observations being included and add their values to the total
                        numberOfObservations++;
                        total += obs.getValue();
                        
                        //if larger than the current max, reassign
                        if (obs.getValue() > max)
                        {
                            max = obs.getValue();
                            stidMax = obs.getStid();
                        }
                        //if smaller than current min, reassign
                        if (obs.getValue() < min)
                        {
                            min = obs.getValue();
                            stidMin = obs.getStid();
                        }
                        
                    }
                }
                
                average = total / numberOfObservations;
                Statistics obsAverage = new Statistics (average, MESONET, utcDateTime, numberOfObservations, StatsType.AVERAGE);
                Statistics obsMax= new Statistics (max, stidMax, utcDateTime, numberOfObservations, StatsType.MAXIMUM);
                Statistics obsMin= new Statistics (min, stidMin, utcDateTime, numberOfObservations, StatsType.MINIMUM);
                
                statistics.get(StatsType.AVERAGE).put(key, obsAverage);
                statistics.get(StatsType.MINIMUM).put(key, obsMin);
                statistics.get(StatsType.MAXIMUM).put(key, obsMax);
                
                if (key.compareToIgnoreCase(SRAD) == 0)
                {
                    Statistics obsTotal = new Statistics (total, MESONET, utcDateTime, numberOfObservations, StatsType.TOTAL);
                    statistics.get(StatsType.TOTAL).put(key, obsTotal);
                }
                
            }
        }
        
      /**
       * For now, just calls calculateAllStatistics
       */
        private void calculateStatistics()
        {
            calculateAllStatistics();
        }
        /**
         * Prepares dataCatalog and statistics for use
         */
        private void prepareDataCatalog()
        {
            ArrayList <Observation> TAIRdata = new ArrayList <Observation> ();
            ArrayList <Observation> TA9Mdata = new ArrayList <Observation> ();
            ArrayList <Observation> SRADdata = new ArrayList <Observation> ();
            ArrayList <Observation> PRESdata = new ArrayList <Observation> ();
            ArrayList <Observation> WSPDdata = new ArrayList <Observation> ();
            
            dataCatalog.put (TAIR, TAIRdata);
            dataCatalog.put (TA9M, TA9Mdata);
            dataCatalog.put (SRAD, SRADdata);
            dataCatalog.put (WSPD, WSPDdata);
            dataCatalog.put (PRES, PRESdata);
            
            
            TreeMap<String, Statistics> averageMap = new TreeMap<String, Statistics>();
            TreeMap<String, Statistics> maxMap = new TreeMap<String, Statistics>();
            TreeMap<String, Statistics> minMap = new TreeMap<String, Statistics>();
            TreeMap<String, Statistics> totalMap = new TreeMap<String, Statistics>();
            
            statistics.put(StatsType.AVERAGE, averageMap);
            statistics.put(StatsType.MAXIMUM, maxMap);
            statistics.put(StatsType.MINIMUM, minMap);
            statistics.put(StatsType.TOTAL, totalMap);
            
            
        }
        /**
         * Reads in a mesonet file, parses it out and then pulls air temperature, temperature at 9m, and solar radiation measurements from an mdf
         * @throws IOException
         */
    public void parseFile() throws IOException
    {
        String [] parsedLine = new String [25];
        BufferedReader br = new BufferedReader(new FileReader(this.fileName));
    
        String labels;
        String strg;
        
        //throw away header lines
        br.readLine();
        br.readLine();
        
        
        //time stamps
        
        
        labels = br.readLine();
        labels = labels.trim();
        //get param headers
        parseParamHeader(labels);
        
        //first real line
        strg = br.readLine();
        strg = strg.trim();
        numberOfStations += 1;
        
        while(strg!=null)
        {   
            strg = strg.trim();
            parsedLine = strg.split("\\s+");
            
            
            Observation tAirTemp = new Observation (Double.parseDouble(parsedLine[getIndexOf(TAIR)]), parsedLine[getIndexOf(STID)]);
            Observation ta9mTemp = new Observation (Double.parseDouble(parsedLine[getIndexOf(TA9M)]), parsedLine[getIndexOf(STID)]);
            Observation sradTemp = new Observation (Double.parseDouble(parsedLine[getIndexOf(SRAD)]), parsedLine[getIndexOf(STID)]);
            Observation WSPDTemp = new Observation (Double.parseDouble(parsedLine[getIndexOf(WSPD)]), parsedLine[getIndexOf(STID)]);
            Observation PRESTemp = new Observation (Double.parseDouble(parsedLine[getIndexOf(PRES)]), parsedLine[getIndexOf(STID)]);
            
            
            dataCatalog.get(TAIR).add(tAirTemp);
            dataCatalog.get(TA9M).add(ta9mTemp);
            dataCatalog.get(SRAD).add(sradTemp);
            dataCatalog.get(WSPD).add(WSPDTemp);
            dataCatalog.get(PRES).add(PRESTemp);
            
            strg = br.readLine();
            
            ++numberOfStations;
        }
        
        //close reader
        br.close();
        
        calculateStatistics();
        
    }

    
    /**
     * @return String representation of MapData object
     */
    public String toString ()
    {
        String output ="";
        String equalsLine = "";
        //57 equal signs...
        for (int count = 0; count < 51; ++count)
        {
            equalsLine += "=";
        }
        equalsLine += "\n";
        //second line
        //tring.format("=== %04d-%02d-%02d %02d:%02d ===\n", this.year, this.month, this.day, this.hour, this.minute);
        output += equalsLine +"==="+ utcDateTime.toZonedDateTime().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm z")) + "===\n";
        
        output+= equalsLine; //looking for tair max value and stid
        output += "Maximum Air Temperature [1.5m] = " + statistics.get(StatsType.MAXIMUM).get(TAIR).getValue() + " C at " + statistics.get(StatsType.MAXIMUM).get(TAIR).getStid();
        output += "\nMinimum Air Temperature [1.5m] = " + statistics.get(StatsType.MINIMUM).get(TAIR).getValue() + " C at " + statistics.get(StatsType.MINIMUM).get(TAIR).getStid();
        output += "\nAverage Air Temperature [1.5m] = " + String.format("%.1f", statistics.get(StatsType.AVERAGE).get(TAIR).getValue()) + " C at " + statistics.get(StatsType.AVERAGE).get(TAIR).getStid() + "\n";
        //57 equal signs times 2 lines
        output += equalsLine;
        output += equalsLine;
        
        output += "Maximum Air Temperature [9.0m] = " + statistics.get(StatsType.MAXIMUM).get(TA9M).getValue() + " C at " + statistics.get(StatsType.MAXIMUM).get(TA9M).getStid();
        output += "\nMinimum Air Temperature [9.0m] = " + statistics.get(StatsType.MINIMUM).get(TA9M).getValue() + " C at " + statistics.get(StatsType.MINIMUM).get(TA9M).getStid();
        output += "\nAverage Air Temperature [9.0m] = " + String.format("%.1f", statistics.get(StatsType.AVERAGE).get(TA9M).getValue())  + " C at " + statistics.get(StatsType.AVERAGE).get(TA9M).getStid() + "\n";
        
        //57 equal signs times 2 lines
        output += equalsLine;
        output += equalsLine;
        
        output += "Maximum Solar Radiation [1.5m] = " + statistics.get(StatsType.MAXIMUM).get(SRAD).getValue() + " W/m^2 at " + statistics.get(StatsType.MAXIMUM).get(SRAD).getStid();
        output += "\nMinimum Solar Radiation [1.5m] = " + statistics.get(StatsType.MINIMUM).get(SRAD).getValue() + " W/m^2 at " + statistics.get(StatsType.MINIMUM).get(SRAD).getStid();
        output += "\nAverage Solar Radiation [1.5m] = " + String.format("%.1f", statistics.get(StatsType.AVERAGE).get(SRAD).getValue())  + " W/m^2 at " + statistics.get(StatsType.AVERAGE).get(SRAD).getStid() + "\n";
        
        output+= equalsLine;
        return output;
    }
    /**
     * 
     * @param type of Enum StatsType
     * @param paraId 
     * @return the statistic with the corresponding StatsType and ID
     */
    public Statistics getStatistics (StatsType type, String paraId)
    {
        return statistics.get(type).get(paraId);
    }
    /**
     * @return the dateTime of the statistic
     */
    public GregorianCalendar getUtcDateTime()
    {
        return utcDateTime;
    }
    
}
