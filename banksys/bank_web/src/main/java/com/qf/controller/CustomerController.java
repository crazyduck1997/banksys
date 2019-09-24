package com.qf.controller;

import com.qf.common.JsonResult;
import com.qf.entity.Customer;
import com.qf.service.CustomerService;
import com.qf.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @Controller
 * @ResponseBody等价RestController
 */
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public JsonResult login(String cardId,String password,HttpSession session){
            Customer customer = customerService.login(cardId, password);
            session.setAttribute(StrUtils.LOGININFO,customer);
            return new JsonResult(0,null);

    }

    @RequestMapping("/balance.do")
    public JsonResult balance(HttpSession session){
        Customer customer= (Customer)session.getAttribute(StrUtils.LOGININFO);
        if (customer == null){
            throw new RuntimeException("还未登录");
        }
        Double balance = customerService.findBalance(customer.getCardId());
        return new JsonResult(0,balance);
    }

    @RequestMapping("/updatePassword.do")
    public JsonResult updatePassword(String password,HttpSession session){
        Customer customer= (Customer)session.getAttribute(StrUtils.LOGININFO);
        System.out.println(password);
        customer.setPassword(password);
        customerService.updatePassword(customer);
        return new JsonResult(0,"修改完成");
    }
    @RequestMapping("/verifiPassword.do")
    public JsonResult verifiPassword(String oldPwd,HttpSession session){
        Customer customer = (Customer)session.getAttribute(StrUtils.LOGININFO);
        Customer customer1 = customerService.verifiPassword(customer.getCardId());
        if(customer1 == null){
            throw new RuntimeException("还未登录");
        }
        if(!customer1.getPassword().equals(oldPwd)){
            return new JsonResult(0,"2");
        }
        return new JsonResult(0,"1");
    }

    @RequestMapping("/loginOut")
    public void loginOut(HttpSession session, HttpServletRequest request, HttpServletResponse response)throws Exception{
        session.removeAttribute(StrUtils.LOGININFO);
        response.sendRedirect(request.getContextPath()+"/customer.html");
    }

}
