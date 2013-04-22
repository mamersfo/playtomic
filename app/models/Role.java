package models;

import static models.Keys.DB_ID;
import static models.Keys.DESCRIPTION;
import static models.Keys.NAME;
import static models.Keys.ROLETYPE;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import util.JsonUtil;
import util.StringUtil;
import clojure.lang.Keyword;
import datomic.Entity;

public class Role
{
    public Long    id;
    public String  name;
    public String  description;
    public String  roletype;
    
    public Role( JsonNode node )
    {
        this.name = JsonUtil.getTextValue( node, NAME );
        this.description = JsonUtil.getTextValue( node, DESCRIPTION );
        this.roletype = StringUtil.asString( JsonUtil.getKeywordValue( node, ROLETYPE ) );
    }
    
    public Role( Entity entity )
    {
        this.id = (Long)entity.get( DB_ID );
        this.name = (String)entity.get( NAME );
        this.description = (String)entity.get( DESCRIPTION );        
        this.roletype = StringUtil.asString( (Keyword)entity.get( ROLETYPE ) );
    }

    public Map<String,Object> asMap()
    {
        Map<String,Object> result = new HashMap<String,Object>();
        
        if ( this.name != null ) result.put( NAME, this.name );
        if ( this.description != null ) result.put( DESCRIPTION, this.description );        
        if ( this.roletype != null ) result.put( ROLETYPE, Keyword.find( this.roletype ) );

        return result;
    }
}
