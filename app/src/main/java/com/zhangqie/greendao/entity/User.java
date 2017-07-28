package com.zhangqie.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangqie on 2016/3/26.
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;//主键  自增长
    @NotNull   //不许为空
    private String name;
    private String age;
    private String content;
    private String times;//用于验证版本更新  添加的字段  包含  @Generated  不得添加 字段信息  编译时自动生成  可添加  set  get

    @Generated(hash = 1496555412)
    public User(Long id, @NotNull String name, String age, String content,
            String times) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.content = content;
        this.times = times;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return this.age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTimes() {
        return times;
    }
    public void setTimes(String times) {
        this.times = times;
    }
}
