package com.taotao.rest.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
import com.taotao.util.ExceptionUtil;
import com.taotao.util.JsonUtils;

@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult getItemBasicInfo(Long itemId) {

        // 添加缓存逻辑
        // 从缓存中取商品信息，商品id对应的信息
        String jsonData = jedisClient
                .get(REDIS_ITEM_KEY + ":" + itemId + ":base");
        if (!StringUtils.isBlank(jsonData)) {
            TbItem item = JsonUtils.jsonToPojo(jsonData, TbItem.class);
            return TaotaoResult.ok(item);
        }

        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        // 缓存业务逻辑不应该影响正常的业务,所以需要try catch
        try {
            // 把商品写入缓存
            String objectJsonData = JsonUtils.objectToJson(item);
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base",
                    objectJsonData);
            // 设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base",
                    REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            String stackTrace = ExceptionUtil.getStackTrace(e);
            return TaotaoResult.build(500, stackTrace);
        }
        return TaotaoResult.ok(item);
    }

}
