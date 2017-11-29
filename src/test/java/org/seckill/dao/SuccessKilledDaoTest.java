package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilled successKilled;
    @Test
    public void insertSuccessKilled() throws Exception {
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long sckillId=0;
        //SuccessKilled successKilled=SuccessKilledDao.;
    }

}