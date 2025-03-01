package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huing
 * @date 2025/3/1 14:52
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper categoryMapper;

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = categoryMapper.selectTreeNodes(id);
        //将list转map,以备使用,排除根节点
        Map<String,CourseCategoryTreeDto> mapTemp = courseCategoryTreeDtos
                .stream()
                .filter(item->!id.equals(item.getId()))
                .collect(Collectors.toMap(key -> key.getId(),value-> value,(key1,key2) -> key2));
        //最终返回的list
        List<CourseCategoryTreeDto> categoryTreeDtos = new ArrayList<>();
        courseCategoryTreeDtos.stream()
                .filter(item->!id.equals(item.getId()))
                .forEach(item -> {
                    if (item.getParentid().equals(id)){
                        categoryTreeDtos.add(item);
                    }
                    //找到当前结点的父节点
                    CourseCategoryTreeDto parentCateGory = mapTemp.get(item.getParentid());
                    if (parentCateGory != null){
                        if (parentCateGory.getChildrenTreeNodes() == null){
                            parentCateGory.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                        }
                        //下边开始往ChildrenTreeNodes属性中放子节点
                        parentCateGory.getChildrenTreeNodes().add(item);
                    }
                });

        return categoryTreeDtos;
    }
}
