package com.huangyuan.open.gray.config.admin.controller;

import com.huangyuan.open.gray.config.admin.utils.CalendarEditor;
import com.huangyuan.open.gray.config.admin.utils.LongEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

@Controller
public class BaseController {

    public static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public static final String QUERY_FLG = "query";

    @Autowired
    public HttpServletRequest request;

    @Autowired
    private HttpSession session;

    private static final int DIFF = 7;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Calendar.class, new CalendarEditor());
        binder.registerCustomEditor(Long.class, new LongEditor());
    }
    public HttpSession getSession() {
        return session;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

}

