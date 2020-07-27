package com.jianshu.www.web.servlet;

import com.jianshu.www.bean.AppealDisplay;
import com.jianshu.www.bean.UserNums;
import com.jianshu.www.po.utilBean.ResultInfo;
import com.jianshu.www.service.ManagerService;
import com.jianshu.www.service.impl.ManagerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/manager/*")
public class ManagerServlet extends BaseServlet {
    ManagerService service = new ManagerServiceImpl();
    /**
     * 设置文章置顶
     */
    public void topArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取文章id
        String articleId = request.getParameter("articleId");
        //调用业务层
        service.topArticle(articleId);
        writeValue(null,response);
    }

    /**
     * 删除文章
     */
    public void deleteArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取文章id
        String articleId = request.getParameter("articleId");
        //调用业务层
        ResultInfo info = service.deleteArticle(articleId);
        writeValue(info,response);
    }

    /**
     * 用户的显示
     */
    public void userDisplay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用业务层
        List<UserNums> list = service.userDisplay();
        writeValue(list,response);
    }

    /**
     * 查找用户
     */
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户名
        String username = request.getParameter("username");
        List<UserNums> list = service.findUser(username);
        writeValue(list,response);
    }

    /**
     * 判断用户是否处于封号状态
     */
    public void findUserSeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        //调用业务层
        ResultInfo info = service.findUserSeal(userId);
        writeValue(info,response);
    }

    /**
     * 用户账号封号处理
     */
    public void sealUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String number = request.getParameter("number");
        //调用业务层
        service.sealUser(userId,number);
        writeValue(null,response);
    }

    /**
     * 用户解封处理
     */
    public void sealUserOK(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        //调用业务层
        ResultInfo info = service.sealUserOK(userId);
        writeValue(info,response);
    }

    /**
     * 用户举报信息查询
     */
    public void appealDisplay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用业务层
        List<AppealDisplay> list  = service.appealDisplay();
        writeValue(list,response);
    }

    /**
     * 删除举报信息
     */
    public void deleteAppeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取id
        String appealId = request.getParameter("appealId");
        ResultInfo info = service.deleteAppeal(appealId);
        writeValue(info,response);
    }

    /**
     * 管理员登录
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取用户和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //调用业务层
        ResultInfo info = service.login(username,password);
        writeValue(info,response);
    }
}
