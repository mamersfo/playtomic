package controllers;                                                                                                                                     
                                                                                                                                                        import static models.Repository.create;
import static models.Repository.db;
import static models.Repository.entities;
import static models.Repository.update;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.Repository;
import models.User;

import org.codehaus.jackson.JsonNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import actions.BasicAuth;
import actions.BasicAuthAction;
import datomic.Entity;

@BasicAuth
public class Users extends Controller
{
    public static Result createUser()
    {
        try
        {
            JsonNode node = request().body().asJson();
            
            if ( node.get( User.USERNAME_KEY ) == null )
            {
                return badRequest( "Expected username" );
            }
            
            if ( node.get( User.PASSWORD_KEY ) == null )
            {
                return badRequest( "Expected password" );
            }

            Map<String,Object> map = new HashMap<String,Object>();
            map.put( User.USERNAME_KEY, node.get( User.USERNAME_KEY ).getTextValue() );
            map.put( User.PASSWORD_KEY, BasicAuthAction.toMd5( node.get( User.PASSWORD_KEY ).getTextValue() ) );
            
            return created( Json.toJson( new User( create( map ) ) ) );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return badRequest( e.getMessage() );
        }
    }
    
    public static Result updateUser( Long id )
    {
        Entity entity = db().entity( id );
        
        if ( entity != null )
        {
            User user = new User( request().body().asJson() );
            update( id, user.asMap() );            
            return noContent();
        }
        
        return notFound();
    }

    public static Result list() 
    {   
        List<User> users = new LinkedList<User>();
        
        for ( Entity entity : entities( "[:find ?p :in $ :where [?p :username]]", db() ) )
        {
            users.add( new User( entity ) );
        }
        
        return ok( Json.toJson( users ) );
    }
    
    public static Result history()
    {
        return ok( Json.toJson( Repository.history( ":username" ) ) );
    }    
    
    public static Result entity( Long id )
    {
        Entity entity = db().entity( id );
        
        if ( entity != null )
        {
            return ok( Json.toJson( new User( entity ) ) );
        }
        
        return notFound();
    }

    public static Result delete( Long id )
    {
        if ( id != -1L )
        {
            delete( id );            
            return noContent();
        }
        
        return notFound();
    }    
}
