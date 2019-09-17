package com.huangyuan.open.gray.config.admin.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guom 2016-07-19
 * */
public class ElEnumUtils {

	private static Map<String, String> enumMap = new HashMap<>();
	
	static {
		// business
		enumMap.put("AlarmStatus", "com.facishare.pay.business.constant.AlarmStatus");
		enumMap.put("ChargeStatus", "com.facishare.pay.business.constant.ChargeStatus");
		enumMap.put("FundEntityEnum", "com.facishare.pay.business.constant.FundEntityEnum");
		enumMap.put("GetMoneyStatus", "com.facishare.pay.business.constant.GetMoneyStatus");
		enumMap.put("NoticeTypeEnum", "com.facishare.pay.business.constant.NoticeTypeEnum");
		enumMap.put("OperateType", "com.facishare.pay.business.constant.OperateType");
		enumMap.put("PayChannel", "com.facishare.pay.business.constant.PayChannel");
		enumMap.put("PayWayEnum", "com.facishare.pay.business.constant.PayWayEnum");
		enumMap.put("RegulateStatus", "com.facishare.pay.business.constant.RegulateStatus");
		enumMap.put("TransferStatus", "com.facishare.pay.business.constant.TransferStatus");
		enumMap.put("UserStatus", "com.facishare.pay.business.constant.UserStatus");
		
		enumMap.put("EAChannelIdEnum", "com.facishare.pay.enterprise.constant.EAChannelIdEnum");
		enumMap.put("EAChargeStatus", "com.facishare.pay.enterprise.constant.EAChargeStatus");
		enumMap.put("EAConstants", "com.facishare.pay.enterprise.constant.EAConstants");
		enumMap.put("EAGetMoneyStatus", "com.facishare.pay.enterprise.constant.EAGetMoneyStatus");
		enumMap.put("EAPayStatus", "com.facishare.pay.enterprise.constant.EAPayStatus");
		enumMap.put("EAPayType", "com.facishare.pay.enterprise.constant.EAPayType");
		enumMap.put("EAWalletTypeEnum", "com.facishare.pay.enterprise.constant.EAWalletTypeEnum");
		enumMap.put("WithDrawTradeStatusEnum", "com.facishare.pay.enterprise.constant.WithDrawTradeStatusEnum");
		enumMap.put("KuaiqianPayTypeEnum", "com.facishare.pay.enterprise.third.kuaiqian.enums.KuaiqianPayTypeEnum");
		enumMap.put("AmountLimitBizEnum", "com.facishare.pay.business.constant.AmountLimitBizEnum");
		enumMap.put("MerchantPayTypeEnum", "com.facishare.pay.enterprise.constant.MerchantPayTypeEnum");
		
		enumMap.put("AuditOptType", "com.facishare.pay.business.constant.AuditOptType");
		enumMap.put("AuditObjectiveType", "com.facishare.pay.business.constant.AuditObjectiveType");
		enumMap.put("AuditStatus", "com.facishare.pay.business.constant.AuditStatus");
		enumMap.put("EAAuthInfoStatus", "com.facishare.pay.enterprise.constant.EAAuthInfoStatus");
		enumMap.put("EAAccountTypeEnum", "com.facishare.pay.enterprise.constant.EAAccountTypeEnum");
		// bank
		enumMap.put("BankBusiTypeEnum", "com.facishare.pay.bank.constants.BankBusiTypeEnum");
		enumMap.put("AccountDisplayCode", "com.facishare.pay.bank.constants.AccountDisplayCode");
		enumMap.put("OperateType", "com.facishare.pay.bank.constants.OperateType");
		// order
		enumMap.put("EAOrderCodeEnum", "com.facishare.pay.enterprise.order.model.enums.EAOrderCodeEnum");
		enumMap.put("EAOrderPayChannel", "com.facishare.pay.enterprise.order.model.enums.EAOrderPayChannel");
		enumMap.put("EAOrderStatus", "com.facishare.pay.enterprise.order.model.enums.EAOrderStatus");
		enumMap.put("OrderStatus", "com.facishare.pay.enterprise.order.model.enums.OrderStatus");
		// bill
		// announce
		enumMap.put("FSAccountTypeEnum", "com.facishare.pay.announce.constant.FSAccountTypeEnum");

		enumMap.put("EARegulateStatus","com.facishare.pay.enterprise.constant.EARegulateStatus");
		enumMap.put("TransTypeEnum","com.facishare.pay.common.constants.TransTypeEnum");
		
		// luckmoney
		enumMap.put("LuckyMoneySendTypeEnum","com.facishare.open.luckmoney.api.model.enums.LuckyMoneySendTypeEnum");
		enumMap.put("LuckyMoneySendStatusEnum","com.facishare.open.luckmoney.api.model.enums.LuckyMoneySendStatusEnum");
		
		// TOC
		enumMap.put("TOCBankBizTypeEnum", "com.facishare.pay.toc.bank.api.constant.BankBizTypeEnum");
		enumMap.put("TOCOrderStatusEnum", "com.facishare.pay.toc.order.api.constant.OrderStatusEnum");
		enumMap.put("TOCBizTypeEnum", "com.facishare.pay.toc.common.constants.BizTypeEnum");
		enumMap.put("TOCTransTypeEnum", "com.facishare.pay.toc.common.constants.TransTypeEnum");
		enumMap.put("TOCChargeStatusEnum", "com.facishare.pay.toc.business.constant.enums.ChargeStatusEnum");
		enumMap.put("TOCGetMoneyStatusEnum", "com.facishare.pay.toc.business.constant.enums.GetMoneyStatusEnum");
		enumMap.put("TOCRegulateStatusEnum", "com.facishare.pay.toc.business.constant.enums.RegulateStatusEnum");
		enumMap.put("TOCTransferStatusEnum", "com.facishare.pay.toc.business.constant.enums.TransferStatusEnum");
		enumMap.put("TOCWechatLogStatusEnum", "com.facishare.pay.toc.business.constant.enums.WechatLogStatusEnum");
		enumMap.put("TOCWechatLogTypeEnum", "com.facishare.pay.toc.business.constant.enums.WechatLogTypeEnum");
	}
	
	public static Object getEnumByMethod(String className, Object param, String methodName) {
		Class<?> cl = null;
		try {
			String value = enumMap.get(className);
			if (StringUtils.isNotBlank(value)) {
				cl = Class.forName(value);
			} else {
				cl = Class.forName(className);
			}
		} 
		catch (ClassNotFoundException e) {
			return null;
		}
		if (cl != null) {
			try {
				Method[] methods = cl.getMethods();
				for (Method method : methods) {
					if (method.getName().equalsIgnoreCase(methodName)) {
						if (param == null || StringUtils.isBlank(param.toString())) {
							if (method.getParameterTypes().length != 0) {
								return null;
							}
							return method.invoke(cl);
						}
						return method.invoke(cl, param);
					}
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	public static Object findByIndex(String className, Object param) {
		return getEnumByMethod(className, param, "findByIndex");
	}
	
	public static void main(String[] args) {
		System.out.println(getEnumByMethod("EAAccountTypeEnum", 1, "findByIndex"));
	}
	
}
