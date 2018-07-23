package com.ly.repository;

import com.ly.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * @author zw
 * @since 2017-10-08
 */
@Service
public interface UserRepository extends PagingAndSortingRepository<User, Long>,
        JpaSpecificationExecutor<User>, 
        QuerydslPredicateExecutor<User> {
    /**
     * 登陆检验
     * @return
     */
    User findUserByPasswordAndPhone(String password,String phone);

    @Modifying
    @Query("update user set password= :newPassword,gmt_modified=:gmt_modified " +
            "where id= :id and password= :oldPassword")
    int updatePassword(@Param( "id" ) Long id,
                       @Param( "oldPassword" ) String oldPassword,
                       @Param( "newPassword" ) String newPassword,
                       @Param( "gmt_modified" )Date gmt_modified);

    /**
     * 通过手机获得user  密码修改时实时反馈
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);
}