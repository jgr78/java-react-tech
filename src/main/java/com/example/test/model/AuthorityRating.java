package com.example.test.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.io.Serializable;

/**
 * A single store rating item.
 */
public class AuthorityRating implements Serializable {

     private HashMap<String, Double> values;
     private int total;
     private String schemetype; 

      public AuthorityRating()
      {
          this.values = new HashMap<String, Double>();
          this.total = 0;
          this.schemetype = "FHRS";
      }

    public void createRating() {
        Set<Entry<String,Double>> hashSet=values.entrySet();
        for(Entry entry:hashSet ) {
            double v = ((double) entry.getValue()) * 100.0f / total;
            values.put((String) entry.getKey(), v);
        }
    }

    public double getValue(String text) {
        if(values.containsKey(text)) {
            return values.get(text);
        }
        return 0;
    }

    public String getSchemeType() {
        return schemetype;
    }

    public void setSchemeType(String schemetype) {
        this.schemetype = schemetype;
    }

    public String getValueFormatted(String text) {
        if(values.containsKey(text)) {
            return String.format("%,.2f", values.get(text));
        }
        return "0.00";
    }

    public void addNewValue(String text) {
         double currentvalue = 1; values.get(text);
         if(values.containsKey(text)) {
             currentvalue = values.get(text);
             currentvalue++;
         } 
         total++;
         values.put(text, currentvalue);
    }

    @Override
    public String toString() {
        return values.toString() + "  Total : " + total;
    }
}
