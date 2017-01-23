package com.taotao.rest.service;

public interface RediusService {

    /**
     * Sync data for specify contentId in Redius
     * @param contentId
     */
    public void contentCacheSync(long contentId);
}
