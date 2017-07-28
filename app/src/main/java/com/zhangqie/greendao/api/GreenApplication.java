package com.zhangqie.greendao.api;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zhangqie.greendao.gen.DaoMaster;
import com.zhangqie.greendao.gen.DaoSession;
import com.zhangqie.greendao.util.GreenDaoUtils;
import com.zhangqie.greendao.util.Helper;

/**
 * Created by zhangqie on 2016/3/26.
 */

public class GreenApplication extends Application{

    private Helper mHelper;
    //private DaoMaster.DevOpenHelper mHelper;  //基本使用
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static GreenApplication instances;
    @Override
    public void onCreate() {
        super.onCreate();
        instances=this;
        setDatabase();

    }

    public static GreenApplication getInstances(){
        return instances;
    }

    private void setDatabase(){

        // mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);   //基本的


        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new Helper(new GreenDaoUtils(this));

        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        if (null == mDaoMaster) {
            mDaoMaster = new DaoMaster(db);
        }
        if (null == mDaoSession) {
            mDaoSession = mDaoMaster.newSession();
        }
    }


    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
