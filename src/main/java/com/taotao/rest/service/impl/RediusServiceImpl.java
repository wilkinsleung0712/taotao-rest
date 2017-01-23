package com.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.taotao.rest.dao.JediusClientSingle;
import com.taotao.rest.service.ContentService;
import com.taotao.rest.service.RediusService;

public class RediusServiceImpl implements RediusService {

    @Autowired
    private JediusClientSingle jediusClientSingle;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Override
    public void contentCacheSync(long contentId) {

        // delete exist data value
        jediusClientSingle.hdel(INDEX_CONTENT_REDIS_KEY,
                String.valueOf(contentId));

    }

}
