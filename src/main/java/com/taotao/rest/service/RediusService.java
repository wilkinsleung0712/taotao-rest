package com.taotao.rest.service;

import com.taotao.pojo.TaotaoResult;

public interface RediusService {

    /**
     * Sync data for specify contentId in Redius
     * @param contentId
     */
    public TaotaoResult contentCacheSync(long contentId);
}
