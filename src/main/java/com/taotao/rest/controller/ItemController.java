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

    /**
     * 根据商品id获取商品基本信息
     * 
     * @param itemId
     * @return
     */
    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public TaotaoResult getItemBasicInfo(@PathVariable Long itemId) {
        if (null == itemId || itemId.intValue() == 0) {
            return TaotaoResult.build(500, "Item id cannot be null or 0");
        }
        return itemService.getItemBasicInfo(itemId);
    }

    /**
     * 根据商品id获取商品描述
     * 
     * @param itemId
     * @return
     */
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDesc(@PathVariable Long itemId) {
        if (null == itemId || itemId.intValue() == 0) {
            return TaotaoResult.build(500, "Item id cannot be null or 0");
        }
        return itemService.getItemDesc(itemId);
    }
}
