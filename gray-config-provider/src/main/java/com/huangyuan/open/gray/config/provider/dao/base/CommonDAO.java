package com.huangyuan.open.gray.config.provider.dao.base;

import com.huangyuan.open.gray.base.common.BaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 切换对应数据源
 *
 * @param <T>
 */
public abstract class CommonDAO<T extends Serializable> extends BaseDao<T> {

    @Override
    @Resource(name = "sqlSessionFactory")
    public void setSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionTemplate(new SqlSessionTemplate(sqlSessionFactory));
    }
}
