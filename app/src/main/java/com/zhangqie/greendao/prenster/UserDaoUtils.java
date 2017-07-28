package com.zhangqie.greendao.prenster;

import android.util.Log;

import com.zhangqie.greendao.api.GreenApplication;
import com.zhangqie.greendao.entity.User;
import com.zhangqie.greendao.gen.UserDao;

import java.util.List;

/**
 * Created by zhangqie on 2016/3/26.
 *
 * 增删改查操作
 *
 */

public class UserDaoUtils implements GreenDaoChargeRecordImpl<User>{

    private UserDao userDao;

    public UserDaoUtils() {
        userDao= GreenApplication.getInstances().getDaoSession().getUserDao();
    }

    @Override
    public long insert(User data) {
        long add=userDao.insert(data);
        return add;
    }

    /****
     * 指定ID修改信息
     * @param data
     */
    @Override
    public void update(User data) {
        User user=userDao.queryBuilder().where(UserDao.Properties.Id.eq(data.getId())).build().unique();
        if (null!=user){
            userDao.update(data);
        }
        else {
            Log.i("update", "该条件下的数据为空");
        }
    }

    /***
     * 删除全部
     */
    @Override
    public void deleteAll() {
        userDao.deleteAll();
    }

    /***
     * 条件删除
     * @param id
     */
    @Override
    public void deleteWhere(long id) {
        userDao.deleteByKey(id);
    }

    /***
     * 查询全部
     * @return
     */
    @Override
    public List<User> selectAll() {
        List<User> list=userDao.loadAll();
        return null!=list && list.size() > 0 ? list : null;
    }

    /***
     * 模糊查询
     * @param data
     * @return
     */
    @Override
    public List<User> selectWhere(User data) {
        List<User> list=userDao.queryBuilder().where(
          UserDao.Properties.Name.like(data.getName())).build().list();
        return null != list && list.size() > 0 ? list : null;
    }

    /***
     * 唯一查询
     * @param name
     * @return
     */
    @Override
    public User seelctWhrer(String name) {
        User user=userDao.queryBuilder().where(
                UserDao.Properties.Name.eq(name)).build().unique();
        return null!= user ? user : null;
    }

    /***
     * Id查询
     * @param id
     * @return
     */
    @Override
    public List<User> selectWhrer(long id) {
        List<User> users=userDao.queryBuilder().where(
                UserDao.Properties.Id.le(id)).build().list();
        return null != users && users.size()>0 ? users : null;
    }
}
