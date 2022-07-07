package com.ty.service.impl;

import com.ty.dao.TagMapper;
import com.ty.domain.pojo.Tag;
import com.ty.domain.vo.TagVo;
import com.ty.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
