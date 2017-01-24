package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.RediusService;

/**
 * @author WEIQIANG LIANG
 *
 */
@Controller
@RequestMapping("/cache/sync")
public class RediusController {

	@Autowired
	private RediusService rediusService;

	@RequestMapping("/content/{contentId}")
	@ResponseBody
	public TaotaoResult SyncContentById(@PathVariable Long contentId) {
		return rediusService.contentCacheSync(contentId);

	}
}
