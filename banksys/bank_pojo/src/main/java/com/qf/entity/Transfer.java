package com.qf.entity;

import javax.xml.crypto.Data;
@lombok.Data
public class Transfer {

    private Integer id;
    private Data time;
    private Integer cid;
    private Double money;
    private Double balance;
    private String amount;

}
