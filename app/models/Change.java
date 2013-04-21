package models;

import java.util.Date;
import java.util.List;

import clojure.lang.Keyword;

public class Change
{
    public String   type;
    public Long     e;
    public String   a;
    public Object   v;
    public Date     date;
    public String   user;
    
    public Change( List<Object> values )
    {
        this.type = ((Boolean)values.get(0)) ? "+" : "-";
        this.e = (Long)values.get( 1 );
        this.a = ((Keyword)values.get( 2 )).getName();
        this.v = values.get( 3 );
        this.date = (Date)values.get( 4 );
//        this.user = (String)values.get( 5 );
    }
    
    public static class Comparator implements java.util.Comparator<Change>
    {
        @Override
        public int compare( Change o1, Change o2 )
        {
            return o1.date.compareTo( o2.date );
        }        
    }
}
