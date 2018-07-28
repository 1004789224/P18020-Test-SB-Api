package com.ly.repository;

import com.ly.domain.SmsCode;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zw
 * @since 2017-10-08
 */
@Service
public interface SmsCodeRepository extends PagingAndSortingRepository<SmsCode, Long>,
        JpaSpecificationExecutor<SmsCode>, 
        QuerydslPredicateExecutor<SmsCode> {
    SmsCode findSmsCodeByPhoneAndCode(String phone,String Code);
}
