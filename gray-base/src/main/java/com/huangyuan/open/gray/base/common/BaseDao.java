package com.huangyuan.open.gray.base.common;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author huangy on 2019-09-10
 */
public abstract class BaseDao<T extends Serializable> extends
        BaseSqlSessionDaoSupport {

    private static Logger logger  = LoggerFactory.getLogger(BaseDao.class);

    /**
     * 子类直接覆盖，用于设置sqlSessionTemplate
     * 尽量直接设定sessionTemplate，防止配置被覆盖
     *
     */
    public abstract void setSqlSessionTemplate(
            SqlSessionFactory sqlSessionFactory);

    /**
     *
     * @param statement
     * @param list
     * @return
     */
    public int save(final String statement, final List<T> list) {
        Assert.notNull(statement, "mapper statement 不能为空");
        logger.debug("method[save], params[statement:{" + statement + "} ");
        return getSqlSession().insert(statement, list);
    }

    /**
     *
     * @param statement mapper中statement的id
     * @param entity mapper中statement的参数 entity
     * @return 影响的行数
     */
    public int save(final String statement, final T entity) {
        Assert.notNull(statement, "mapper statement 不能为空");
        Assert.notNull(entity, "entity不能为空");
        logger.debug("method[save], params[statement:{" + statement
                + "} ,entity: {" + entity + "}]");
        return getSqlSession().insert(statement, entity);
    }

    /**
     * 保存实体
     *
     * @param statement mapper中statement的id
     * @param entity mapper中statement的参数 entity
     * @return 影响的行数
     */
    public int save(final String statement, final Map<String, Object> parameters) {
        Assert.notNull(statement, "mapper statement 不能为空");
        Assert.notNull(parameters, "parameters不能为空");
        logger.debug("method[save], params[statement:{" + statement
                + "} ,parameters: {" + parameters + "}]");
        return getSqlSession().insert(statement, parameters);
    }

    /**
     * 删除实体
     *
     * @param statement mapper中statement的id
     * @param entity mapper中statement的参数 entity
     * @return 影响的行数
     */
    public int delete(final String statement, final T entity) {
        Assert.notNull(statement, "mapper statement 不能为空");
        Assert.notNull(entity, "entity不能为空");
        logger.debug("method[delete],params[statement: {" + statement
                + "} ,entity:{" + entity + "}]");
        return getSqlSession().delete(statement, entity);
    }

    /**
     * 删除实体
     *
     * @param statement mapper中statement的id
     * @param parameters mapper中statement的参数 parameters
     * @return 影响的行数
     */
    public int delete(final String statement,
                      final Map<String, Object> parameters) {
        Assert.notNull(statement, "mapper statement 不能为空");
        Assert.notNull(parameters, "Object不能为空");
        logger.debug("method[delete],params[statement: {" + statement
                + "} ,parameters:{" + parameters + "}]");
        return getSqlSession().delete(statement, parameters);
    }

    /**
     * 根据id删除实体
     *
     * @param statement mapper中statement的id
     * @param id mapper中statement的参数实体id
     * @return 影响的行数
     */
    public int delete(final String statement, final Number id) {
        Assert.notNull(statement, "mapper statement 不能为空");
        Assert.notNull(id, "id不能为空");
        logger.debug("method[delete],params[statement:{" + statement
                + "} ,entity: {" + id + "}]");
        return getSqlSession().delete(statement, id);
    }

    /**
     * 修改实体
     *
     * @param statement mapper中statement的id
     * @return 影响的行数
     */
    public int update(final String statement) {
        Assert.notNull(statement, "mapper statement 不能为空");
        return this.getSqlSession().update(statement);
    }

    /**
     * 修改实体
     *
     * @param statement mapper中statement的id
     * @param entity mapper中statement的参数entity
     * @return 影响的行数
     */
    public int update(final String statement, final T entity) {
        Assert.notNull(statement, "mapper statement 不能为空");
        Assert.notNull(entity, "entity不能为空");
        logger.debug("method[update], params[statement:{" + statement
                + "} ,entity: {" + entity + "}]");
        return this.getSqlSession().update(statement, entity);
    }

    /**
     * 修改实体
     *
     * @param statement mapper中statement的id
     * @param parameters mapper中statement的参数map
     * @return
     */
    public int update(final String statement,
                      final Map<String, Object> parameters) {
        Assert.notNull(statement, "mapper statement 不能为空");
        logger.debug("method[update], params[statement:{" + statement
                + "} ,parameterMap: {" + parameters + "}]");
        return this.getSqlSession().update(statement, parameters);
    }

    /**
     * 根据id获取实体
     *
     * @param statement mapper中statement的id
     * @param id mapper中statement的参数实体id
     * @return
     */
    public T get(final String statement, final Number id) {
        logger.debug("method[get], params[statement:{" + statement + "} ,id: {"
                + id + "}]");
        return (T) getSqlSession().selectOne(statement, id);
    }

    public <Vo extends Serializable> Vo getCount(final String statement,
                                                 final Number id) {
        return (Vo) getSqlSession().selectOne(statement, id);
    }

    /**
     * 根据条件查询唯一实体
     *
     * @param statement mapper中statement的id
     * @param entity mapper中statement的参数entity
     * @return
     */
    public T getUnique(final String statement, final T entity) {
        logger.debug("method[getUnique], params[statement:{" + statement
                + "} ,entity: {" + entity + "}]");
        return (T) getSqlSession().selectOne(statement, entity);
    }

    /**
     * 根据条件查询唯一实体
     *
     * @param statement mapper中statement的id
     * @param parameters mapper中statement的参数entity
     * @return
     */
    public T getUnique(final String statement,
                       final Map<String, Object> parameters) {
        logger.debug("method[getUnique], params[statement:{" + statement
                + "} ,parameterMap: {" + parameters + "}]");
        return (T) getSqlSession().selectOne(statement, parameters);
    }

    /**
     * 查询实体
     *
     * @param statement mapper中statement的id
     * @return
     */
    public List<T> getList(final String statement) {
        logger.debug("method[getList], params[statement:{" + statement + "} ]");
        return this.getSqlSession().selectList(statement);
    }

    /**
     * 根据条件查询
     *
     * @param statement mapper中statement的id
     * @param entity mapper中statement的参数entity
     * @return
     */
    public List<T> getList(final String statement, final T entity) {
        logger.debug("method[getList], params[statement:{" + statement
                + "} ,entity: {" + entity + "}]");
        return this.getSqlSession().selectList(statement, entity);
    }

    /**
     * 根据条件查询
     *
     * @param statement  mapper中statement的id
     * @param parameters mapper中statement的参数map
     * @return
     */
    public List<T> getList(final String statement,
                           final Map<String, Object> parameters) {
        logger.debug("method[getList], params[statement:{" + statement
                + "} ,parameterMap: {" + parameters + "}]");
        return this.getSqlSession().selectList(statement, parameters);
    }

    /**
     * 根据条件统计数量
     *
     * @param statement
     * @param parameters
     * @return
     */
    public int count(final String statement,
                     final Map<String, Object> parameters) {
        logger.debug("method[count], params[statement:{" + statement
                + "} ,parameterMap:{" + parameters + "}]");
        return ((Number) this.getSqlSession().selectOne(statement, parameters))
                .intValue();
    }

    /**
     * 根据条件统计数量
     *
     * @param statement
     * @param parameters
     * @return
     */
    public Number statis(final String statement,
                         final Map<String, Object> parameters) {
        logger.debug("method[statis], params[statement:{" + statement
                + "} ,parameterMap:{" + parameters + "}]");
        return (Number) this.getSqlSession().selectOne(statement, parameters);
    }

    /**
     * 分页请求
     *
     * @param statementCount count语句mybatis Key
     * @param statementList list语句的mybatis Key
     * @param pager
     * @return
     */
    public Pager<T> queryPage(String statementCount, String statementList,
                              Pager<T> pager) {
        logger.debug("method[get], params[statement:{" + statementCount + " - "
                + statementList + "} ,pager: {" + pager + "}]");
        Map<String, Object> parameters = pager.params();
        Long recordSize = this.getSqlSession().selectOne(statementCount,
                parameters);
        if (null != recordSize) {
            pager.setRecordSize(recordSize.intValue());
            if (recordSize.intValue() > 0
                    && pager.getPageTotal() >= pager.getCurrentPage()) {
                List<T> lst = getList(statementList, parameters);
                if (null != lst) {
                    pager.setData(lst);
                }
            }
        }
        return pager;
    }
}
