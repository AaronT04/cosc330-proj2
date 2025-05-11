package com.at04.touchmovetest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
*   AttackParameter stores the list of parameters for an attack.
*   It also forces Firebase to serialize/deserialize everything as a "double".
*   Otherwise, it sometimes saves numbers as "long", which cannot be cast to "float", causing an error.
*/

public class AttackParameter implements Serializable {
    public String type;
    public String objectType = "Object";
    public Object value;
    public double numValue;
    public String description;

    //Empty default constructor is required for firebase storage
    public AttackParameter() {}
    public AttackParameter(String type, Object value) {
        this.type = type;
        this.value = value;
        if(value instanceof Number) {wrapNumber((Number)value);}
        if(value instanceof Boolean) {objectType = "Boolean";}
    }
    public AttackParameter(String type, Object value, String description) {
        this(type, value);
        this.description = description;
    }
    private void wrapNumber(Number num) {
        objectType = "Number";
        if (num instanceof Integer) {numValue = num.doubleValue();}
        if (num instanceof Double) {numValue = num.doubleValue();}
        if (num instanceof Float) {numValue = num.doubleValue(); }
        if (num instanceof Long) {numValue = num.doubleValue(); }
    }

    private Number unwrapNumber() {
        if(type.equals("int")) {return (int)numValue;}
        if(type.equals("double")) {return (double)numValue;}
        if(type.equals("float")) {return (float)numValue;}
        if(type.equals("long")) {return (long)numValue;}
        return null;
    }
    public Object unwrap() {
        if(objectType.equals("Number")) {
            return unwrapNumber();
        }
        return value;
    }
    public static ArrayList<AttackParameter> flattenLine(Line line) {
        ArrayList<AttackParameter> result = new ArrayList<>();
        result.add(new AttackParameter("float", line.x.min, "xmin"));
        result.add(new AttackParameter("float", line.x.max, "xmax"));
        result.add(new AttackParameter("float", line.y.min, "ymin"));
        result.add(new AttackParameter("float", line.y.max, "ymax"));
        return result;
    }
    public static Line unflattenLine(List<AttackParameter> nums) {
        return new Line(new Range((float)nums.get(0).unwrap(), (float)nums.get(1).unwrap()),
                        new Range((float)nums.get(2).unwrap(), (float)nums.get(3).unwrap()));
    }
}
