package org.seckill.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: yangshuo@Ctrip.com
 * @Date: 2018/1/30 17:09
 */
public class testss {
    public static class Name{
        private int n;
        private String a;

        public Name(int n, String a) {
            this.n = n;
            this.a = a;
        }

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "n=" + n +
                    ", a='" + a + '\'' +
                    '}';
        }
    }
    public static void main(String []args){
        List<Name> strings=new ArrayList<Name>();
        List<Name> nameList = new ArrayList<>();
        Name name=new Name(1,"re");
        Name name1=new Name(2,"re");
        strings.add(name1);
        strings.add(name);
        nameList.add(name1);
        nameList.add(name);
        /*name.setA("tr");
        name.setN(2);
        strings.add(name);
        for (Name s: strings) {
            String m=s.getA();
            m="54";
            s.setA(m);
        }*/
        Iterator<Name> iterator = strings.iterator();
        while(iterator.hasNext()){
            Name next = iterator.next();
            if (next.getN() == 1){
                iterator.remove();
            }
        }
        for (Name s:strings) {
            System.out.println("S"+s);
        }
        for (Name s:nameList) {
            System.out.println(s);
        }

        long j = 200L/1000;
        long i = 200L%1000;

        System.out.println(1);
    }
}
