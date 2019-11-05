package getHttp;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author yangshuo
 * @date 2018/12/19 18:59
 */
public class javaStream {

    private static Logger logger = LoggerFactory.getLogger(javaStream.class);

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main (String[] args){
        HashMap<String ,Integer> hashMap = Maps.newHashMap();
        for (int i = 0;i<100;i++){
            hashMap.put(String.valueOf(i),i);
        }
        HashMap<String, Integer> countMap = Maps.newHashMap();
        Set<String> strings = hashMap.keySet();
        for (int j= 0;j<100;j++){
            String s = strings.parallelStream().parallel().findAny().get();
            if (countMap.containsKey(s)){
                countMap.put(s,countMap.get(s)+1);
            }else {
                countMap.put(s,1);
            }
            logger.info("得到的字符串为：{}",s);
        }

        List<String> collect = strings.stream().collect(Collectors.toList());

        for (int x = 0;x<100;x++){
            int i = (int)(Math.random() * collect.size());
            logger.info("list中取：{}",collect.get(i));
        }

        /*collect.forEach(s ->{
            switch (s){
                case "55":
                    return;
                case "91":
                    return;
                default:
                    throw new RuntimeException("***");
            }
        });*/

        for (int i=0; i < 10; i++){
            executorService.submit(()->{
                logger.info("执行多线程");
            });
        }
        logger.info("");
    }
}
