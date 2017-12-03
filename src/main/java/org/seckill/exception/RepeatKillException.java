package org.seckill.exception;
/*
* 重复秒杀运行时异常
* */
public class RepeatKillException extends seckillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
