package actions;

import java.math.BigInteger;
import java.security.MessageDigest;

import models.Repository;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import util.AppException;
import datomic.Entity;

/**
 * @author martin
 * Based on http://digitalsanctum.com/2012/06/07/basic-authentication-in-the-play-framework-using-custom-action-annotation/
 *
 */
public class BasicAuthAction extends Action<BasicAuth>
{
    private static final String AUTHORIZATION = "authorization";
    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String REALM = "Basic realm=\"default\"";

    @Override
    public Result call( Http.Context context ) throws Throwable
    {
        String authHeader = context.request().getHeader( AUTHORIZATION );
        
        if (authHeader == null)
        {
            context.response().setHeader( WWW_AUTHENTICATE, REALM );
            return unauthorized();
        }

        String auth = authHeader.substring( 6 );
        byte[] decodedAuth = new sun.misc.BASE64Decoder().decodeBuffer( auth );
        String[] credString = new String( decodedAuth, "UTF-8" ).split( ":" );

        if (credString == null || credString.length != 2)
        {
            return unauthorized();
        }

        Entity user = Repository.entity(
                "[:find ?e :in $ ?u ?p :where [?e :username ?u][?e :password ?p]]",
                Repository.db(), credString[0], toMd5( credString[1] ) );
        
        if ( user == null ) return unauthorized();
        
        context.args.put( ":user", user );
                
        return delegate.call( context );
    }
    
    public static String toMd5( String string )
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            md.reset();
            md.update( string.getBytes() );
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger( 1, digest );
            return bigInt.toString( 16 );
        }
        catch( Exception e )
        {
            throw new AppException( "Error calculating MD5 hash", e );
        }
    }
    
    public static void main( String[] args )
    {
        System.out.println( toMd5( "hallohallo" ) );
    }
}