package cn.tovi.luainandroid.ModelTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author <a href='mailto:zhaotengfei9@gmail.com'>Tengfei Zhao</a>
 */
public class DataModel {
    /**
     *Lua中好像不分区那么多的数据类型，统一用Number
     */




    public String aString;
    public int anInt;
    public float aFloat;
    public Double aDouble;
    public long aLong;
    public Object aObject;
    public List<String> listString;
    public Map<String, String> maps;
    public String[] aStrings;


    public Person aPerson;

    @Override
    public String toString() {
        return "DataModel{" +
                "aString='" + aString + '\'' +
                ", anInt=" + anInt +
                ", aFloat=" + aFloat +
                ", aDouble=" + aDouble +
                ", aLong=" + aLong +
                ", aObject=" + aObject +
                ", listString=" + listString +
                ", maps=" + maps +
                ", aStrings=" + Arrays.toString(aStrings) +
                ", aPerson=" + aPerson +
                '}';
    }
}
