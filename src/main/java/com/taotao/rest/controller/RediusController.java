package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.RediusService;

@Controller
@RequestMapping("/cache/sync")
public class RediusController {

	@Autowired
	private RediusService rediusService;

	@RequestMapping("/{contentId}")
	@ResponseBody
	public TaotaoResult SyncContentById(@PathVariable long contentId) {
		return rediusService.contentCacheSync(contentId);

	}
}
