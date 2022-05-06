
<%@ page language="java" contentType="text/html; charset=UTF-8 "
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<title>
  验证码
</title>
<html>
<head>

  <script type="text/javascript" src="jquery-3.3.1.js">
  </script>
  <script type="text/javascript">
    function reloadCheckImg() {

      $("img").attr("src","img.jsp?t="+(new Date().getTime()));

    }
    $(document).ready(function () {
      $("#checkcodeId").blur(function(){
      //校验：文本框输入的值 发送到服务器
        //服务端：获取文本框输入的值，和真实验证码图片中的值对比，并返回验证结果
        var $checkcode =$("#checkcodeId").val();
        $.post(
                "CheckCodeServlet",//服务端地址
                "checkcode="+$checkcode ,
                function(result) {//图片地址
                  var resultHtml = $("<img src=' "+result+" ' height='15' width='15px' />");

                  $("#tip").html(resultHtml);
                }
        );

      });
    });

  </script>
</head>
<body>
验证码：
<input type="text" name="checkcode" id="checkcodeId" size="4"/>

<a href="javascript:reloadCheckImg();"><img src="img.jsp"/></a>
<span id="tip"> </span>

</body>
</html>
