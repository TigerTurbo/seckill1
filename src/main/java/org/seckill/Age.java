package org.seckill;

/**
 * @author yangshuo
 * @date 2019/6/13 14:17
 */
public class Age {
    private String name;
    private int num;

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Age{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
