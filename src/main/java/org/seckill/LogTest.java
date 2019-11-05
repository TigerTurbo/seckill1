package org.seckill;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangshuo
 * @date 2019/6/13 14:17
 */
public class LogTest {
    public static void main(String[] args){
        Age age = new Age();
        age.setName("li");
        age.setNum(2);
        Human human1 = new Human();
        Human human2 = new Human();
        Human human3 = new Human();
        human1.setSeq(1);
        human1.setAge(age);
        human2.setSeq(2);
        human2.setAge(age);
        human3.setSeq(3);
        human3.setAge(age);
        System.out.println("1:"+human1);
        System.out.println("2:"+human2);
        System.out.println("3:"+human3);
        human1.getAge().setNum(4);
        System.out.println("1:"+human1);
        System.out.println("2:"+human2);
        System.out.println("3:"+human3);
        List list = new ArrayList();
        list.add(human1);
        list.add(human2);
        list.add(human3);
        human1 = new Human();
        human1.setSeq(1234);
        System.out.println("3:"+human3);
    }
}
