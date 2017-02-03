package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

public interface ItemService {
    
    /**
     * @param itemId
     * @return
     */
    public TaotaoResult getItemBasicInfo(Long itemId);
}
