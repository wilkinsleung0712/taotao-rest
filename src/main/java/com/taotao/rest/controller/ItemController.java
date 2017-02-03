package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    
    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public TaotaoResult getItemBasicInfo(@PathVariable Long itemId) {
        return itemService.getItemBasicInfo(itemId);
    }
}
