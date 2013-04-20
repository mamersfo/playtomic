package models;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonNode;

import datomic.Entity;

public class User
{
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String FIRSTNAME_KEY = "firstname";
    public static final String MIDDLENAME_KEY = "middlename";
    public static final String LASTNAME_KEY = "lastname";
    public static final String EMAIL_KEY = "email";
    
    public static final Pattern EMAIL_REGEX = Pattern.compile( "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$" );
    
    public Long     id;
    public String   username;
    public String   firstname;
    public String   middlename;
    public String   lastname;
    public String   email;
    
    public User( JsonNode json )
    {
        String temp = null;
        
        if ( json.get( USERNAME_KEY ) != null )
        {
            temp = json.get( USERNAME_KEY ).getTextValue();
            this.username = temp;
        }
        
        if ( json.get( FIRSTNAME_KEY ) != null )
        {
            temp = json.get( FIRSTNAME_KEY ).getTextValue();
            this.firstname = temp;
        }

        if ( json.get( MIDDLENAME_KEY ) != null )
        {
            temp = json.get( MIDDLENAME_KEY ).getTextValue();
            this.middlename = temp;
        }
        
        if ( json.get( LASTNAME_KEY ) != null )
        {
            temp = json.get( LASTNAME_KEY ).getTextValue();
            this.lastname = temp;
        }
        
        if ( json.get( EMAIL_KEY ) != null )
        {
            temp = json.get( EMAIL_KEY ).getTextValue();
            
            if ( EMAIL_REGEX.matcher( temp ).matches() )
            {
                this.email = temp;
            }
            else
            {
                System.out.println( "no match" );
            }
        }
    }
    
    public User( Entity entity )
    {
        this.id = (Long)entity.get( "db/id" );
        this.username = (String)entity.get( USERNAME_KEY );
        this.firstname = (String)entity.get( FIRSTNAME_KEY );
        this.middlename = (String)entity.get( MIDDLENAME_KEY );
        this.lastname = (String)entity.get( LASTNAME_KEY );
        this.email = (String)entity.get( EMAIL_KEY );
    }
    
    public Map<String,Object> asMap()
    {
        Map<String,Object> result = new HashMap<String,Object>();
        
        result.put( USERNAME_KEY, username );
        result.put( FIRSTNAME_KEY, firstname );
        result.put( MIDDLENAME_KEY, middlename );
        result.put( LASTNAME_KEY, lastname );
        result.put( EMAIL_KEY, email );
        
        return result;
    }
}
