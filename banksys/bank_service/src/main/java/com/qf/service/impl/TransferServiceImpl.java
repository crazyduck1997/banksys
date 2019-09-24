package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.qf.entity.Customer;
import com.qf.entity.Transfer;
import com.qf.mapper.CustomerMapper;
import com.qf.mapper.TransferMapper;
import com.qf.redis.RedisService;
import com.qf.service.TransferService;
import com.qf.vo.TransferVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    TransferMapper transferMapper;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    RedisService redisService;

    @Override
    public void add(String sourceCardId, String descCardId, Double money) {
        Customer byCardId = customerMapper.findByCardId(sourceCardId);
        Customer byCardId1 = customerMapper.findByCardId(descCardId);
        if(sourceCardId.equals(descCardId)){
            throw new RuntimeException("卡号不能相同");
        }
        if(byCardId==null){
            throw new RuntimeException("卡号不存在");
        }
        if(byCardId.getStatus()==2){
            throw new RuntimeException("该账户已被冻结");
        }
        if(byCardId.getBalance()<money){
            throw new RuntimeException("卡内余额不足");
        }
        Transfer sourceTransfer = new Transfer();
        sourceTransfer.setMoney(0-money);
        sourceTransfer.setAmount(sourceCardId);
        sourceTransfer.setCid(byCardId.getId());
        sourceTransfer.setBalance(byCardId.getBalance()-money);

        byCardId.setBalance(byCardId.getBalance()-money);
        customerMapper.update(byCardId);
        transferMapper.add(sourceTransfer);

        redisService.delField("balance",sourceCardId);

        Transfer descTransfer = new Transfer();
        descTransfer.setMoney(money);
        descTransfer.setAmount(descCardId);
        descTransfer.setCid(byCardId1.getId());
        descTransfer.setBalance(byCardId1.getBalance()+money);
        byCardId1.setBalance(byCardId1.getBalance()+money);
        customerMapper.update(byCardId1);
        transferMapper.add(descTransfer);

    }
    @Override
    public List<TransferVo> findAll(Date beginTime, Date endTime, String cardId,Integer page, Integer limit) {
        // 设置查询的页码和每页显示的记录数
        // 该语句后面，一定要紧跟着查询用的方法
        PageHelper.startPage(page,limit);
        List<TransferVo> list = transferMapper.findAll(beginTime, endTime, cardId);
        return list;
    }
}
