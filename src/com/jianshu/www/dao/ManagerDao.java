package com.jianshu.www.dao;

import com.jianshu.www.po.Appeal;
import com.jianshu.www.po.Manager;
import com.jianshu.www.po.User;

import java.sql.Timestamp;
import java.util.List;

public interface ManagerDao {
    //设置文章置顶
    void topArticle(int parseInt, Timestamp valueOf);
    //用户信息
    List<User> findUser();
    //根据用户名查找用户
    List<User> findUserByUsername(String username);
    //根据用户id查找用户
    User findUserById(int parseInt);
    //封号处理
    void sealUser(Integer userId, Timestamp dat);
    //解封处理
    void userSealOK(int parseInt);
    //查找用户的举报信息
    List<Appeal> findAppealDisplay();
    //删除举报信息
    boolean deleteAppeal(int parseInt);
    //管理员登录
    Manager login(String username, String encodeByMd5);
}
