package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TaotaoResult;
import com.taotao.rest.service.RediusService;
import com.taotao.util.ExceptionUtil;

@Controller
@RequestMapping("/cache/sync")
public class RediusController {

    @Autowired
    private RediusService rediusService;

    @RequestMapping("/{contentId}")
    @ResponseBody
    public TaotaoResult SyncContentById(@PathVariable long contentId) {
        try {
            rediusService.contentCacheSync(contentId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
