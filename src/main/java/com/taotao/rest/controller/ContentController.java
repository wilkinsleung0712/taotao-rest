package com.taotao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;
import com.taotao.util.ExceptionUtil;

@RestController
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public TaotaoResult getContentByCategoryId(@PathVariable Long contentCategoryId) {

		try {
			List<TbContent> resultList;
			resultList = contentService.getContentByCategoryId(contentCategoryId);
			return TaotaoResult.ok(resultList);
		} catch (Exception e) {
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

	}
}
