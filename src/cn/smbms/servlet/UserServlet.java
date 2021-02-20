package cn.smbms.servlet;

import cn.smbms.pojo.SmbmsUser;
import cn.smbms.service.SmbmsUserService;
import cn.smbms.service.SmbmsUserServiceImpl;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/jsp/user.do")
public class UserServlet extends HttpServlet {
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

        if("query".equals(method)){

            //获取前端的 用户名、用户名选择、当前几页
            String queryname = request.getParameter("queryname");
            String queryUserRole = request.getParameter("queryUserRole");
            String pageIndex = request.getParameter("pageIndex");

            //对以上三个参数做非空处理
            queryname = queryname == null ? "" : queryname;
            queryUserRole = queryUserRole == null ? "0" : queryUserRole;
            int page = pageIndex == null ? 1 : Integer.parseInt(pageIndex);
            int limit = 5;

            //把以上参数封装成 map-->一般公司不用 (VO view Object)
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("queryname",queryname);
            params.put("queryUserRole",queryUserRole);
            //偏移量分页计算公式-->（当前页数-1）*每页显示量
            params.put("page",(page-1)*limit);
            params.put("limit",limit);

            //调用service层
            try {
                //获取页面所需显示的集合信息
                List<SmbmsUser> userList = userService.getUserListByMap(params);

                //获取页面所需总条数
                int count = userService.getUserCountByMap(params);

                //总条数 --> 计算为总页数 --> 总条数21条 每页5条 5页
                //公式 --> (总条数+每页显示条数-1)/每页显示条数
                int totalPageCount = (count+limit-1)/limit;

                //返回前端的 用户集合、用户角色下拉框数据、总页数、总条数
                request.setAttribute("userList",userList);

                request.setAttribute("totalPageCount",totalPageCount);
                request.setAttribute("totalCount",count);
                request.setAttribute("currentPageNo",page);

            } catch (Exception e) {
                e.printStackTrace();
            }

            //转发进入用户管理页面
            request.getRequestDispatcher("userlist.jsp").forward(request,response);
        }else if("ucexist".equals((method))){

            //ajax异步 请求用户名是否存在
            String userCode = request.getParameter("userCode");

            try {
                //把前端的值发送到数据库做验证
                int flag = userService.checkUserCode(userCode);

                //用Map集合封装前端返回的数据格式 json -> 键值对 k,v
                Map<String,String> result = new HashMap<String, String>();
                if(userCode.trim().equals("")){
                    result.put("userCode","empty");
                }else if(flag>0){
                    result.put("userCode","exist");
                }else{
                    result.put("userCode","noexit");
                }

                //异步返回数据（Map对象序列化）
                String json = JSON.toJSONString(result);
                //把json字符串发送到前端
                out.print(json);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("add".equals(method)){

            //添加用户功能
            String userCode = request.getParameter("userCode");
            String userName = request.getParameter("userName");
            String userPassword = request.getParameter("userPassword");
            String gender = request.getParameter("gender");
            String birthday = request.getParameter("birthday");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String userRole = request.getParameter("userRole");


            try {
                //把前端数据转换为Javabean（Java对象）
                SmbmsUser user = new SmbmsUser();
                user.setUserCode(userCode);
                user.setUserName(userName);
                user.setUserPassword(userPassword);
                user.setGender(Integer.parseInt(gender));
                user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
                user.setPhone(phone);
                user.setAddress(address);
                user.setUserRole(Integer.parseInt(userRole));

                //把user对象 存入数据库
                userService.addUser(user);

                //重定向到列表页面
                response.sendRedirect("user.do?method=query");
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}
