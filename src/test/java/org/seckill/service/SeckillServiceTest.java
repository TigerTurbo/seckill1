package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("list={}",seckillList);
    }

    @Test
    public void getById() throws Exception {
        long seckillId=1000;
        Seckill seckill = seckillService.getById(seckillId);
        logger.info("seckill={}",seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId=1000;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer={}",exposer);

    }

    @Test
    public void executeSeckill() throws Exception {
        long seckillId=1000L;
        long userPhone=12345699635L;
        String md5=null;
        seckillService.executeSeckill(seckillId,userPhone,md5);
    }

}