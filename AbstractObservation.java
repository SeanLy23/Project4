
public abstract class AbstractObservation
{
    protected boolean valid;
        
    public abstract boolean isValid();
    public AbstractObservation()
    {
        valid = true;
    }
    
}
