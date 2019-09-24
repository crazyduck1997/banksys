package com.qf.mapper;

import com.qf.entity.Transfer;
import com.qf.vo.TransferVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TransferMapper {

    public void add(Transfer transfer);

    public List<TransferVo> findAll(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime,@Param("cardId") String cardId);

}
