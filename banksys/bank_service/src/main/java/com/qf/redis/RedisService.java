package com.qf.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;


    //向redis中写入余额数据
    public void setBanlance(String key,String cardId,Double balance){
        Jedis jedis = jedisPool.getResource();
        jedis.hset(key,cardId,balance.toString());
        jedis.close();

    }


    //从redis中获取余额
    public Double getBanlance(String key,String cardId){
        Jedis jedis = jedisPool.getResource();
        Double money = null;
        try{
            String balance = jedis.hget(key, cardId);
            money = Double.valueOf(balance);
        }catch (Exception e){

        }
        jedis.close();
        return money;
    }

    //从redis删除余额
    public void delField(String key,String cardId){
        Jedis jedis = jedisPool.getResource();
        jedis.hdel(key,cardId);
        jedis.close();
    }

}
