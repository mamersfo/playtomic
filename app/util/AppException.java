package util;

@SuppressWarnings("serial")
public class AppException extends RuntimeException
{
    public AppException( String message )
    {
        super( message );
    }
    
    public AppException( String message, Throwable t )
    {
        super( message, t );
    }
}
