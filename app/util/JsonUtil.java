package util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
    
    public static String asString( Keyword keyword )
    {
        if ( keyword != null )
        {
            return keyword.getNamespace() + "/" + keyword.getName();
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
    
    public static List<JsonNode> elements( JsonNode node )
    {
        List<JsonNode> result = new LinkedList<JsonNode>();
        
        if ( node.isArray() )
        {
            Iterator<JsonNode> iterator = node.iterator();
            
            while ( iterator.hasNext() )
            {
                result.add( iterator.next() );
            }
        }
        
        return result;
    }
}
