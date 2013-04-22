package util;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import datomic.Entity;

public class ToMany
{
    public static Set<Object> additions( Set<Object> oldVals, Set<Object> newVals )
    {
        if ( oldVals != null )
        {
            if ( newVals != null )
            {
                return convertIfEntities( Sets.difference( newVals, oldVals ) );
            }
        }
        else
        {
            if ( newVals != null )
            {
                return convertIfEntities( newVals );
            }
        }
        
        return new HashSet<Object>();
    }

    public static Set<Object> retractions( Set<Object> oldVals, Set<Object> newVals )
    {
        if ( oldVals != null )
        {
            if ( newVals != null )
            {
                return convertIfEntities( Sets.difference( oldVals, newVals ) );                
            }
            else
            {
                return convertIfEntities( oldVals );
            }
        }
        
        return new HashSet<Object>();
    }

    private static Set<Object> convertIfEntities( Set<Object> set )
    {
        return SetUtil.map( set, new Function<Object,Object>() {

            @Override
            public Object apply( Object arg )
            {
                if ( arg instanceof Entity )
                {
                    return ((Entity)arg).get( "db/id" );
                }
                
                return arg;
            }
        });
    }
}
