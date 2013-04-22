package util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetUtil
{
    public static <I,O> Set<O> map( Iterable <I> iterable, Function<I,O> fun )
    {
        if ( iterable != null ) return map( iterable.iterator(), fun );
        return new HashSet<O>();
    }
    
    public static <I,O> Set<O> map( Iterator <I> iterator, Function<I,O> fun )
    {
        Set<O> result = new HashSet<O>();

        if ( iterator != null && fun != null )
        {
            while ( iterator.hasNext() )
            {
                result.add( fun.apply( iterator.next() ) );
            }
        }

        return result;
    }
}
