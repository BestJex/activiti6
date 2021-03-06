package com.revert.xc.askForLeave.controller;

import com.revert.platform.common.base.model.WebResult;
import com.revert.platform.common.constant.StaticsData;
import com.revert.xc.activitit.util.activiti.skipNode.ActSkipNodeUtil;
import com.revert.xc.askForLeave.model.AskForLeave;
import com.revert.xc.askForLeave.service.AskForLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * xiecong
 */
@RestController
@RequestMapping("/api/v1/askForLeave")
public class AskForLeaveController {

    @Autowired
    private AskForLeaveService askForLeaveService;

    @GetMapping("/all")
    public WebResult getAll(AskForLeave askForLeave){
        WebResult webResult = new WebResult();
        List<AskForLeave> listAFL = askForLeaveService.selectByProperties(askForLeave);
        webResult.setData(listAFL);
        return webResult;
    }

    //新增请假单
    @PostMapping
    public WebResult save(AskForLeave askForLeave){
        WebResult webResult = new WebResult();
        Map<String, Object> res = askForLeaveService.saveAskForLeave(askForLeave);
        webResult.setData(res);
        return webResult;
    }

    //查询某个实例在哪个节点
    @GetMapping("/queryProInstance")
    public WebResult queryProInstance(@RequestParam("proInstanceId") String proInstanceId){
        WebResult webResult = new WebResult();
        webResult.setData(askForLeaveService.queryTaskNameByProInstanceId(proInstanceId));
        return webResult;
    }

    //提交申请
    @PostMapping("/submitApplyFor")
    public WebResult submitApplyFor(@RequestParam("id") Long id){
        WebResult webResult = new WebResult();
        String msg = askForLeaveService.submitApplyForComplete(id);
        if(!"ok".equals(msg)){
            webResult.setCode("5000");
            webResult.setMessage(msg);
        }
        return webResult;
    }

    //同意
    @PostMapping("/AFLeaveAgree")
    public WebResult AFLeaveAgree(AskForLeave askForLeave){
        WebResult webResult = new WebResult();
        askForLeaveService.AFLeaveAgree(askForLeave);
        return webResult;
    }
    //获取流程定义的所有节点
    @GetMapping("/getAllNode")
    public WebResult getAllNodeByProcessDefinitionId(@RequestParam("processDefinitionId") String processDefinitionId){
        WebResult webResult = new WebResult();
        webResult.setData(askForLeaveService.getAllNode(processDefinitionId));
        return webResult;
    }

    //获取流程定义的所有节点
    @PostMapping("/skipNode")
    public WebResult skipNode(@RequestParam("proInstanceId") String proInstanceId, @RequestParam("toFlowElementId") String toFlowElementId){
        WebResult webResult = new WebResult();
        ActSkipNodeUtil.skipNode(proInstanceId, toFlowElementId);
        return webResult;
    }
}
