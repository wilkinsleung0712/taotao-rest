package com.taotao.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/itemcat")
public class CatItemController {

	@RequestMapping("/all")
	public Object getItemCatList(String callback) {

		return null;
	}
}
