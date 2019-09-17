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
                <div class="panel panel-heading">灰度应用服务列表</div>
                <div class="panel panel-body">
                    <div class="dataTable_wrapper">
                        <div class="row" style="padding:10px">
                            <div class="col-lg-12" >
                                <form  role="form" method="post" action="/gray/listGrayApplicationInfo"  class="form-inline fs-form" id="dataForm">
                                	<input type="hidden" value="query" name="queryFlag" />
                                    <div class="form-group " > 
                                        <label class="form-inline" >应用服务名称：</label>
                                        <input  type=text class="form-control"  onblur="this.value=this.value.replace(/(^\s+)|(\s+$)/g, '');" value="${vo.applicationName}" name="applicationName">
                                    </div>

                                    <div class="form-group kstar-control">
                                        <label class="form-inline">分组：</label>
                                        <select name="groupId" class="form-control" style="width:196px;">
                                            <option value=" ">请选择</option>
                                            <c:forEach items="${grayGroupMap.data}" var="grayGroup">
                                                <option value="${grayGroup.id}" ${vo.groupId == grayGroup.id ? "selected" : ""}>${grayGroup.groupName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label class="form-inline">状态：</label>
                                        <select name="status" style="width:196px" class="form-control">
                                            <option value="">请选择</option>
                                            <option value="1" ${"1" == vo.status ? "selected" : ""}>开启</option>
                                            <option value="2" ${"2" == vo.status ? "selected" : ""}>关闭</option>
                                        </select>
                                    </div>
                                    <div class="form-group" style="width:120px;text-align: right;margin:-6px 0 0 0">
                                        <button type="submit" class="btn btn-primary">查询</button>
                                        <button type="button" class="btn btn-default" onclick="addGrayApplication()">添加</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                         <div class="row fs-table-row">
                         <table class="table table-striped table-bordered table-hover" style="min-width:3400px;">
                            <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>应用名称</th>
                                    <th>所属分组</th>
                                    <th>IP地址</th>
                                    <th>状态</th>
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
                                    <td><a href="/gray/listGrayApplicationIpConfig?applicationName=${item.applicationName}"> ${item.applicationName}</a></td>
                                    <td class="center"><a href="/gray/listGrayApplicationGroupConfig?groupName=${item.groupName}">${item.groupName}</a></td>
                                    <td class="center">${item.ipAddress}</td>
                                    <td class="center">${item.status == 1 ? "开启"
                                    	: item.status == 2 ? "关闭"  : ""}</td>
                                    <td class="center">${item.describe}</td>
                                    <td class="center">${fsfun:parseLongCalendarString(item.updateTime)}</td>
                                    <td class="center">${fsfun:parseLongCalendarString(item.createTime)}</td>
                                    <td class="center">
                                        <button type="button" class="btn btn-primary"
                                                onclick="updateGrayApplication('${item.id}', '${item.applicationName}', '${item.groupId}', '${item.status}', '${item.describe}', '${item.ipAddress}')">
                                            修改详情信息
                                        </button>
                                        <button type="button" class="btn btn-primary"
                                                onclick="delGrayApplicationInfo('${item.id}')">
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
        <div class="modal fade" id="applicationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close"  data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">添加灰度服务信息</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group " >
                            <label class="form-inline" >应用名称</label>
                            <input  type=text class="form-control"  name="applicationName">
                        </div>
                        <div class="form-group kstar-control">
                            <label class="form-inline">分组：</label>
                            <select name="groupId" class="form-control" style="width:196px;">
                                <c:forEach items="${grayGroupMap.data}" var="grayGroup">
                                    <option value="${grayGroup.id}" ${vo.groupId == grayGroup.id ? "selected" : ""}>${grayGroup.groupName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group " >
                            <label class="form-inline" >IP地址</label>
                            <input  type=text class="form-control" name="ipAddress">
                        </div>
                        <div class="form-group " >
                            <label class="form-inline" >状态</label>
                            <select name="status" class="form-control">
                                <option value="1" >开启</option>
                                <option value="2" >关闭</option>
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

function addGrayApplication() {
    $("#id").val("");
    $("#myModalLabel").val("添加灰度服务信息");
    $("input[name='applicationName']").val("");
    $("input[name='groupId']").val("");
    $("input[name='ipAddress']").val("");
    $("input[name='status']").val("");
    $("input[name='describe']").val("");
    $("#applicationModal").modal();
}

function updateGrayApplication(id, applicationName, groupId, status, describe, ipAddress) {
    $("#id").val(id);
    $("#myModalLabel").val("修改灰度服务信息");
    $("input[name='applicationName']").val(applicationName);
    $("input[name='ipAddress']").val(ipAddress);
    $("input[name='describe']").val(describe);
    $("select[name='status']").find("option").each(function(){
        if (this.value == status) {
            $(this).prop("selected", true);
        } else {
            $(this).prop("selected", false);
        }
    });
    $("select[name='groupId']").find("option").each(function(){
        if (this.value == groupId) {
            $(this).prop("selected", true);
        } else {
            $(this).prop("selected", false);
        }
    });
    $("#applicationModal").modal();
}
function commitEvent() {
    var url = '/gray/ajaxInsertGrayApplicationInfo';
    var id = $("#id").val();
    if (id != "") {
        url = '/gray/ajaxEditDetailApplicationInfo';
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

function delGrayApplicationInfo(id) {
    if (id != "") {
        var url = "/gray/ajaxDelGrayApplicationInfo?id=" + id;
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
