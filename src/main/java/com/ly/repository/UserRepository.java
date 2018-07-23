package com.ly.repository;

import com.ly.domain.User;
import com.ly.vo.form.UserVo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.transaction.Transactional;
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
     *
     * @return
     */
    User findUserByPasswordAndPhone(String password, String phone);

    @Transactional
    @Modifying
    @Query("update user set password= :newPassword,gmtModified=:gmt_modified " +
            "where id= :id and password= :oldPassword")
    int updatePassword(@Param("id") Long id,
                       @Param("oldPassword") String oldPassword,
                       @Param("newPassword") String newPassword,
                       @Param("gmtModified") Date gmtModified);

    /**
     * 通过手机获得user  密码修改时实时反馈
     *
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);

    @Transactional
    @Modifying
    @Query("update user set name= :name,imgUrl= :imgUrl,idnumber=:idnumber," +
            "gmtModified=:gmtModified where id=:id")
    int updateUser(User user);
}