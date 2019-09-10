package com.huangyuan.open.gray.base.common;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

/**
 * @author huangy on 2019-09-10
 */
public abstract class BaseSqlSessionDaoSupport extends DaoSupport {

    private SqlSession sqlSession;

    private boolean externalSqlSession;

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        Assert.notNull(sqlSession,
                "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        if (!externalSqlSession) {
            sqlSession = new SqlSessionTemplate(sqlSessionFactory);
        }
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        sqlSession = sqlSessionTemplate;
        externalSqlSession = true;
    }

    public final SqlSession getSqlSession() {
        return sqlSession;
    }
}