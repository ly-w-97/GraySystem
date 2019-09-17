package com.huangyuan.open.gray.config.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.base.result.EserviceResult;
import com.huangyuan.open.gray.config.api.model.arg.*;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationEaInfoResult;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationGroupConfigResult;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationInfoResult;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationIpConfigResult;
import com.huangyuan.open.gray.config.api.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/gray")
public class WechatGrayController extends BaseController {

    @Resource
    private GrayApplicationEaInfoService grayApplicationEaInfoService;
    @Resource
    private GrayApplicationGroupConfigService grayApplicationGroupConfigService;
    @Resource
    private GrayApplicationInfoService grayApplicationInfoService;
    @Resource
    private GrayApplicationIpConfigService grayApplicationIpConfigService;
    @Resource
    private GraySupportService graySupportService;

    @RequestMapping("/listGrayApplicationInfo")
    public String listGrayApplicationInfo(ModelMap modelMap, Pager<GrayApplicationInfoResult> page,
                                          GrayApplicationInfoArg vo) {
        EserviceResult<List<GrayApplicationGroupConfigResult>> grayGroupConfigList = grayApplicationGroupConfigService.queryGrayGroupConfigList(new GrayApplicationGroupConfigArg());
        modelMap.put("grayGroupMap", grayGroupConfigList);

        EserviceResult<Pager<GrayApplicationInfoResult>> result = grayApplicationInfoService.queryGrayApplicationInfoPage(page, vo);
        if (result.isSuccess()) {
            page = result.getData();
        } else {
            modelMap.put("msg", result.getErrMsg());
        }
        modelMap.put("page", page);
        modelMap.put("vo", vo);
        return "/wechatUnion/gray/listGrayApplicationInfo";
    }

    @RequestMapping("/ajaxInsertGrayApplicationInfo")
    @ResponseBody
    public Map<String, Object> ajaxInsertGrayApplicationInfo(GrayApplicationInfoArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (vo.getApplicationName() != null && vo.getGroupId() != null) {
            EserviceResult<Boolean> result = grayApplicationInfoService.insertGrayApplicationInfo(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "添加成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    @RequestMapping("/ajaxEditDetailApplicationInfo")
    @ResponseBody
    public Map<String, Object> ajaxEditDetailApplicationInfo(GrayApplicationInfoArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (vo.getId() != null && vo.getApplicationName() != null && vo.getGroupId() != null) {
            EserviceResult<Boolean> result = grayApplicationInfoService.updateGrayApplicationInfo(vo);

            // 更新灰度服务到redis
            UpdateGrayIpListArg updateGrayIpListArg = new UpdateGrayIpListArg();
            updateGrayIpListArg.setApplicationName(vo.getApplicationName());
            EserviceResult<Boolean> eserviceResult = graySupportService.updateGrayIpList(updateGrayIpListArg);

            if (result.isSuccess() && result.getData() &&
                    eserviceResult.isSuccess() && (eserviceResult.getData())) {
                modelMap.put("msg", "修改成功");
            } else {
                modelMap.put("msg", "updateGrayApplicationInfo errorMsg=" + result.getErrMsg() + "    updateGrayIpList errorMsg="
                 + eserviceResult.getErrMsg());
            }
        }
        return modelMap;
    }

    @RequestMapping("/ajaxDelGrayApplicationInfo")
    @ResponseBody
    public Map<String, Object> ajaxDelGrayApplicationInfo(GrayApplicationInfoArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (vo.getId() != null) {
            EserviceResult<Boolean> result = grayApplicationInfoService.delGrayApplicationInfo(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "删除成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    //======================================灰度服务应用配置end================================================


    //======================================灰度企业配置start================================================

    @RequestMapping("/listGrayApplicationEaInfo")
    public String listGrayApplicationEaInfo(ModelMap modelMap, Pager<GrayApplicationEaInfoResult> page, GrayApplicationEaInfoArg vo) {
        EserviceResult<Pager<GrayApplicationEaInfoResult>> result = grayApplicationEaInfoService.queryGrayApplicationEaInfoPage(page, vo);
        if (result.isSuccess()) {
            page = result.getData();
        } else {
            modelMap.put("errorMsg", result.getErrMsg());
        }
        modelMap.put("page", page);
        modelMap.put("vo", vo);
        return "/wechatUnion/gray/listGrayApplicationEaInfo";
    }

    @RequestMapping("/ajaxInsertGrayApplicationEaInfo")
    @ResponseBody
    public Map<String, Object> ajaxInsertGrayApplicationEaInfo(GrayApplicationEaInfoArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (StringUtils.isNotBlank(vo.getFsEa()) && StringUtils.isNotBlank(vo.getEaName())) {
            EserviceResult<Boolean> result = grayApplicationEaInfoService.insertGrayApplicationEaInfo(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "添加成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    @RequestMapping("/ajaxEditDetailEAInfo")
    @ResponseBody
    public Map<String, Object> ajaxEditDetailEAInfo(GrayApplicationEaInfoArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (vo.getId() != null && StringUtils.isNotBlank(vo.getFsEa()) && StringUtils.isNotBlank(vo.getEaName())) {
            EserviceResult<Boolean> result = grayApplicationEaInfoService.updateApplicationEaInfo(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "修改成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    @RequestMapping("/ajaxDelGrayApplicationEaInfo")
    @ResponseBody
    public Map<String, Object> ajaxDelGrayApplicationEaInfo(GrayApplicationEaInfoArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (vo.getId() != null) {
            EserviceResult<Boolean> result = grayApplicationEaInfoService.delGrayApplicationEaInfo(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "删除成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    //======================================灰度企业配置end================================================


    //======================================灰度ip配置start================================================

    @RequestMapping("/listGrayApplicationIpConfig")
    public String listGrayApplicationIpConfig(ModelMap modelMap, Pager<GrayApplicationIpConfigResult> page, GrayApplicationIpConfigArg vo) {
        EserviceResult<Pager<GrayApplicationIpConfigResult>> result = grayApplicationIpConfigService.queryGrayApplicationIpConfigPage(page, vo);
        if (result.isSuccess()) {
            page = result.getData();
        } else {
            modelMap.put("errorMsg", result.getErrMsg());
        }
        modelMap.put("page", page);
        modelMap.put("vo", vo);
        return "/wechatUnion/gray/listGrayApplicationIpConfig";
    }

    @RequestMapping("/ajaxInsertGrayIpConfig")
    @ResponseBody
    public Map<String, Object> ajaxInsertGrayIpConfig(GrayApplicationIpConfigArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (StringUtils.isNotBlank(vo.getIpAddress()) && vo.getApplicationId() != null) {
            EserviceResult<Boolean> result = grayApplicationIpConfigService.insertGrayApplicationIpConfig(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "添加成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    @RequestMapping("/ajaxEditDetailIPInfo")
    @ResponseBody
    public Map<String, Object> ajaxEditDetailIPInfo(GrayApplicationIpConfigArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (vo.getId() != null && StringUtils.isNotBlank(vo.getIpAddress()) && vo.getApplicationId() != null) {
            EserviceResult<Boolean> result = grayApplicationIpConfigService.updateApplicationIPConfig(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "修改成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    @RequestMapping("/ajaxDelGrayApplicationIpConfig")
    @ResponseBody
    public Map<String, Object> ajaxDelGrayApplicationIpConfig(GrayApplicationIpConfigArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (vo.getId() != null) {
            EserviceResult<Boolean> result = grayApplicationIpConfigService.delGrayApplicationIpConfig(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "删除成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    //======================================灰度ip配置end================================================


    //======================================灰度分组配置start================================================

    @RequestMapping("/listGrayApplicationGroupConfig")
    public String listGrayApplicationGroupConfig(ModelMap modelMap, Pager<GrayApplicationGroupConfigResult> page, GrayApplicationGroupConfigArg vo) {
        EserviceResult<List<GrayApplicationInfoResult>> grayApplicationInfoList = grayApplicationInfoService.queryGrayApplicationInfoList(new GrayApplicationInfoArg());
        modelMap.put("grayApplicationInfoMap", grayApplicationInfoList);

        EserviceResult<Pager<GrayApplicationGroupConfigResult>> result = grayApplicationGroupConfigService.queryGrayApplicationGroupConfigPage(page, vo);
        if (result.isSuccess()) {
            page = result.getData();
        } else {
            modelMap.put("errorMsg", result.getErrMsg());
        }
        modelMap.put("page", page);
        modelMap.put("vo", vo);
        return "/wechatUnion/gray/listGrayApplicationGroupConfig";
    }

    @RequestMapping("/ajaxInsertGrayGroup")
    @ResponseBody
    public Map<String, Object> ajaxInsertGrayGroup(GrayApplicationGroupConfigArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (StringUtils.isNotBlank(vo.getGroupName())) {
            EserviceResult<Boolean> result = grayApplicationGroupConfigService.insertGrayApplicationGroupConfig(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "添加成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    @RequestMapping("/ajaxEditDetailGroupInfo")
    @ResponseBody
    public Map<String, Object> ajaxEditDetailGroupInfo(GrayApplicationGroupConfigArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (vo.getId() != null && StringUtils.isNotBlank(vo.getGroupName())) {
            EserviceResult<Boolean> result = grayApplicationGroupConfigService.updateGroupConfig(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "修改成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    @RequestMapping("/ajaxDelGrayGroupConfig")
    @ResponseBody
    public Map<String, Object> ajaxDelGrayGroupConfig(GrayApplicationGroupConfigArg vo) {
        Map<String, Object> modelMap = new HashMap<>(1);
        if (vo.getId() != null) {
            EserviceResult<Boolean> result = grayApplicationGroupConfigService.delGrayApplicationGroupConfig(vo);
            if (result.isSuccess()) {
                modelMap.put("msg", "删除成功");
            } else {
                modelMap.put("msg", result.getErrMsg());
            }
        }
        return modelMap;
    }

    //======================================灰度分组配置end================================================
}
