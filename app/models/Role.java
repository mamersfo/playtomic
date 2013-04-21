package models;

import static models.Keys.DESCRIPTION;
import static models.Keys.NAME;
import static models.Keys.ROLETYPE;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import util.AppException;
import clojure.lang.Keyword;
import datomic.Entity;

public class Role
{
    public Long    id;
    public String  name;
    public String  description;
    public String  roletype;
    
    public Role( JsonNode json )
    {
        String temp = null;
        
        if ( json.get( NAME ) != null )
        {
            temp = json.get( NAME ).getTextValue();
            this.name = temp;
        }
        
        if ( json.get( DESCRIPTION ) != null )
        {
            temp = json.get( DESCRIPTION ).getTextValue();
            this.description = temp;
        }
        
        if ( json.get( ROLETYPE ) != null )
        {
            temp = json.get( ROLETYPE ).getTextValue();
            
            Keyword keyword = Keyword.find( temp );
            
            if ( keyword == null )
            {
                throw new AppException( "Illegal keyword: " + temp );
            }
            
            this.roletype = keyword.toString();
        }
    }
    
    public Role( Entity entity )
    {
        this.id = (Long)entity.get( "db/id" );
        this.name = (String)entity.get( NAME );
        this.description = (String)entity.get( DESCRIPTION );
        
        Keyword keyword = (Keyword)entity.get( ROLETYPE );
        
        if ( keyword != null )
        {
            this.roletype = keyword.getNamespace() + "/" + keyword.getName();
        }
    }

    public Map<String,Object> asMap()
    {
        Map<String,Object> result = new HashMap<String,Object>();
        
        if ( name != null ) result.put( NAME, name );
        if ( description != null ) result.put( DESCRIPTION, description );
        if ( roletype != null ) result.put( ROLETYPE, roletype );

        return result;
    }
}
