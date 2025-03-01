package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @author huing
 * @date 2025/3/1 14:51
 */
public interface CourseCategoryService {
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
