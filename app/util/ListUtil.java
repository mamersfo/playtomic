package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListUtil
{
    public static <I,O> List<O> map( Iterable <I> iterable, Function<I,O> fun )
    {
        return map( iterable.iterator(), fun );
    }
    
    public static <I,O> List<O> map( Iterator <I> iterator, Function<I,O> fun )
    {
        List<O> result = new ArrayList<O>();

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
