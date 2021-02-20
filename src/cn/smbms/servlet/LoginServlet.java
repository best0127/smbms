package cn.smbms.servlet;

import cn.smbms.pojo.SmbmsUser;
import cn.smbms.service.SmbmsUserService;
import cn.smbms.service.SmbmsUserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 登录控制器
 * @author Tang
 *
 */

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {

    //

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置中文字符编码
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        //用于直接返回字符串数据给前端的流
        PrintWriter out = response.getWriter();

        //获取当前请求的识别
        String method = request.getParameter("method");

        //创建service对象
        SmbmsUserService userService = new SmbmsUserServiceImpl();

        if("login".equals(method)){
            //进入登录功能编写
            String userCode = request.getParameter("userCode");
            String userPassword = request.getParameter("userPassword");


            try {
                //把账号密码送到数据库做判断
                SmbmsUser user = userService.login(userCode,userPassword);

                //保持登录
                request.getSession().setAttribute("userSession",user);

                //登录成功后重定向进入主页
                response.sendRedirect("jsp/frame.jsp");

            } catch (Exception e) {
                e.printStackTrace();

                //让前端显示错误
                request.setAttribute("error",e.getMessage());

                //转发到登录页面
                request.getRequestDispatcher("login.jsp").forward(request,response);
            }
        }else if("logout".equals(method)){
            //退出功能编写
            //把session中的登录状态删除
            request.getSession().removeAttribute("userSession");
            //退出登陆后回到登录页面
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //当有请求进入post的时候，把该请求转向get方法中
        this.doGet(request,response);
    }
}
