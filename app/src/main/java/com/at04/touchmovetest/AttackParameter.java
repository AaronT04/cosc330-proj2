package com.at04.touchmovetest;

import java.io.Serializable;

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
    Class<Line> aaa = Line.class;

    //Empty default constructor is required for firebase storage
    public AttackParameter() {}
    public AttackParameter(String type, Object value) {
        this.type = type;
        this.value = value;
        if(value instanceof Number) {wrapNumber((Number)value);}
    }
    private void wrapNumber(Number num) {
        objectType = "Number";
        if (num instanceof Integer) {numValue = num.doubleValue();}
        if (num instanceof Double) {numValue = num.doubleValue();}
        if (num instanceof Float) {numValue = num.doubleValue();}
        if (num instanceof Long) {numValue = num.doubleValue();}
    }

    public Number unwrapNumber() {
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
}
