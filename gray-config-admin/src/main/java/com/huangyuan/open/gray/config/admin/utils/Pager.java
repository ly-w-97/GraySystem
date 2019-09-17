package com.huangyuan.open.gray.config.admin.utils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class Pager extends TagSupport {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页数
     */
    private Integer curPage;

    /**
     * 总共页数
     */
    private Integer totalPage;

    /**
     * 每一页的值
     */
    private Integer pageSize = 20;

    /**
     * 记录数
     */
    private Integer totalCount;

    /**
     * 表单的ID
     */
    private String formId;

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public void setPageSize(Integer pageSize) {

        this.pageSize = pageSize;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        int pageNumber = 0;
        if (totalPage % pageSize == 0) {
            pageNumber = totalPage / pageSize;
        } else {
            pageNumber = (totalPage / pageSize) + 1;
        }
        if (curPage < 1) {
            curPage = 1;
        }

        try {
            if (pageNumber > 0) {
                out.print("<script type='text/javascript'> " +
                    "function go(pageNum){" +
                    "var f = document.getElementById('" + formId + "');" +
                    "f.action = f.action + '?currentPage=' + pageNum + '&pageSize=" + pageSize + "';" +
                    "f.submit();" +
                    "}" +
                    "function goToInputPage(){" +
                    "var f = document.getElementById('" + formId + "');" +
                    "var gotoinputpage = document.getElementById('gotoinputpage').value;" +
                    "if (gotoinputpage != '' && !isNaN(gotoinputpage)) {" +
                    "f.action = f.action + '?currentPage=' + gotoinputpage + '&pageSize=" + pageSize + "';" +
                    "f.submit();" +
                    "}}" +
                    "function goToInputPageSize(obj){" +
                    "var f = document.getElementById('" + formId + "');" +
                    "var pageSize = obj.value;" +
                    "f.action = f.action + '?pageSize=' + pageSize;" +
                    "f.submit();" +
                    "}" +
                    "</script>");

                out.print("<nav style='text-align: center'><ul class='pagination text-right'>");
                int start = 1;
                int end = totalPage;
                for (int i = 4; i >= 1; i--) {
                    if ((curPage - i) >= 1) {
                        start = curPage - i;
                        break;
                    }
                }
                for (int i = 4; i >= 1; i--) {
                    if ((curPage + i) <= totalPage) {
                        end = curPage + i;
                        break;
                    }
                }
                //如果小于9   左侧对齐
                if (end - start + 1 <= 9) {
                    Integer padLen = 9 - (end - start + 1);
                    for (int i = padLen; i >= 1; i--) {
                        if ((end + i) <= totalPage) {
                            end = end + i;
                            break;
                        }
                    }
                }

                //如果小于9   左侧对齐
                if (end - start + 1 <= 9) {
                    Integer padLen = 9 - (end - start + 1);
                    for (int i = padLen; i >= 1; i--) {
                        if ((start - i) >= 1) {
                            start = start - i;
                            break;
                        }
                    }
                }
                out.print("<li><li><select style='float:left;width:50px;height:34px' onchange='goToInputPageSize(this)'>");
                if (pageSize == 10) {
                	out.print("<option selected>10</option>");
                }
                else {
                	out.print("<option >10</option>");
                }
                if (pageSize == 15) {
                	out.print("<option selected>15</option>");
                }
                else {
                	out.print("<option >15</option>");
                }
                if (pageSize == 20) {
                	out.print("<option selected>20</option>");
                }
                else {
                	out.print("<option >20</option>");
                }
                if (pageSize == 30) {
                	out.print("<option selected>30</option>");
                }
                else {
                	out.print("<option >30</option>");
                }
                if (pageSize == 50) {
                	out.print("<option selected>50</option>");
                }
                else {
                	out.print("<option >50</option>");
                }
                if (pageSize == 100) {
                	out.print("<option selected>100</option>");
                }
                else {
                	out.print("<option >100</option>");
                }
                out.print("</select></li></li>");
                if (curPage > 1) {
                    if (start > 1) {
                        out.print("<li><a href='javascript:go(1)'>首页</a></li>");
                    }
                    out.print("<li><a href='javascript:go(" + (curPage - 1) + ")'>&laquo;</a></li>");
                }

                for (int i = start; i <= end; i++) {
                    if (i == curPage) {
                        out.print("<li class='active'><a href='#'>" + i + "</a></li>");
                    } else {
                        out.print("<li><a href='javascript:go(" + i + ")'>" + i + "</a></li>");
                    }
                }
                if (curPage < totalPage) {
                    out.print("<li><a href='javascript:go(" + (curPage + 1) + ")'>&raquo;</a></li>");
                    if (end < totalPage) {
                        out.print("<li><a href='javascript:go(" + totalPage + ")'>尾页</a></li>");
                        out.print("<li><input type='text' style='height:34px;width:40px;float:left' id='gotoinputpage'></li>");
                        out.print("<li><a href='javascript:goToInputPage() '>go</a></li>");
                    }
                }
                out.print("<li><a href='javascript:void(0)'>共" + totalPage + "页" + this.totalCount + "条</a></li>");
                out.print("</ul></nav>");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.doStartTag();

    }

    public static Integer getStartIndex(Integer pageNum, Integer pageSize) {
        Integer res = 0;
        if (pageNum > 0) {
            res = (pageNum - 1) * pageSize;
        }
        return res;
    }
}
