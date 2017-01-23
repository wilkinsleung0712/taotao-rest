package com.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.dao.JediusClientSingle;
import com.taotao.rest.service.RediusService;
import com.taotao.util.ExceptionUtil;

public class RediusServiceImpl implements RediusService {

	@Autowired
	private JediusClientSingle jediusClientSingle;

	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public TaotaoResult contentCacheSync(long contentId) {

		try {
			// 删除redius服务器的数据 让下次数据自动再存入
			jediusClientSingle.hdel(INDEX_CONTENT_REDIS_KEY, String.valueOf(contentId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

}
