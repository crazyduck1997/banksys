package com.qf.service;


import com.qf.vo.TransferVo;

import java.util.Date;
import java.util.List;

public interface TransferService {

   public void add(String sourceCardId, String descCardId, Double money);

   public List<TransferVo> findAll(Date beginTime, Date endTime, String cardId,Integer page, Integer limit);


}
