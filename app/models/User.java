package models;

import static models.Keys.EMAIL;
import static models.Keys.FIRSTNAME;
import static models.Keys.ID;
import static models.Keys.LASTNAME;
import static models.Keys.MIDDLENAME;
import static models.Keys.ROLES;
import static models.Keys.USERNAME;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonNode;

import util.AppException;
import util.Function;
import util.JsonUtil;
import util.SetUtil;
import datomic.Entity;

public class User
{
    public static final Pattern EMAIL_REGEX = Pattern.compile( "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$" );
    
    public Long         id;
    public String       username;
    public String       firstname;
    public String       middlename;
    public String       lastname;
    public String       email;
    public Set<Role>    roles;
    
    public User( JsonNode json )
    {
        this.username = JsonUtil.getTextValue( json, USERNAME );
        this.firstname = JsonUtil.getTextValue( json, FIRSTNAME );
        this.middlename = JsonUtil.getTextValue( json, MIDDLENAME );
        this.lastname = JsonUtil.getTextValue( json, LASTNAME );
        
        String temp = JsonUtil.getTextValue( json, EMAIL );
             
        if ( temp != null )
        {
            if ( EMAIL_REGEX.matcher( temp ).matches() )
            {
                this.email = temp;
            }
            else
            {
                throw new AppException( "Illegal email address: " + temp );
            }
        }
        
        this.roles = SetUtil.map( json.get( ROLES ).iterator(), new Function<JsonNode,Role>() {
           public Role apply( JsonNode node ) {
               return new Role( Repository.db().entity( node.get( ID ).getLongValue() ) );
           }
        });
    }
    
    @SuppressWarnings("unchecked")
    public User( Entity entity )
    {
        this.id = (Long)entity.get( "db/id" );
        this.username = (String)entity.get( USERNAME );
        this.firstname = (String)entity.get( FIRSTNAME );
        this.middlename = (String)entity.get( MIDDLENAME );
        this.lastname = (String)entity.get( LASTNAME );
        this.email = (String)entity.get( EMAIL );
        
        this.roles = SetUtil.map( (Set<Entity>)entity.get( ROLES ), new Function<Entity,Role>() {
           public Role apply( Entity e ) { return new Role( e ); } 
        });
    }
    
    public Map<String,Object> asMap()
    {
        Map<String,Object> result = new HashMap<String,Object>();
        
        if ( username != null ) result.put( USERNAME, username );
        if ( firstname != null ) result.put( FIRSTNAME, firstname );
        if ( middlename != null ) result.put( MIDDLENAME, middlename );
        if ( lastname != null ) result.put( LASTNAME, lastname );
        if ( email != null ) result.put( EMAIL, email );
        
        result.put( ROLES, SetUtil.map( this.roles, new Function<Role,Entity>() {
            public Entity apply( Role role ) { return Repository.db().entity( role.id ); }
        }));
        
        return result;
    }
}
