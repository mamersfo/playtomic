package controllers;

import static models.Repository.createEntity;
import static models.Repository.db;
import static models.Repository.deleteEntity;
import static models.Repository.entities;
import static models.Repository.updateEntity;

import java.util.LinkedList;
import java.util.List;

import models.Organization;
import models.Repository;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import actions.BasicAuth;
import datomic.Entity;

@BasicAuth
public class Organizations extends Controller
{
    public static Result list() 
    {   
        List<Organization> list = new LinkedList<Organization>();
        
        for ( Entity entity : entities( "[:find ?e :in $ :where [?e :orgtype]]", db() ) )
        {
            list.add( new Organization( entity ) );
        }
        
        return ok( Json.toJson( list ) );
    }
    
    public static Result history()
    {
        return ok( Json.toJson( Repository.history( ":orgtype" ) ) );
    }

    public static Result find( Long id )
    {
        Entity entity = db().entity( id );
        
        if ( entity != null )
        {
            return ok( Json.toJson( new Organization( entity ) ) );
        }
        
        return notFound();
    }

    public static Result create()
    {
        try
        {
            Organization organization = new Organization( request().body().asJson() );
            return created( Json.toJson( new Organization( createEntity( organization.asMap() ) ) ) );
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
            Organization organization = new Organization( request().body().asJson() );
            updateEntity( id, organization.asMap() );            
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
