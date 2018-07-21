package com.ly.helper;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by zhongwei on 26/04/2017.
 */
public class MySpecification<T> implements Specification<T> {

    /**
     * 查询的条件列表，是一组列表
     */
    private List<JpaOper> opers;

    public MySpecification(List<JpaOper> opers) {
        this.opers = opers;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        int index = 0;
        //通过resultPre来组合多个条件
        Predicate resultPre = null;
        for (JpaOper op : opers) {
            if (index++ == 0) {
                resultPre = generatePredicate(root, criteriaBuilder, op);
                continue;
            }
            Predicate pre = generatePredicate(root, criteriaBuilder, op);
            if (pre == null) {
                continue;
            }
            if (JpaOper.AND.equalsIgnoreCase(op.getJoin())) {
                resultPre = criteriaBuilder.and(resultPre, pre);
            } else if (JpaOper.OR.equalsIgnoreCase(op.getJoin())) {
                resultPre = criteriaBuilder.or(resultPre, pre);
            }
        }
        return resultPre;
    }

    private Predicate generatePredicate(Root<T> root, CriteriaBuilder criteriaBuilder, JpaOper op) {
        /*
        * 根据不同的操作符返回特定的查询*/
        if (JpaOper.EQ.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.equal(root.get(op.getKey()), op.getValue());
        } else if (JpaOper.GTE.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.ge(root.get(op.getKey()), (Number) op.getValue());
        } else if (JpaOper.LTE.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.le(root.get(op.getKey()), (Number) op.getValue());
        } else if (JpaOper.GT.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.gt(root.get(op.getKey()), (Number) op.getValue());
        } else if (JpaOper.LT.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.lt(root.get(op.getKey()), (Number) op.getValue());
        } else if (JpaOper.LIKE.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.like(root.get(op.getKey()), JpaOper.PATTERN + op.getValue() + JpaOper.PATTERN);
        } else if (JpaOper.RLIKE.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.like(root.get(op.getKey()), op.getValue() + JpaOper.PATTERN);
        } else if (JpaOper.LLIKE.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.like(root.get(op.getKey()), JpaOper.PATTERN + op.getValue());
        } else if (JpaOper.EQNULL.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.isNull(root.get(op.getKey()));
        } else if (JpaOper.NEQNULL.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.isNotNull(root.get(op.getKey()));
        } else if (JpaOper.NEQ.equalsIgnoreCase(op.getOper())) {
            return criteriaBuilder.notEqual(root.get(op.getKey()), op.getValue());
        }
        return null;
    }
}
