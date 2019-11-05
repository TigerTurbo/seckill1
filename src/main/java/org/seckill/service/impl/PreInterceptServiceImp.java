package org.seckill.service.impl;

import org.seckill.service.InterceptService;
import org.springframework.stereotype.Component;

/**
 * @author yangshuo
 * @date 2018/10/11 15:19
 */
@Component
public class PreInterceptServiceImp implements InterceptService{

    public void doService() {
        System.out.println("PreInterceptServiceImp execute");
    }

    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}
