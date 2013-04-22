package util;

import clojure.lang.Keyword;

public class StringUtil
{
    public static String asString( Keyword keyword )
    {
        if ( keyword != null )
        {
            return keyword.getNamespace() + "/" + keyword.getName();
        }
        
        return null;
    }
}
