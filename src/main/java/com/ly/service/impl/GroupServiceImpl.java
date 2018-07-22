package com.ly.service.impl;

import com.ly.domain.QGroup;
import com.ly.domain.Group;
import com.ly.dto.GroupDto;
import com.ly.helper.MyPage;
import com.ly.model.GroupM;
import com.ly.repository.GroupRepository;
import com.ly.service.GroupService;
import com.ly.vo.query.GroupQueryVo;
import com.ly.vo.form.GroupVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;
    
    @Override
    public MyPage<GroupDto> listPage(GroupQueryVo groupQueryVo) {
        BooleanBuilder where = new BooleanBuilder();


        if (StringUtils.hasText(groupQueryVo.getName())) {
            where.and(QGroup.group.name.like(Expressions.asString("%").concat(groupQueryVo.getName()).concat("%")));
        }


        if (groupQueryVo.getPhone() != null && groupQueryVo.getPhone() > 0 ) {
            where.and(QGroup.group.phone.eq(groupQueryVo.getPhone().longValue()));
        }

        if (StringUtils.hasText(groupQueryVo.getLinkName())) {
            where.and(QGroup.group.linkName.like(Expressions.asString("%").concat(groupQueryVo.getLinkName()).concat("%")));
        }


        Sort sort = new Sort(Sort.Direction.ASC, GroupM.ORDERNUM);
        Pageable page = PageRequest.of(groupQueryVo.getNumber(), groupQueryVo.getSize(), sort);
        Page<Group> componentPage = groupRepository.findAll(where, page);
        return getPageDto(componentPage);
    }

    @Override
    public List<GroupDto> listGroup() {
        return null;//getListDto(groupRepository.getByIsDeleted(0L));
    }

    @Override
    public GroupVo findGroup(Long id) {
        Group group = groupRepository.findById(id).orElse(null);
        GroupVo groupVo = new GroupVo();
        if (group != null){
            BeanUtils.copyProperties(group, groupVo);
            return groupVo;
        }
        return null;
    }

    @Override
    public Long saveGroup(GroupVo groupVo) {
        Group group = new Group();
        BeanUtils.copyProperties(groupVo, group);        
        group.setIsDeleted(0L);
        group.setGmtCreate(new Date());
        group.setGmtModified(new Date());
        return groupRepository.save(group) == null ? 0L : 1L;
    }

    @Override
    public Long updateGroup(GroupVo groupVo) {
        Group group = groupRepository.findById(groupVo.getId()).orElse(null);
        if (group == null) {
            return 0L;
        }
        BeanUtils.copyProperties(groupVo, group);
        return groupRepository.save(group) == null ? 0L : 1L;
    }

    @Override
    public Long del(Long id) {
        Group group = groupRepository.findById(id).orElse(null);
        return groupRepository.save(group) == null ? 0L : 1L;
    }

    private MyPage<GroupDto> getPageDto(Page<Group> componentPage){
        List<GroupDto> groupDtos = new LinkedList<>();
        for (Group group : componentPage.getContent()) {
            GroupDto groupDto = new GroupDto();
            BeanUtils.copyProperties(group,groupDto);
            groupDtos.add(groupDto);
        }
        MyPage<GroupDto> myPage = new MyPage();
        myPage.setContent(groupDtos);
        myPage.setTotalElements(componentPage.getTotalElements());
        return myPage;
    }

    private List<GroupDto> getListDto(List<Group> groupList){
        List<GroupDto> groupDtos = new LinkedList<>();
        for (Group group : groupList) {
            GroupDto groupDto = new GroupDto();
            BeanUtils.copyProperties(group,groupDto);
            groupDtos.add(groupDto);
        }
        return groupDtos;
    }
}