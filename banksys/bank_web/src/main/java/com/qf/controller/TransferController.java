package com.qf.controller;

import com.github.pagehelper.Page;
import com.qf.common.JsonResult;
import com.qf.entity.Customer;
import com.qf.service.TransferService;
import com.qf.utils.StrUtils;
import com.qf.vo.TransferVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TransferController {

    @Autowired
    TransferService transferService;

    @RequestMapping("/transfer.do")
    public JsonResult transfer(String descCardId, Double money, HttpSession session){
        Customer customer = (Customer)session.getAttribute(StrUtils.LOGININFO);
        if(customer==null){
            throw new RuntimeException("用户未登录");
        }
        transferService.add(customer.getCardId(),descCardId,money);
        return new JsonResult(0,"转账成功");
    }


    @RequestMapping("/transferInfo.do")
    public Map<String, Object> transferInfo(@DateTimeFormat(pattern = "yyyy-MM-dd")Date beginTime, @DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime, Integer page, Integer limit, HttpSession session){
        Customer customer = (Customer)session.getAttribute(StrUtils.LOGININFO);
        Map<String, Object> map = new HashMap<>();
        if(customer == null){
            map.put("code",1);
            map.put("msg","还未登录");
            return map;
        }

        List<TransferVo> list = transferService.findAll(beginTime, endTime, customer.getCardId(),page,limit);
        map.put("code",0);
        map.put("count",((Page)list).getTotal());
        map.put("data",list);
        return map;
    }


}
