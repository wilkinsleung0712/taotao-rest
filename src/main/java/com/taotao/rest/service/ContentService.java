package com.taotao.rest.service;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface ContentService {

    /**
     * 根据内容分类id查询内容列表
     * 
     * @param categoryId
     * @return
     */
    public List<TbContent> getContentByCategoryId(long categoryId);
}
