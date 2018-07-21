package com.ly.helper;


import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhongwei on 26/04/2017.
 */
public class MyJpaBuilder<T> {

    /**
     * 条件列表
     */
    private List<JpaOper> opers;

    /**
     * 构造函数，初始化的条件是and
     */
    public MyJpaBuilder(String key, String oper, Object value) {
        JpaOper so = new JpaOper();
        so.setJoin(JpaOper.AND);
        so.setKey(key);
        so.setOper(oper);
        so.setValue(value);
        opers = new ArrayList<JpaOper>();
        opers.add(so);
    }

    public MyJpaBuilder() {
        opers = new ArrayList<JpaOper>();
    }

    /**
     * 完成条件的添加
     *
     * @return
     */
    public MyJpaBuilder add(String key, String oper, Object value, String join) {
        JpaOper so = new JpaOper();
        so.setKey(key);
        so.setValue(value);
        so.setOper(oper);
        so.setJoin(join);
        opers.add(so);
        return this;
    }

    /**
     * 添加or条件的重载
     *
     * @return this，方便后续的链式调用
     */
    public MyJpaBuilder addOr(String key, String oper, Object value) {
        return this.add(key, oper, value, JpaOper.OR);
    }

    /**
     * 添加and的条件
     *
     * @return
     */
    public MyJpaBuilder add(String key, String oper, Object value) {
        return this.add(key, oper, value, JpaOper.AND);
    }

    public Specification getJpa() {
        Specification<T> specification = new MySpecification<T>(opers);
        return specification;
    }
}
