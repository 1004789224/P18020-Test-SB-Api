package com.ly.helper;

/**
 * Created by zhongwei on 26/04/2017.
 */
public class JpaOper {

    public static final String AND = "and";

    public static final String OR = "or";

    public static final String EQ = "=";

    public static final String NEQ = "!=";

    public static final String EQNULL = "null";

    public static final String NEQNULL = "!null";

    public static final String GT = ">";

    public static final String LT = "<";

    public static final String GTE = ">=";

    public static final String LTE = "<=";

    public static final String LIKE = ":";

    public static final String RLIKE = "l:";

    public static final String LLIKE = ":l";

    public static final String PATTERN = "%";

    /**
     * 操作符的key，如查询时的name,id之类
     */
    private String key;
    /**
     * 操作符的value，具体要查询的值
     */
    private Object value;
    /**
     * 操作符，自己定义的一组操作符，用来方便查询
     */
    private String oper;
    /**
     * 连接的方式：and或者or
     */
    private String join;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }
}
