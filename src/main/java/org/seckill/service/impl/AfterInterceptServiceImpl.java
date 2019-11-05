package org.seckill.service.impl;

import org.seckill.service.InterceptService;
import org.springframework.stereotype.Component;

/**
 * @author yangshuo
 * @date 2018/10/11 15:21
 */
@Component
public class AfterInterceptServiceImpl implements InterceptService{
    public void doService() {
        System.out.println("AfterInterceptServiceImpl execute");
    }

    public int getOrder() {
        return 0;
    }
}
