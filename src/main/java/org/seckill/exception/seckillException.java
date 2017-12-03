package org.seckill.exception;

public class seckillException extends RuntimeException {
    public seckillException(String message) {
        super(message);
    }

    public seckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
