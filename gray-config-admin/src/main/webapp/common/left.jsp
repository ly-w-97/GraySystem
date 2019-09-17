<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                       <!--  <li class="sidebar-search">
                            <div class="input-group custom-search-form">
                                <input type="text" class="form-control" placeholder="Search...">
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </span>
                            </div>
                            /input-group
                        </li> -->
                        <li>
                            <!-- <a href="index"><i class="fa fa-dashboard fa-fw"></i> 创建通知</a> -->
                            <a href="#"><i class="fa fa-volume-up fa-fw"> </i>用户管理 <span class="fa arrow"></span> </a>
                             <ul class="nav nav-second-level">
                                <li>
                                    <a href="/user/listUserInfo">用户列表</a>
                                </li>
                                <li>
                                    <a href="/user/listUserWallet">用户钱包列表</a>
                                </li>
                                <li>
                                     <a href="/user/listUserWalletLog">用户钱包流水</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>订单管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="/order/listOrder">订单列表</a>
                                </li>
                                <li>
                                    <a href="/order/listOrderLog">订单流水</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>流水管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="/charge/listCharge">充值流水</a>
                                </li>
                                <li>
                                    <a href="/getmoney/listGetMoney">提现流水</a>
                                </li>
                                <li>
                                    <a href="/transfer/listTransfer">转账流水</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>对账管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                            	<li>
                                    <a href="/bill/listBillLog">对账流水查询</a>
                                </li>
                                <li>
                                    <a href="/bill/listBillResult">对账记录</a>
                                </li>
                                <li>
                                    <a href="/bill/listErrorBillDetail">错账记录</a>
                                </li>
                                <li>
                                    <a href="/thirdLog/listKqSettle">快钱交易列表</a>
                                </li>
                                <li>
                                    <a href="/thirdLog/listWxSettle">微信交易列表</a>
                                </li>
                                <li>
                                    <a href="/thirdLog/listThirdError">漏单列表</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>财务管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="/getmoney/listGetMoneyAuditor">提现审批列表</a>
                                </li>
                                <li>
                                    <a href="/getmoney/listGetMoneyTeller">提现出纳列表</a>
                                </li>
								<li>
                                    <a href="/regulate/listRegulate">调账申请列表</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>红包管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="/luckMoney/listLuckMoney">红包列表</a>
                                    <a href="/luckMoney/listLuckMoneySum">红包汇总</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>操作日志<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="/operateLog/listOperateLog">操作日志</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>警报<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="/alarmLog/listAlarmLog">警报日志</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i>第三方管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="/merchant/listMerchant">回调管理</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                             <a href="/bank/listBank">银行卡列表</a>
                         </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
