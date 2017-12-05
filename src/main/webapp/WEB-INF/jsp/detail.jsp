<%--
  Created by IntelliJ IDEA.
  User: yangshuo
  Date: 2017/12/5
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--引入jstl--%>
<%@include file="../common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
    <%--页面显示部分--%>
    <div class="container">
        <div class="panel panel-default text-center">
             <div class="panel-heading ">
                 <h2>${seckill.name}</h2>
             </div>
            <div class="panel-body">
                <h2 class="text-danger">
                    <%--显示time图标--%>
                    <span class="glyphicon glyphicon-time"></span>
                    <%--展示倒计时--%>
                    <span class="glyphicon" id="seckill-box"></span>
                </h2>

            </div>
        </div>
    </div>
    <%--登录弹出层，输入电话--%>
    <div id="killPhoneModel" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title text-center">
                        <span class="glyphicon glyphicon-phone"></span>
                    </h3>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="手机号" class="from-control"/>
                    </div>

                </div>
                <div class="modal-footer">
                    <%--验证信息--%>
                    <span id="killPhoneMessage" class="glyphicon"></span>
                    <button type="button" id="killPhoneBth" class="btn btn-success">
                        <span class="glyphicon glyphicon-phone"></span>
                        Submit
                    </button>

                </div>

            </div>

        </div>

    </div>

</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%--使用CDN获取公共js http://www.bootcdn.cn--%>
<%--jQuery cookie操作插件--%>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jQuery countDown倒计时插件--%>
<script src="https://cdn.bootcss.com/jquery-countdown/2.0.1/jquery.countdown.min.js"></script>
<%--jQuery cookie操作插件--%>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jQuery countDown倒计时插件--%>
<script src="/resources/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){
        //使用EL表达式传入参数
        seckill.detail.init({
            seckillId : ${seckill.seckillId},
            startTime : ${seckill.startTime.time},
            endTime : ${seckill.endTime.time}
        });
    })
</script>
</html>

