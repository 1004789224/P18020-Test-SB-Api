package com.ly.repository;

import com.ly.domain.Instrument;
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
public interface InstrumentRepository extends PagingAndSortingRepository<Instrument, Long>,
        JpaSpecificationExecutor<Instrument>, 
        QuerydslPredicateExecutor<Instrument> {
}