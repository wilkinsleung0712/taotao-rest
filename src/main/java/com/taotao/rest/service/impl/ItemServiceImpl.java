package com.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public TaotaoResult getItemBasicInfo(Long itemId) {
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        // we expected item list should have one item
        return TaotaoResult.ok(item);
    }

}
