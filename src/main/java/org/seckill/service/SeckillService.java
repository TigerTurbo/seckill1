package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.seckillExecuetion;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.seckillCloseException;
import org.seckill.exception.seckillException;

import java.util.List;

//设计时，站在使用者的角度设计接口
/*1.方法定义粒度
* 2.参数，简练直接
* 3.返回类型/异常，友好
* */
public interface SeckillService {
    /*
    * 获取秒杀列表*/
    List<Seckill> getSeckillList();
    /*
    * 查询单个秒杀记录*/
    Seckill getById(Long seckillId);
    /*
    * 秒杀开启时输出秒杀接口地址
    * 否则输出秒杀开启时间与系统时间*/
    Exposer exportSeckillUrl(long seckilledId);
    /*执行秒杀操作*/
    seckillExecuetion executeSeckill(long seckillId, long userPhone,String md5)
    throws RepeatKillException,seckillCloseException,seckillException;







}
