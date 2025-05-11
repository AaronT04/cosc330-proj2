package com.at04.touchmovetest;

import java.io.Serializable;

public class TypedNumber implements Serializable {
    public String type;
    public double value;
    //Empty default constructor is required for firebase storage
    public TypedNumber() {}
    public TypedNumber(String type, double value) {
        this.type = type;
        this.value = value;
    }
    public static TypedNumber wrap(Number num) {
        if (num instanceof Integer) {return new TypedNumber("int", num.doubleValue());}
        if (num instanceof Double) {return new TypedNumber("double", num.doubleValue());}
        if (num instanceof Float) {return new TypedNumber("float", num.doubleValue());}
        if (num instanceof Long) {return new TypedNumber("long", num.doubleValue());}
        return null;
    }
    public static Number unwrap(TypedNumber tnum) {
        if(tnum.type.equals("int")) { return (int)tnum.value;}
        if(tnum.type.equals("double")) {return tnum.value;}
        if(tnum.type.equals("float")) {return (float)tnum.value;}
        if(tnum.type.equals("long")) {return (long)tnum.value;}
        return null;
    }
}
