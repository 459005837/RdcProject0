package com.jianshu.www.service;

import com.jianshu.www.bean.AppealDisplay;
import com.jianshu.www.bean.UserNums;
import com.jianshu.www.po.utilBean.ResultInfo;

import java.util.List;

public interface ManagerService {
    //设置文章置顶
    void topArticle(String articleId);
    //删除文章
    ResultInfo deleteArticle(String articleId);
    //用户的显示
    List<UserNums> userDisplay();
    //根据用户名查找用户
    List<UserNums> findUser(String username);
    //判断用户是否已经登录
    ResultInfo findUserSeal(String userId);
    //用户封号处理
    void sealUser(String userId, String number);
    //用户账号解封处理
    ResultInfo sealUserOK(String userId);
    //用户的举报信息
    List<AppealDisplay> appealDisplay();
    //删除举报信息
    ResultInfo deleteAppeal(String appealId);
    //管理员登录
    ResultInfo login(String username, String password) throws Exception;
}
