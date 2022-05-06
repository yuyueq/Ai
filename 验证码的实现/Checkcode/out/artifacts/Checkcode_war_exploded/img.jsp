<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.awt.*"%>
<%@ page import="java.util.Random" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page contentType="image/jpeg;charset=UTF-8" language="java" %>
<%!
    //随机产生颜色值
    public Color getColor(){
        Random ran =new Random();
        int r = ran.nextInt(256);
        int g = ran.nextInt(256);
        int b = ran.nextInt(256);
        return new Color(r,g,b);
    }
    public String getNum()
    {
        int ran = (int) (Math.random()*9000)+1000;
        return String.valueOf(ran);
    }
%>
<%
    //禁止缓存防止验证码过期
    response.setHeader("pragma","no-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setHeader("Expires","0");
    //绘制验证码
    BufferedImage image = new BufferedImage(80,30,BufferedImage.TYPE_INT_RGB);
    //画笔
     Graphics graphics = image.getGraphics();
     graphics.fillRect(0,0,80,30);
     //绘制干扰曲线
     for(int i=0;i<60;i++){
         Random ran =new Random();
         int xBegin=ran.nextInt(80);
         int yBegin=ran.nextInt(30);
         int xEnd=ran.nextInt(xBegin+10);
         int yEnd=ran.nextInt(yBegin+10);
         graphics.setColor(getColor());
         graphics.drawLine(xBegin,yBegin,xEnd,yEnd);

     }
    graphics.setFont(new Font("seif",Font.BOLD,20));

     graphics.setColor(Color.BLACK);
     String checkCode=getNum();
     StringBuffer sb =new StringBuffer();
     for(int i=0;i<checkCode.length();i++){
         sb.append(checkCode.charAt(i)+" ");
     }

     graphics.drawString(sb.toString(),15,20);
     //验证真实值
     session.setAttribute("CHECKCODE",checkCode);

     //真实的产生图片
    ImageIO.write(image,"jpeg",response.getOutputStream());

    out.clear();
    out = pageContext.pushBody();//将图片放入进去
%>