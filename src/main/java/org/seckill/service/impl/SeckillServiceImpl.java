package org.seckill.service.impl;

import enums.SeckillStateEnum;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.seckillExecuetion;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.seckillCloseException;
import org.seckill.exception.seckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
@Service
public class SeckillServiceImpl implements SeckillService{
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    //注入service依赖
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    //md5盐值字符串
    private final String slat="r9yp32r'd;[w132awa.dwp;a,2'[]2[2pio#$";
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(Long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill=seckillDao.queryById(seckillId);
        if(seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),endTime.getTime());
        }
        String md5=getMd5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMd5(long seckillId){
        String base=seckillId+"/"+slat;
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    /*
    * 使用注解控制事务方法的优点
    * 1.开发团队可以约定明确标注事务方法的编程风格
    * 2.保证事务方法的执行时间尽可能短，不要穿插其他的网络操作RPC/HTTP请求
    *   或者剥离到事务方法外部
    * 3.不是所有的方法都需要事务，如只有一条修改操作，或者只读操作不需要声明为事务
    *
    * */
    public seckillExecuetion executeSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException, seckillCloseException, seckillException {
        if(md5==null||!md5.equals(getMd5(seckillId))){
            throw new seckillException("秒杀数据被重写");
        }
        Date nowTime=new Date();
        //执行秒杀逻辑：减库存+记录够买行为

        try {
            int updateCount=seckillDao.reduceNumber(seckillId,nowTime);
            if(updateCount<=0){
                //没有更新到记录，秒杀结束
                throw new seckillCloseException("seckill is closed");
            }else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    throw new RepeatKillException("seckilled repeat");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId);
                    return new seckillExecuetion(seckillId, 1, SeckillStateEnum.SUCCESS, successKilled);
                }

            }
        }catch (seckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            //所有编译期异常转化为运行期异常
            throw new seckillException("seckill inner error"+e.getMessage());
        }
    }
}
