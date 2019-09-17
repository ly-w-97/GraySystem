package com.huangyuan.open.gray.config.admin.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author guom 2016-03-28
 * */
public class TldUtils {
	
	public static Calendar parseCalendar(Long timeInMillis) {
    	if (timeInMillis == null || timeInMillis.intValue() == 0) {
    		return null;
    	}
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(timeInMillis);
    	return calendar;
    }
	
    public static String getDateFormat(Calendar calendar, String format) {
    	if (calendar == null) {
    		return "";
    	}
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(format);
            String dateString = sdf.format(calendar.getTime());
            return dateString;
    	} catch (Exception ex) {
			return "";
		}
    }
	
	public static String parseString(Long timeInMillis) {
		return getDateFormat(parseCalendar(timeInMillis), "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String parseStringFormat(Long timeInMillis, String format) {
		return getDateFormat(parseCalendar(timeInMillis), format);
	}
	
	public static String strictClosedPhone(String phone) {
		try {
			if (StringUtils.isNotBlank(phone)) {
				if (phone.length() > 6) {
					return phone.substring(0,3) + "***" + phone.substring(phone.length()-3, phone.length());
				}
			}
		} catch (Exception ex) {
			return "";
		}
		return "";
	}
	
	public static String strictClosedUserName(String userName) {
		try {
			if (StringUtils.isNotBlank(userName)) {
				if (userName.length() > 1) {
					return userName.substring(0,1) + "**" ;
				}
			}
		} catch (Exception ex) {
			return "";
		}
		return "";
	}
	
	public static String strictCloseIdCard(String cardNo) {
		try {
			if (StringUtils.isNotBlank(cardNo)) {
				if (cardNo.length() > 4) {
					return "********" + cardNo.substring(cardNo.length() - 4, cardNo.length());
				}
			}
		} catch (Exception ex) {
			return "";
		}
		return "";
	}
	
	public static String fenConvertYuan(Long amount) {
		if (amount == null) {
			return "--";
		}
        if (amount.intValue() == 0) {
        	return "0.00";
        }
        BigDecimal result = new BigDecimal(amount).divide(new BigDecimal(100), 2, RoundingMode.HALF_DOWN);
        return result.toString();
    }
	
	public static String fenConvertYuanFormat(Long amount) {
		if (amount == null) {
			return "--";
		}
		if (amount.intValue() == 0) {
			return "0.00";
		}
		BigDecimal result = new BigDecimal(amount).divide(new BigDecimal(100), 2, RoundingMode.HALF_DOWN);
		return NumberUtils.getFormatMoney(result);
	}
	
	public static void main(String[] args) {
		System.out.print(fenConvertYuan(1L));
	}

}
