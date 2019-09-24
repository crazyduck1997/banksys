package com.qf.entity;

import lombok.Data;

@Data
public class Customer {

    private Integer id;
    private String cardId;
    private String password;
    private Integer status;
    private Double balance;

}
