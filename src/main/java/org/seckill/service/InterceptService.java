package org.seckill.service;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

/**
 * @author yangshuo
 * @date 2018/10/11 15:18
 */
public interface InterceptService extends Ordered {
    void doService();
}
