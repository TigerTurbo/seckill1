package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

/*
* 配置spring和juint整合，junit启动时加载SpringIOC容器
* */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉juint spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})

public class SeckillDaoTest {
    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void reduceNumber() throws Exception {
        long id=1000;
        Seckill seckill=seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        //abcdefg
    }
    @Test
    /*java没有保存表述形参的记录*/
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill:seckills) {
            System.out.println(seckill);
        }
    }
    @Test
    public void queryById() throws Exception {
    }



}