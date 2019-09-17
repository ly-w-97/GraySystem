<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib prefix="page" uri="/WEB-INF/pager.tld"%>
<%@ taglib prefix="fsfun" uri="/WEB-INF/fs_fun.tld"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>后台管理系统</title>
<%@include file="/common/common.jsp"%>
</head>
<body>
    <div class="row" style="margin:auto">
        <div class="col-lg-12">
            <div class="panel panel-default" style="margin:30px 0 0 0">
                <div class="panel panel-heading">灰度服务IP地址列表</div>
                <div class="panel panel-body">
                    <div class="dataTable_wrapper">
                        <div class="row" style="padding:10px">
                            <div class="col-lg-12" >
                                <form  role="form" method="post" action="/gray/listGrayApplicationIpConfig"  class="form-inline fs-form" id="dataForm">
                                	<input type="hidden" value="query" name="queryFlag" />
                                    <div class="form-group " >
                                        <label class="form-inline">应用服务名称:</label>
                                        <select name="applicationId" class="form-control" style="width:196px;">
                                            <option value="-1">请选择</option>
                                            <c:forEach items="${grayApplicationInfoMap.data}" var="application">
                                                <option value="${application.applicationId}" ${vo.applicationId == application.applicationId ? "selected" : ""}>${application.applicationName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group" style="width:120px;text-align: right;margin:-6px 0 0 0">
                                        <button type="submit" class="btn btn-primary">查询</button>
                                        <button type="button" class="btn btn-default" onclick="addGrayIP()">添加</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                         <div class="row fs-table-row">
                         <table class="table table-striped table-bordered table-hover" style="min-width:3400px;">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>ip地址</th>
                                    <th>所属服务</th>
                                    <th>备注</th>
                                    <th>更新时间</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${page.data}" var="item" varStatus="vs">
                                <tr <c:if test="${vs.count%2=='0'}">class="odd" </c:if><c:if test="${vs.count%2!='0'}">class="even"</c:if>>
                                    <td>${vs.index + 1 + (page.currentPage - 1)*page.pageSize}</td>
                                    <td>${item.ipAddress}</td>
                                    <td><a href="/gray/listGrayApplicationInfo?applicationName=${item.applicationName}"> ${item.applicationName}</a></td>
                                    <td class="center">${item.describe}</td>
                                    <td class="center">${fsfun:parseLongCalendarString(item.updateTime)}</td>
                                    <td class="center">${fsfun:parseLongCalendarString(item.createTime)}</td>
                                    <td class="center">
                                        <button type="button" class="btn btn-primary"
                                                onclick="updateGrayIP('${item.id}', '${item.ipAddress}', '${item.applicationId}', '${item.describe}')">
                                            修改详情信息
                                        </button>
                                        <button type="button" class="btn btn-primary"
                                                onclick="delGraIPConfig('${item.id}')">
                                            删除
                                        </button>
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                       </div>
                       <page:createPager pageSize="${page.pageSize}" totalPage="${page.pageTotal}"
                                          totalCount="${page.recordSize}" curPage="${page.currentPage}" formId="dataForm" />
                </div>
            </div>
            </div>
        </div>
    </div>

    <!-- 模态框（Modal） -->
    <form id="addForm">
        <div class="modal fade" id="grayApplicationIPConfigModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">添加灰度服务机器IP</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group " >
                            <label class="form-inline" >机器ip地址</label>
                            <input  type=text class="form-control" value="" name="ipAddress">
                        </div>
                        <div class="form-group " >
                            <label class="form-inline">应用服务名称:</label>
                            <select name="modelApplicationIdIndex" class="form-control" style="width:196px;">
                                <c:forEach items="${grayApplicationInfoMap.data}" var="application">
                                    <option value="${application.id}" ${vo.applicationId == application.id ? "selected" : ""}>${application.applicationName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group " >
                            <label class="form-inline" >备注</label>
                            <textarea class="form-control" placeholder="备注" name="describe"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default"  data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="commitEvent()">提交更改</button>
                    </div>
                </div>
            </div>
            <!-- 保留要传的值 -->
            <input type="hidden" value="" name="id" id="id"/>
        </div>
    </form>

<script type="text/javascript">
$(function(){
    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:ss",
        autoclose: true,
        todayBtn: true,
        language:'zh-CN'
    });
})

function addGrayIP() {
    $("#id").val("");
    $("#myModalLabel").val("添加灰度服务机器IP");
    $("input[name='ipAddress']").val("");
    $("input[name='modelApplicationIdIndex']").val("");
    $("input[name='describe']").val("");
    $("#grayApplicationIPConfigModal").modal();
}
function updateGrayIP(id, ipAddress, applicationId, describe) {
    $("#id").val(id);
    $("#myModalLabel").val("修改灰度服务机器IP");
    $("input[name='ipAddress']").val("");
    $("input[name='modelApplicationIdIndex']").val("");
    $("input[name='describe']").val("");
    $("#grayApplicationIPConfigModal").modal();
}
function commitEvent() {
    var url = '/gray/ajaxInsertGrayIpConfig';
    var id = $("#id").val();
    if (id != "") {
        url = '/gary/ajaxEditDetailIPInfo';
    }
    var $form = $("#addForm");
    $.ajax({
        type:'POST',
        url:url,
        data:$form.serializeArray(),
        dataType:"json",
        cache:false,
        success:function(data){
            alert(data.msg);
            window.location.reload();
        },
        error:function(){
            alert("出现异常");
        }
    });
}

function delGraIPConfig(id) {
    if (id != "") {
        var url = "/gray/ajaxDelGrayApplicationIpConfig?id=" + id;
        if (url != "") {
            $("#redisData").html("");
            $.ajax({
                type: 'POST',
                url: url,
                dataType: "json",
                cache: false,
                success: function (data) {
                    alert(data.msg);
                    window.location.reload();
                },
                error: function () {
                    alert("出现异常");
                }
            });
        }
    }
}

</script>
</body>
</html>
