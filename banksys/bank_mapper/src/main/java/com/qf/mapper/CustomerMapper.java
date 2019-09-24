package com.qf.mapper;

import com.qf.entity.Customer;

public interface CustomerMapper {

    public Customer findByCardId(String cardId);

    public void update(Customer customer);


}
