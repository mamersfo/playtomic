package util;

import java.util.Iterator;

import models.Repository;

import org.codehaus.jackson.JsonNode;

import clojure.lang.Keyword;
import datomic.Entity;

public class JsonUtil
{
    public static Entity getEntityValue( JsonNode node, String key )
    {
        if ( node.get( key ) != null )
        {
            return Repository.db().entity( node.get( key ).getLongValue() );
        }
        
        return null;
    }
    
    public static Keyword getKeywordValue( JsonNode node, String key )
    {
        Keyword result = null;
        
        String temp = getTextValue( node, key );
        
        if ( temp != null )
        {
            result = Keyword.find( temp );
            
            if ( result == null )
            {
                throw new AppException( "Illegal keyword: " + temp );
            }
        }
        
        return result;
    }
    
    public static String getTextValue( JsonNode node, String key )
    {
        if ( node.get( key ) != null )
        {
            return node.get( key ).getTextValue();
        }

        return null;
    }
    
    public static Iterator<JsonNode> getArrayValue( JsonNode node, String key )
    {
        JsonNode value = node.get( key );
        
        if ( value != null )
        {
            if ( value.isArray() )
            {
                return value.iterator();
            }
            else
            {
                throw new AppException( "Expected array for key: '" + key + "'" );                
            }
        }
        
        return null;
    }
}
