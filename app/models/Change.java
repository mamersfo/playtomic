package models;

import java.util.Date;
import java.util.List;

import clojure.lang.Keyword;

public class Change
{
    public String   type;
    public Long     id;
    public String   attr;
    public Object   val;
    public String   user;
    public Date     dt;
    
    public Change( List<Object> values )
    {
        this.id = (Long)values.get( 0 );
        this.attr = ((Keyword)values.get( 1 )).getName();
        this.val = values.get( 2 );
        this.user = (String)values.get( 3 );
        this.dt = (Date)values.get( 4 );
        this.type = ((Boolean)values.get(5)) ? "+" : "-";
    }
    
    public static class Comparator implements java.util.Comparator<Change>
    {
        @Override
        public int compare( Change o1, Change o2 )
        {
            return o1.dt.compareTo( o2.dt );
        }        
    }
}
