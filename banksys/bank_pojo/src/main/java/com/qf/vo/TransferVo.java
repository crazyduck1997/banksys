package com.qf.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TransferVo {

    private Date time;
    private String amount;
    private Integer id;
    private Double income;
    private Double output;
    private Double balance;

}
