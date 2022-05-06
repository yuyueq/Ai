package code.dwx;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/CheckCodeServlet")
public class CheckCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户输入验证码
        String checkcodeClient= req.getParameter("checkcode");
        //真正的验证码值
        String checkcodeServer =(String)req.getSession().getAttribute("CHECKCODE");
        String resultTip = "imgs/n.jpg";
        System.out.println("checkcodeClient"+checkcodeClient);
        System.out.println("checkcodeServer"+checkcodeServer);
        if(checkcodeServer.equals(checkcodeClient)){
            resultTip="imgs/y.jpg";
        }
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.write(resultTip);
        writer.flush();
        writer.close();
    }
}
