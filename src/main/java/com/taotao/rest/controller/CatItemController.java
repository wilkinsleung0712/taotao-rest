package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.CatItemService;

@Controller
@RequestMapping("/itemcat")
public class CatItemController {

    @Autowired
    private CatItemService catItemService;

    @RequestMapping("/all")
    @ResponseBody
    public MappingJacksonValue getItemCatList(String callback) {
        CatResult list = catItemService.getItemCatList();
        MappingJacksonValue result = new MappingJacksonValue(list);
        result.setJsonpFunction(callback);
        return result;
    }
}
