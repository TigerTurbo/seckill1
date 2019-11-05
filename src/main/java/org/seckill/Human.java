package org.seckill;

/**
 * @author yangshuo
 * @date 2019/6/13 14:17
 */
public class Human {
    private Age age;
    private int seq;

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "Human{" +
                "age=" + age +
                ", seq=" + seq +
                '}';
    }
}
