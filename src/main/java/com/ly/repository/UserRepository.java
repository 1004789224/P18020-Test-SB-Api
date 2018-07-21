package com.ly.repository;

import com.ly.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zw
 * @since 2017-10-08
 */
@Service
public interface UserRepository extends PagingAndSortingRepository<User, Long>,
        JpaSpecificationExecutor<User>, 
        QueryDslPredicateExecutor<User> {
    List<User> getByIsDeleted(Long isDeleted);
}