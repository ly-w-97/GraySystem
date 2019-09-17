package com.huangyuan.open.gray.config.admin.utils;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.regex.Pattern;


public class CalendarEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isBlank(text)) {
		    setValue(null);
		} 
		else {
		    return;
		}
    }

}
