package com.zhangqie.greendao.prenster;

import java.util.List;

/**
 * Created by zhangqie on 2016/3/26.
 */
public interface GreenDaoChargeRecordImpl<T> {

    long insert(T data);

    void update(T data);

    void deleteAll();

    void deleteWhere(long id);

    List<T> selectAll();

    List<T> selectWhere(T data);

    T seelctWhrer(String name);

    List<T> selectWhrer(long id);


}
