package com.qf.service.impl;

import com.qf.entity.Customer;
import com.qf.mapper.CustomerMapper;
import com.qf.redis.RedisService;
import com.qf.service.CustomerService;
import com.qf.vo.TransferVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    RedisService redisService;


    @Override
    public Customer login(String cardId, String password) {
        Customer customer = customerMapper.findByCardId(cardId);
        if(customer==null){
           throw new RuntimeException("卡号错误");
        }
        if(!customer.getPassword().equals(password)){
            throw new RuntimeException("密码错误");
        }
        if(customer.getStatus()==2){
            throw new RuntimeException("该账户已被冻结");
        }
        return customer;
    }


    @Override
    public Double findBalance(String cardId) {

        //先看redis中是否缓存了余额数据，如果有直接返回
        //如果没有，从数据库中查询，查到以后放入缓存
        Double balance = redisService.getBanlance("balance", cardId);
        System.out.println(balance);
        if(balance == null){
            Customer customer = customerMapper.findByCardId(cardId);
            if(customer==null){
                throw new RuntimeException("账号不存在");
            }

            balance = customer.getBalance();

            redisService.setBanlance("balance",cardId,balance);

        }
        return balance;
    }

    @Override
    public void updatePassword(Customer customer) {
        customerMapper.update(customer);
    }

    @Override
    public Customer verifiPassword(String cardId) {
        Customer byCardId = customerMapper.findByCardId(cardId);

        return byCardId;
    }


}
