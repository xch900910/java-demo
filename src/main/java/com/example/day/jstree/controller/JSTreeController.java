package com.example.day.jstree.controller;


import com.alibaba.fastjson.JSONArray;
import com.example.day.core.Result;
import com.example.day.core.ResultGenerator;
import com.example.day.jstree.model.JSTreePushInModel;
import com.example.day.jstree.service.JSTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-10-29 14:38
 **/
@RestController
@RequestMapping("/jstree")
public class JSTreeController {
    @Autowired
    private JSTreeService jsTreeService;

    @RequestMapping("/getTree")
    public JSONArray getTree(@RequestParam Integer workspaceId) {
        JSONArray layUiTreeVo = jsTreeService.getTree(workspaceId);
        return layUiTreeVo;
    }

    /**
     * @param inModel
     * @return
     */
    @PostMapping("/pushConfig")
    public Result pushConfig(@RequestBody JSTreePushInModel inModel) {
        jsTreeService.pushConfig(inModel);
        return ResultGenerator.genSuccessResult();
    }

}
