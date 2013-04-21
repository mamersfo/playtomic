package controllers;

import static models.Repository.createEntity;
import static models.Repository.db;
import static models.Repository.deleteEntity;
import static models.Repository.entities;
import static models.Repository.updateEntity;

import java.util.LinkedList;
import java.util.List;

import actions.BasicAuth;

import models.Repository;
import models.Role;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import datomic.Entity;

@BasicAuth
public class Roles extends Controller
{
    public static Result list() 
    {   
        List<Role> list = new LinkedList<Role>();
        
        for ( Entity entity : entities( "[:find ?e :in $ :where [?e :roletype]]", db() ) )
        {
            list.add( new Role( entity ) );
        }
        
        return ok( Json.toJson( list ) );
    }
    
    public static Result history()
    {
        return ok( Json.toJson( Repository.history( ":roletype" ) ) );
    }

    public static Result find( Long id )
    {
        Entity entity = db().entity( id );
        
        if ( entity != null )
        {
            return ok( Json.toJson( new Role( entity ) ) );
        }
        
        return notFound();
    }

    public static Result create()
    {
        try
        {
            Role role = new Role( request().body().asJson() );
            return created( Json.toJson( new Role( createEntity( role.asMap() ) ) ) );
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return badRequest( e.getMessage() );
        }
    }
    
    public static Result update( Long id )
    {
        Entity entity = db().entity( id );
        
        if ( entity != null )
        {
            Role role = new Role( request().body().asJson() );
            updateEntity( id, role.asMap() );            
            return noContent();
        }
        
        return notFound();
    }
    
    public static Result delete( Long id )
    {
        deleteEntity( id );            
        return noContent();
    }        
}
