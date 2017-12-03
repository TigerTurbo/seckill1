package org.seckill.exception;
/*秒杀关闭异常*/
public class seckillCloseException extends seckillException {
    public seckillCloseException(String message) {
        super(message);
    }

    public seckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
