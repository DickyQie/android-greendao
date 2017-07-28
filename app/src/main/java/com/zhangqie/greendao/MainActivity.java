package com.zhangqie.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangqie.greendao.api.GreenApplication;
import com.zhangqie.greendao.entity.User;
import com.zhangqie.greendao.gen.UserDao;
import com.zhangqie.greendao.prenster.UserDaoUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textView,textView2;
    UserDao userDao;//方式1
    UserDaoUtils userDaoUtils;//方式2

    int ii=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView(){
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        textView= (TextView) findViewById(R.id.text);
        textView2= (TextView) findViewById(R.id.text_where);

        userDao= GreenApplication.getInstances().getDaoSession().getUserDao();
        userDaoUtils=new UserDaoUtils();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn1:
                ii++;
                User user2=new User();
                user2.setName("白子画"+ii);
                user2.setAge("18");
                user2.setContent("我是");
                user2.setTimes("时间");
               // long a=userDao.insert(user2);
                long a=userDaoUtils.insert(user2);
               if (a>0)
               {
                   Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_LONG).show();
               }
                break;
            case R.id.btn2:
                User user3=new User((long)2,"花千骨","19","你是","3月28日");

             //   userDao.update(user3);

                userDaoUtils.update(user3);
                break;
            case R.id.btn3:
               // userDao.deleteByKey((long)1);
               userDaoUtils.deleteWhere(1);
                //userDaoUtils.deleteAll();
                break;
            case R.id.btn4:
                //List<User> users=userDao.loadAll();
                List<User> users=userDaoUtils.selectAll();
                String username="";
                if (null != users) {
                    for (int i = 0; i < users.size(); i++) {
                        username += users.get(i).getId() + "---"+users.get(i).getName() +"--"+users.get(i).getTimes()+ "\n";
                    }
                    textView.setText(username);
                }

                List<User> users2=userDaoUtils.selectWhrer(2);
                String username1="";
                if (null != users2) {
                    for (int i = 0; i < users2.size(); i++) {
                        username1 += users2.get(i).getId() + "---"+users2.get(i).getName() +"--"+users2.get(i).getTimes()+ "\n";
                    }
                    textView2.setText("where:"+username1);
                }

                break;
        }
    }



}
