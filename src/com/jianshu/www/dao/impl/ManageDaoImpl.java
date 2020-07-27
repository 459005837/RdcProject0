package com.jianshu.www.dao.impl;

import com.jianshu.www.dao.BaseDao;
import com.jianshu.www.dao.ManagerDao;
import com.jianshu.www.po.Appeal;
import com.jianshu.www.po.Manager;
import com.jianshu.www.po.User;

import java.sql.Timestamp;
import java.time.chrono.MinguoDate;
import java.util.List;

public class ManageDaoImpl extends BaseDao implements ManagerDao {
    @Override
    public void topArticle(int parseInt, Timestamp valueOf) {
        String sql = "update article set article_checked = '每日推荐' ,article_date = ? where article_id = ?";
        Object[] obj = {valueOf,parseInt};
        Update(sql,obj);
    }

    @Override
    public List<User> findUser() {
        String sql = "select * from user ";
        Object[] obj = null;
        List<User> list = Query(sql,obj,User.class);
        return list;
    }

    @Override
    public List<User> findUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        Object[] obj = {username};
        List<User> list = Query(sql,obj,User.class);
        return (list!=null && list.size()>0) ? list : null;
    }

    @Override
    public User findUserById(int parseInt) {
        String sql = "select * from user where id = ? ";
        Object[] obj = {parseInt};
        List<User> list = Query(sql,obj,User.class);
        return list.get(0);
    }

    @Override
    public void sealUser(Integer userId, Timestamp dat) {
        String sql = "update user set user_checked = '封号中' ,user_date = ? where id = ?   ";
        Object[] obj = {dat,userId};
        Update(sql,obj);
    }

    @Override
    public void userSealOK(int parseInt) {
        String sql = "update user set user_checked = '正常' where id = ?";
        Object[] obj = {parseInt};
        Update(sql,obj);
    }

    @Override
    public List<Appeal> findAppealDisplay() {
        String sql = "select * from appeal order by appeal_date desc";
        Object[] obj = null;
        List<Appeal> list = Query(sql,obj,Appeal.class);
        return (list!=null && list.size()>0) ? list : null;
    }

    @Override
    public boolean deleteAppeal(int parseInt) {
        try{
            String sql = "delete from appeal where appeal_id = ?";
            Object[] obj = {parseInt};
            Update(sql,obj);
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Manager login(String username, String encodeByMd5) {
        String sql = "select * from manager where username = ? and password = ?";
        Object[] obj = {username,encodeByMd5};
        List<Manager> list = Query(sql,obj,Manager.class);
        return (list!=null && list.size()>0) ? list.get(0) : null;
    }
}
