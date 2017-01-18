package com.taotao.rest.service;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface ContentService {

	/**
	 * 提取广告类别相关的列表
	 * @param contentId - 广告类别号
	 * @return - 广告类别列表
	 */
	List<TbContent> getTbContentListByContentId(long contentId);

}