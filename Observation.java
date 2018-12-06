
public class Observation extends AbstractObservation
{
    protected double value;
    protected String stid;
    //Rethink the threshold value and location, may be long hig
    private static final double THRESHOLD = -900;
    
    public Observation (double value, String stid)
    {
        this.value = value;
        this.stid = stid;
        
        if (value <= THRESHOLD)
        {
            valid = false;
        }
        else
        {
            valid = true;
        }
    }
    
    public boolean isValid ()
    {
        return valid;
    }
    
    public String getStid() 
    {
        return this.stid;
    }
    
    public double getValue()
    {
        return this.value;
    }
    
    public String toString ()
    {
        return String.format("%s %.2f", this.stid, this.value);
    }
}
