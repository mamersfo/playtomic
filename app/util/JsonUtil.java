package util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

public class JsonUtil
{
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
