package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JediusClientSingle;
import com.taotao.rest.service.ContentService;
import com.taotao.util.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JediusClientSingle jediusClientSingle;

    /**
     * Redius服务器hash名字
     */
    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    /* (non-Javadoc)
     * @see com.taotao.rest.service.ContentService#getContentByCategoryId(long)
     */
    @Override
    public List<TbContent> getContentByCategoryId(long categoryId) {
    	//在redius服务器查找资料
        String rediusResult = jediusClientSingle.hget(INDEX_CONTENT_REDIS_KEY,
                String.valueOf(categoryId));
        List<TbContent> list = new ArrayList<>();
        try {
        	//验证查找出来的结果是否正确
            if (!StringUtils.isBlank(rediusResult)) {
                list = JsonUtils.jsonToList(rediusResult, TbContent.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据内容分类id查询内容列表
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        // 根据内容分类id查询内容列表
        list = contentMapper.selectByExampleWithBLOBs(example);
        // 向缓存中添加内容
        try {
            // 把list转换成字符串
            String listJsonData = JsonUtils.objectToJson(list);
            jediusClientSingle.hset(INDEX_CONTENT_REDIS_KEY,
                    String.valueOf(categoryId), listJsonData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
