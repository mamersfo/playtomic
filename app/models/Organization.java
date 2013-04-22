package models;

import static models.Keys.DB_ID;
import static models.Keys.DESCRIPTION;
import static models.Keys.ID;
import static models.Keys.NAME;
import static models.Keys.ORGTYPE;
import static models.Keys.PARENT;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import util.JsonUtil;
import util.StringUtil;
import clojure.lang.Keyword;
import datomic.Entity;

public class Organization
{
    public Long         id;
    public String       name;
    public String       description;
    public String       orgtype;
    public Organization parent;

    public Organization( JsonNode node )
    {
        this.name = JsonUtil.getTextValue( node, NAME );
        this.description = JsonUtil.getTextValue( node, DESCRIPTION );   
        this.orgtype = StringUtil.asString( JsonUtil.getKeywordValue( node, ORGTYPE ) );     
        
        if ( node.get( PARENT ) != null )
        {
            Entity entity = JsonUtil.getEntityValue( node.get( PARENT ), ID );
            if ( entity != null ) this.parent = new Organization( entity );
        }
    }
    
    public Organization( Entity entity )
    {
        this.id = (Long)entity.get( DB_ID );
        this.name = (String)entity.get( NAME );
        this.description = (String)entity.get( DESCRIPTION );
        this.orgtype = StringUtil.asString( (Keyword)entity.get( ORGTYPE ) );
        
        Entity temp = (Entity)entity.get( PARENT );
        if ( temp != null ) this.parent = new Organization( temp );
    }
    
    public Map<String,Object> asMap()
    {
        Map<String,Object> result = new HashMap<String,Object>();
        
        if ( this.name != null ) result.put( NAME, name );
        if ( this.description != null ) result.put( DESCRIPTION, description );
        if ( this.orgtype != null ) result.put( ORGTYPE, Keyword.find( this.orgtype ) );
        if ( this.parent != null ) result.put( PARENT, Repository.db().entity( this.parent.id ) );

        return result;
    }
}
