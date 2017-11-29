package org.seckill.dao;

import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

public interface SeckillDao {
    /*
    * 减库存
    * @param seckillId
    * @param killTime
    * @return
    * */
    int reduceNumber(long seckillId, Date killTime);

    //根据id查询秒杀对象
    Seckill queryById(long seckillId);
    //根据偏移量查询商品列表
    List <Seckill> queryAll(int offet,int limit);

}
