package com.ly.service;

import com.ly.domain.Group;
import com.ly.dto.GroupDto;
import com.ly.helper.MyPage;
import com.ly.vo.query.GroupQueryVo;
import com.ly.vo.form.GroupVo;
import java.util.List;
/**
 * @author zw
 * @since 2017-11-11
 */
public interface GroupService {

    GroupVo findGroup(Long id);

    Long saveGroup(GroupVo groupVo);

    Long updateGroup(GroupVo groupVo);

    Long del(Long id);

    MyPage<GroupDto> listPage(GroupQueryVo groupQueryVo);

    List<GroupDto> listGroup();

}