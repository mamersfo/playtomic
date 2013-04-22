package models;

public class Error
{
    public String   message;
    
    public Error( Throwable t )
    {
        this.message = t.getMessage();
    }
}
