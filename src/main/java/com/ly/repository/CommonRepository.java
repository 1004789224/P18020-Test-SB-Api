package com.ly.repository;

import com.ly.domain.Common;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zw
 * @since 2017-10-08
 */
@Service
public interface CommonRepository extends PagingAndSortingRepository<Common, Long>,
        JpaSpecificationExecutor<Common>, 
        QuerydslPredicateExecutor<Common> {

    /**
     * 根据名称 获取通用台账里的仪器分类、仪器状态和仪器服务方式
     * TODO 仪器状态只有3个  不可添加修改  直接放在Instrument的字段里???
     * @param code 即InstrumentM中的InstrumentM.GROUP_ID SERVICE_METHOD_ID CATEGROY_ID
     * @return
     */
    List<Common> findCommonsByCodeAndIsDeleted(String code,Long isDeleted);

}