package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao"})
public class SuccessKilledDaoTest {
    @Resource
    private SuccessKilledDao successKilledDao;
    @Test
    public void insertSuccessKilled() throws Exception {
        long seckill=1001L;
        long userPhone=15010234568L;
        int insertnum=successKilledDao.insertSuccessKilled(seckill,userPhone);
        System.out.println(insertnum);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
    }

}