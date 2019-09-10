package com.huangyuan.open.gray.base.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangy on 2019-09-10
 */
public class Pager<T> implements Serializable {
    private static final long serialVersionUID = -6814398952058550296L;

    /**
     * 每页显示记录数
     */
    private int pageSize = 10;
    /**
     * 当前页码
     */
    private int currentPage = 1;
    /**
     * 总页数
     */
    private int pageTotal = 0;
    /**
     * 记录数
     */
    private int recordSize = 0;

    /**
     * 参数表
     */
    private Map<String, Object> params = new HashMap<String, Object>();
    /**
     * 数据
     */
    private List<T> data = new ArrayList<T>();

    /**
     *  获取offset
     * @return
     */
    public int offset() {
        return (currentPage - 1) * pageSize;
    }

    /**
     *  获取limit
     * @return
     */
    public int limit() {
        return getPageSize();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (currentPage > Integer.MAX_VALUE) {
            currentPage = Integer.MAX_VALUE;
        }
        this.currentPage = currentPage;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getRecordSize() {
        return recordSize;
    }

    public void setRecordSize(int recordSize) {
        if (recordSize >= 0) {
            pageTotal = recordSize / pageSize;
            if (recordSize % pageSize != 0) {
                pageTotal++;
            }
            this.recordSize = recordSize;
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void addParam(String key, Object value) {
        params.put(key, value);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> params() {
        addParam("limit", this.limit());
        addParam("offset", this.offset());
        addParam("pager_case", "limit #{limit} offset #{offset}");
        return params;
    }

    @Override
    public String toString() {
        return "Pager [pageSize=" + pageSize + ", currentPage=" + currentPage
                + ", pageTotal=" + pageTotal + ", recordSize=" + recordSize
                + ", params=" + params + ", data=" + data + "]";
    }
}

