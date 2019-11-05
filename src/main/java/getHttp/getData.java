package getHttp;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author yangshuo
 * @date 2018/12/10 20:01
 */
public class getData {
    private static Logger logger = LoggerFactory.getLogger(getData.class);

    private static Splitter FAN_XIE = Splitter.on("/");

    private static Splitter DAN_YIN_HAO = Splitter.on("'");

    private static Splitter SHUANG_YIN_HAO = Splitter.on("\"");

    private static Splitter splitter = Splitter.on(">");

    private static Splitter WEN_HAO = Splitter.on("?");

    private static Joiner joiner = Joiner.on(">");

    private static Joiner SHUANG_YIN_HAO_JOINER = Joiner.on("\"");

    private final static String HREF = "href=";

    private final static String SRC = "src=";

    private final static String OPTION = "<option value";

    private final static String DISPLAY_DOCUMENT = "DisplayDocument";

    private final static String URL = "http://www.pat.aero";

    private final static Set URL_SET = Sets.newHashSet();

    private final static Set URL_DOWNLOAD_SET = Sets.newHashSet();

    private final static HttpClient client= HttpClients.createDefault();

    private final static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String []args){
        String url = "http://www.pat.aero/gro/users/login";
        HttpPost httpPost = new HttpPost(url);
        String json = "data[User][username]=CTRIP01&data[User][password]=shanghai";
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        // 构建消息实体
        StringEntity entity = new StringEntity(json, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        // 发送Json格式的数据请求
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        try {
            HttpResponse response=client.execute(httpPost);
            String getUrl = "http://www.pat.aero/gro/main/";
            HttpGet httpGet = new HttpGet(getUrl);
            HttpResponse execute = client.execute(httpGet);
            String startStr = EntityUtils.toString(execute.getEntity(), "utf-8");
            genFile(startStr,"start.html");
            splitDownloadFile(startStr);
        } catch (Exception e) {
            logger.error("主函数捕获异常:{}",e.getMessage());
        }
    }

    /**
     * 修改原本的链接为本地链接
     * @param originFileString
     * @return
     */
    public static String changeFileTrace(String originFileString){
        List<String> newStringList = Lists.newArrayList();
        List<String> originStringList = splitter.splitToList(originFileString);
        // 处理普通的.js .css .doc .pdf .gif格式的文件
        for (int i=0;i<originStringList.size();i++){
            String s = originStringList.get(i);
            newStringList.add(s);
            if (s.contains(".js") || s.contains(".css")
                    || s.contains(".doc") || s.contains(".pdf")
                    || s.contains(".gif") || s.contains(".jpg")){
                // 形如href = "/gro/XX.js" 的地址
                List <String> newStrings = Lists.newArrayList();
                List<String> strings = SHUANG_YIN_HAO.splitToList(s);
                boolean isUrl = false;
                for (int j=0; j< strings.size(); j++){
                    newStrings.add(strings.get(j));
                    if (isUrl){
                        String url = strings.get(j);
                        /*url = url.substring(1);
                        if (url.contains("?")){
                            int index = url.indexOf("?");
                            url = url.substring(0,index);
                        }*/
                        url = genFileName(url);
                        newStrings.set(j,url);
                        isUrl = false;
                    }
                    if (strings.get(j).contains("href=") || strings.get(j).contains("src=")){
                        isUrl = true;
                    }

                }
                String join = SHUANG_YIN_HAO_JOINER.join(newStrings);
                newStringList.set(i,join);
            }
        }

        // 处理javascript:openChange
        for(int i = 0; i<newStringList.size(); i++){
            String s = newStringList.get(i);
            if (s.contains("javascript:openChange")){
                List <String> newStrings = Lists.newArrayList();
                List<String> strings = SHUANG_YIN_HAO.splitToList(s);
                boolean isUrl = false;
                for (int j = 0; j<strings.size(); j++){
                    newStrings.add(strings.get(j));
                    if (isUrl){
                        String s1 = strings.get(j);
                        List<String> partList = DAN_YIN_HAO.omitEmptyStrings().splitToList(s1);
                        String s2 = "";
                        if (partList.get(5).equals("mutNew")){
                            s2 = s2.concat(partList.get(3)).concat(".html");
                        }else if (partList.get(5).equals("mutOld")){
                            s2 = s2.concat(partList.get(3)).concat(partList.get(7)).concat(".html");
                        }
                        newStrings.set(j,s2);
                        isUrl = false;
                    }
                    if (strings.get(j).contains("href=")){
                        isUrl = true;
                    }
                }
                String join = SHUANG_YIN_HAO_JOINER.join(newStrings);
                newStringList.set(i,join);
            }
        }

        // 以.xml 结尾的地址
        for(int i = 0; i<newStringList.size(); i++){
            String s = newStringList.get(i);
            if (s.contains(".xml") || s.contains("fm")){
                List <String> newStrings = Lists.newArrayList();
                List<String> strings = SHUANG_YIN_HAO.splitToList(s);
                boolean isUrl = false;
                for (int j = 0; j<strings.size(); j++){
                    newStrings.add(strings.get(j));
                    if (isUrl){
                        String originUrl = strings.get(j);
                        originUrl = genFileName(originUrl);
                        newStrings.set(j,originUrl);
                        isUrl = false;
                    }
                    if (strings.get(j).contains(OPTION) || strings.get(j).contains("href=")){
                        isUrl = true;
                    }
                }
                String join = SHUANG_YIN_HAO_JOINER.join(newStrings);
                newStringList.set(i,join);
            }
        }

        // 超链接
        for(int i = 0; i<newStringList.size(); i++){
            String s = newStringList.get(i);
            if (s.contains("http://www.")){
                List <String> newStrings = Lists.newArrayList();
                List<String> strings = SHUANG_YIN_HAO.splitToList(s);
                boolean isUrl = false;
                for (int j = 0; j<strings.size(); j++){
                    newStrings.add(strings.get(j));
                    if (isUrl){
                        String originUrl = strings.get(j);
                        /*int start = originUrl.lastIndexOf("/");
                        originUrl = originUrl.substring(start);
                        originUrl = "staticSource" + originUrl;*/
                        originUrl = genFileName(originUrl);
                        newStrings.set(j,originUrl);
                        isUrl = false;
                    }
                    if (strings.get(j).contains("href=")){
                        isUrl = true;
                    }
                }
                String join = SHUANG_YIN_HAO_JOINER.join(newStrings);
                newStringList.set(i,join);
            }
        }

        // 没有标志的链接
        for(int i = 0; i<newStringList.size(); i++){
            String s = newStringList.get(i);
            if (s.contains("<option value=\"/") || s.contains("href=\"/")){
                List <String> newStrings = Lists.newArrayList();
                List<String> strings = SHUANG_YIN_HAO.splitToList(s);
                boolean isUrl = false;
                for (int j = 0; j<strings.size(); j++){
                    newStrings.add(strings.get(j));
                    if (isUrl){
                        String originUrl = strings.get(j);
                       /* originUrl = originUrl.substring(1);
                        originUrl = originUrl + ".html";*/
                       originUrl = genFileName(originUrl);
                       newStrings.set(j,originUrl);
                       isUrl = false;
                    }
                    if (strings.get(j).contains("href=") || strings.get(j).contains(OPTION)){
                        isUrl = true;
                    }
                }
                String join = SHUANG_YIN_HAO_JOINER.join(newStrings);
                newStringList.set(i,join);
            }
        }

        // 替换一处js函数
        for(int i = 0; i<newStringList.size(); i++){
            String s = newStringList.get(i);
            if (s.contains("<select class=\"dropdown\"")){
                List <String> newStrings = Lists.newArrayList();
                List<String> strings = SHUANG_YIN_HAO.splitToList(s);
                boolean isUrl = false;
                for (int j = 0; j<strings.size(); j++){
                    newStrings.add(strings.get(j));
                    if (isUrl){
                        String originUrl = "window.location=this.value;";
                        newStrings.set(j,originUrl);
                        isUrl = false;
                    }
                    if (strings.get(j).contains("onchange=")){
                        isUrl = true;
                    }
                }
                String join = SHUANG_YIN_HAO_JOINER.join(newStrings);
                newStringList.set(i,join);
            }
        }

        return joiner.join(newStringList);
    }



    /**
     * 从文本信息中分离出正确的地址
     * @param startStr
     * @throws Exception
     */
    public static void splitDownloadFile(String startStr) throws Exception{

        List<String> strList = splitter.splitToList(startStr);
        List<String> url = Lists.newArrayList();
        // 判断是不是含有链接的标签
        List<String> urlList = strList.stream().filter(
                s -> s.contains(HREF) || s.contains(SRC) || s.contains(OPTION) || s.contains(".xml") || s.contains(DISPLAY_DOCUMENT))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(urlList)){
            return;
        }
        // 按照双引号进行分割，遇到关键词之后的后一个就是链接
        urlList.stream().forEach(s -> {
            List<String> content = SHUANG_YIN_HAO.splitToList(s);
            boolean isUrl = false;
            for (String str : content){
                if (isUrl){
                    // 保存需要下载的页面
                    if (!URL_SET.contains(str) && !str.equals("#")){
                        url.add(str);
                        URL_SET.add(str);
                    }
                    isUrl = false;
                }
                if (str.contains(HREF) || str.contains(SRC) || str.contains(OPTION) || str.contains("onclick=")){
                    isUrl = true;
                }
            }
        });
        executor.submit(()->{
            downLoadWeb(url);
        });

    }


    public static void downLoadWeb(List<String> url){
        url.stream().forEach(s -> {

            logger.info("******原始地址为：{}",s);

            if (!URL_DOWNLOAD_SET.contains(s)){
                // 访问地址，并下载
                HttpGet httpGet;

                if (s.contains("logout") || StringUtils.isEmpty(s) || s.contains("openImg")){
                    return;
                }

                if (s.contains("DoNothing")
                        || (s.contains("javascript") && !s.contains(".xml") && !s.contains(".fm"))){
                    return;
                }

                if (s.contains("www")){
                    return;
                }else{
                    if (s.contains(".xml") || s.contains(".fm")){
                        s = productXmlUrl(s);
                    }
                    httpGet = new HttpGet(URL+s);
                }
                try {
                    logger.info("访问链接为:{}",httpGet.getURI().toString());
                    HttpResponse execute = client.execute(httpGet);
                    StatusLine statusLine = execute.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode != 200){
                        logger.error("访问错误：{}",s);
                        return;
                    }
                    String startStr = EntityUtils.toString(execute.getEntity(), "utf-8");
                    boolean isSuccess = genFile(startStr, s);
                    if (isSuccess){
                        if (!(s.contains(".js") || s.contains(".css")
                                || s.contains(".pdf") || s.contains(".doc")
                                || s.contains("www") || s.contains(".gif")
                                || s.contains(".jpg"))){
                            executor.submit(()->{
                                try {
                                    splitDownloadFile(startStr);
                                } catch (Exception e) {
                                    logger.info("执行分离文件时失败，{}",e.getMessage());
                                    e.printStackTrace();
                                }
                            });

                        }
                    }else {
                        throw new RuntimeException("生成文件时错误");
                    }

                } catch (Exception e) {
                    logger.error("downLoadWeb 异常：{}",e.getMessage());
                    e.printStackTrace();
                }
            }
            URL_DOWNLOAD_SET.add(s);
        });
    }

    /**
     * 生成想要的文件名
     * @param origin
     * @return
     */
    public static String genFileName(String origin){
        try{
            if (origin.contains("DisplayMenu") || origin.contains(DISPLAY_DOCUMENT) || origin.contains("JumpTo")){
                List<String> partList = DAN_YIN_HAO.splitToList(origin);
                String s = partList.get(1);
                s = s.substring(0,s.indexOf(".")).concat(partList.get(3)).concat(".html");
                return s;
            }

            if (origin.contains("selectDate")){
                int start = origin.lastIndexOf("/");
                int end = origin.lastIndexOf(".");
                origin = origin.substring(start + 1,end);
                origin = "selectDate" + origin + ".html";
                return origin;
            }


            if (origin.contains("www")) {
                int start = origin.lastIndexOf("/");
                origin = origin.substring(start);
                origin = "staticSource" + origin;
                return origin;
            }

            List<String> originList = FAN_XIE.omitEmptyStrings().splitToList(origin);

            if (originList.size()>2){
                String s = originList.get(originList.size() - 2);
                if (s.contains(".xml")){
                    s = s.substring(0,s.indexOf("."));
                    s = s.concat(originList.get(originList.size() - 1)).concat(".html");
                    return s;
                }
            }


            origin = originList.get(originList.size() - 1);

            if (origin.contains(".")){
                String newString = origin.substring(origin.indexOf(".")+1);
                char c = newString.charAt(0);
                if (c>='0' && c<='9'){
                    origin = originList.get(originList.size() -2) + origin + ".html";
                    return origin;
                }
            }


            if (origin.contains("?")){
                int index = origin.indexOf("?");
                origin = origin.substring(0,index);
            }

            if (origin.contains(".xml")){
                int index = origin.indexOf(".");
                origin = origin.substring(0,index);
                origin = origin + ".html";
            }
            if (!origin.contains(".")){
                origin = origin + ".html";
            }

            return origin;
        }catch (Exception e){
            logger.error("生成文件名发生错误：{}",origin);
            throw new RuntimeException("生成文件名发生错误",e);
        }
    }

    // 处理xml格式的链接
    private static String productXmlUrl(String str){

        // docChanges 里的子链接
        if (str.contains("javascript:openChange")){
            List<String> partList = DAN_YIN_HAO.omitEmptyStrings().splitToList(str);

            String s = partList.get(1);
            if (s.equals("grch12Edt.xml")){
                s = "grch12.xml/";
            }else if (s.equals("R901_999Edt.xml")){
                s = "R901_999.xml/";
            }

            if (partList.get(5).equals("mutNew")){
                str = "/gro/documents/menu";
                str = str.concat("/").concat(s).concat(partList.get(3));
            }else if (partList.get(5).equals("mutOld")){
                str = "/gro/documents/oldmenu";
                str = str.concat("/").concat(s).concat(partList.get(3)).concat("/").concat(partList.get(7));
            }
            return str;
        }


        if (str.contains("DisplayMenu") || str.contains(DISPLAY_DOCUMENT) || str.contains("JumpTo")){
            List<String> partList = DAN_YIN_HAO.splitToList(str);
            str = "/gro/documents/menu";
            String s = partList.get(1);
            s = s.substring(0,s.indexOf("."));
            str = str.concat("/").concat(s).concat(".xml/").concat(partList.get(3));
            return str;
        }

        // 左边主菜单拼接
        char c = str.charAt(0);
        if (c != '/'){
            str = "/gro/documents/menu/" + str;
            return str;
        }
        return str;
    }


    public static boolean genFile(String resource, String fileName){

        String target = changeFileTrace(resource);

        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        List<String> fileNames = WEN_HAO.splitToList(fileName);

        fileName = fileNames.get(0);

        fileName = genFileName(fileName);

        try {
            File distFile = new File("D:/aeroTest/"+fileName);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(target));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            logger.info("执行下载文件:{}",fileName);
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
