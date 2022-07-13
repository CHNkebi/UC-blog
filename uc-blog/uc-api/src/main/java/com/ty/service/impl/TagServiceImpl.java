package com.ty.service.impl;

import com.ty.dao.TagMapper;
import com.ty.domain.http.ResponseResult;
import com.ty.domain.pojo.Tag;
import com.ty.domain.vo.TagVo;
import com.ty.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;


    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {

        List<Tag> tags = tagMapper.selectTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public ResponseResult hots(int limit) {
        /**
         * 1.标签所拥有的文章数量最多 即为 最热标签
         * 2.查询 根据tag_id 分组计数， 从大到小排列 取前limit个
         */
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds))
            return ResponseResult.success(Collections.emptyList());
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return ResponseResult.success(tagList);
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }
    private List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for(Tag tag : tagList)
            tagVoList.add(copy(tag));
        return tagVoList;
    }
}
