package com.jianshu.www.service.impl;

import com.jianshu.www.bean.AppealDisplay;
import com.jianshu.www.bean.UserNums;
import com.jianshu.www.dao.ManagerDao;
import com.jianshu.www.dao.UserActDao;
import com.jianshu.www.dao.impl.ManageDaoImpl;
import com.jianshu.www.dao.impl.UserActDaoImpl;
import com.jianshu.www.po.Appeal;
import com.jianshu.www.po.Manager;
import com.jianshu.www.po.User;
import com.jianshu.www.po.utilBean.ResultInfo;
import com.jianshu.www.service.ManagerService;
import com.jianshu.www.service.UserActService;
import com.jianshu.www.util.Md5Util;
import org.omg.CORBA.TRANSACTION_UNAVAILABLE;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManagerServiceImpl implements ManagerService {
    ManagerDao dao = new ManageDaoImpl();
    UserActDao daoU = new UserActDaoImpl();
    UserActService service = new UserActServiceImpl();
    /**
     * 设置文章置顶
     */
    @Override
    public void topArticle(String articleId) {
        //获取时间
        java.util.Date date = new java.util.Date();
        Timestamp da = new Timestamp(date.getTime());
        String timeStr = da.toString().substring(0, da.toString().indexOf("."));
        //调用Dao层
        dao.topArticle(Integer.parseInt(articleId),Timestamp.valueOf(timeStr));
    }

    /**
     * 删除文章
     */
    @Override
    public ResultInfo deleteArticle(String articleId) {
        //先设置数据库外键无效
        daoU.setForeignNO();
        boolean flag = daoU.deleteArticle(Integer.parseInt(articleId));
        daoU.setForeignOK();
        ResultInfo info = new ResultInfo(flag);
        if(flag){
            info.setMsg("删除成功！");
        }else{
            info.setMsg("操作异常！");
        }
        return info;
    }

    /**
     * 用户信息的显示
     */
    @Override
    public List<UserNums> userDisplay() {
        //先获得用户的信息
        List<User> list = dao.findUser();
        //根据用户信息配置userNums
        List<UserNums> list1 = new ArrayList<>();
        for(User u : list){
            UserNums user = new UserNums();
            UserNums nums = new UserNums();
            //查询相关信息
            nums = service.findUserNums(u.getId(),u.getId());
            //设置参数
            user.setUserId(u.getId());
            user.setUsername(u.getUsername());
            user.setHeadAddress(u.getHeadAddress());
            user.setFollowsNum(nums.getFollowsNum());
            user.setFansNum(nums.getFansNum());
            user.setArticlesNum(nums.getArticlesNum());
            user.setLikesNum(nums.getLikesNum());
            list1.add(user);
        }
        return list1;
    }

    /**
     * 查找用户
     */
    @Override
    public List<UserNums> findUser(String username) {
        //先获得用户的信息
        List<User> list = dao.findUserByUsername(username);
        //根据用户信息配置userNums
        List<UserNums> list1 = new ArrayList<>();
        if(list == null){
            return null;
        }
        for(User u : list){
            UserNums user = new UserNums();
            UserNums nums = new UserNums();
            //查询相关信息
            nums = service.findUserNums(u.getId(),u.getId());
            //设置参数
            user.setUserId(u.getId());
            user.setUsername(u.getUsername());
            user.setHeadAddress(u.getHeadAddress());
            user.setFollowsNum(nums.getFollowsNum());
            user.setFansNum(nums.getFansNum());
            user.setArticlesNum(nums.getArticlesNum());
            user.setLikesNum(nums.getLikesNum());
            list1.add(user);
        }
        return list1;
    }

    /**
     * 判断用户是否已经被封号
     */
    @Override
    public ResultInfo findUserSeal(String userId) {
        User user = dao.findUserById(Integer.parseInt(userId));
        ResultInfo info = new ResultInfo();
        if("封号中".equals(user.getUserChecked())){
            info.setFlag(true);
        }else{
            info.setFlag(false);
        }
        return info;
    }

    /**
     * 用户封号处理
     */
    @Override
    public void sealUser(String userId, String number) {
        //得到现在的时间然后再加上响应的毫秒值
        Date date1 = new java.util.Date();
        Timestamp da1 = new Timestamp(date1.getTime());
        String timeStr=da1.toString().substring(0, da1.toString().indexOf("."));
        //设置封号到时的时间
        long str = Timestamp.valueOf(timeStr).getTime()+3600*24*1000*Integer.parseInt(number);
        Timestamp dat = new Timestamp(str);
        //封号处理
        dao.sealUser(Integer.parseInt(userId),dat);
    }

    /**
     *用户账号解封处理
     */
    @Override
    public ResultInfo sealUserOK(String userId) {
        ResultInfo info = new ResultInfo();
        User user = dao.findUserById(Integer.parseInt(userId));
        if("正常".equals(user.getUserChecked())){
            info.setMsg("用户账号为正常状态。");
        }else{
            //让用户的状态恢复
            dao.userSealOK(Integer.parseInt(userId));
            info.setMsg("解封成功！");
        }
        return info;
    }

    /**
     * 用户的举报信息
     */
    @Override
    public List<AppealDisplay> appealDisplay() {
        List<Appeal> list1 = dao.findAppealDisplay();
        List<AppealDisplay> list2 = new ArrayList<>();
        if(list1==null){
            return null;
        }
        for(Appeal a : list1){
            //设置姓名
            AppealDisplay ad = new AppealDisplay();
            User user1 = dao.findUserById(a.getUserId());
            ad.setUsername(user1.getUsername());
            User user2 = dao.findUserById(a.getAppealUserId());
            ad.setAppealUsername(user2.getUsername());
            //设置其他
            ad.setAppealId(a.getAppealId());
            ad.setAppealDetail(a.getAppealDetail());
            ad.setAppealPhoto(a.getAppealPhoto());

            list2.add(ad);
        }
        return list2;
    }

    /**
     * 删除举报信息
     */
    @Override
    public ResultInfo deleteAppeal(String appealId) {
        ResultInfo info = new ResultInfo();
        boolean flag = dao.deleteAppeal(Integer.parseInt(appealId));
        if(flag){
            info.setFlag(flag);
            info.setMsg("删除成功！");
        }else{
            info.setFlag(flag);
            info.setMsg("操作异常！");
        }
        return info;
    }

    /**
     * 管理员登录
     */
    @Override
    public ResultInfo login(String username, String password) throws Exception {
        ResultInfo info = new ResultInfo();
        Manager manager = dao.login(username, Md5Util.encodeByMd5(password));
        if(manager!=null){
            info.setFlag(true);
        }else{
            info.setFlag(false);
            info.setMsg("用户名或密码错误");
        }
        return info;
    }
}
