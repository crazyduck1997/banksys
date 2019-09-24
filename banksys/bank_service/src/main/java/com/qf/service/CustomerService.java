package com.qf.service;

import com.qf.entity.Customer;
import com.qf.vo.TransferVo;

import java.util.Date;
import java.util.List;

public interface CustomerService {

    public Customer login(String cardId,String password);


    public Double findBalance(String cardId);

    public void updatePassword(Customer customer);

    public Customer verifiPassword(String cardId);
}
