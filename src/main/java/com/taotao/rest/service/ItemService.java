package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

public interface ItemService {
    
    /**
     * 根据商品id获取商品基本信息
     * @param itemId
     * @return
     */
    public TaotaoResult getItemBasicInfo(Long itemId);
    
    /**
     * 根据商品id获取商品描述
     * @param itemId
     * @return
     */
    public TaotaoResult getItemDesc(Long itemId);
}
