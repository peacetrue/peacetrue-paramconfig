package com.github.peacetrue.paramconfig.controller;

import com.github.peacetrue.paramconfig.service.*;
import com.github.peacetrue.spring.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


/**
 * @author xiayx
 */
@SuppressWarnings("unchecked")
@RequestMapping(value = "${peacetrue.paramconfig.urls.base-path}")
public class ParamConfigController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ParamConfigService paramConfigService;

    @ResponseBody
    @PostMapping(value = "${peacetrue.paramconfig.urls.add}")
    public ParamConfigVO add(ParamConfigAdd params) {
        logger.info("新增信息[{}]", params);
        return paramConfigService.add(BeanUtils.map(params, ParamConfigAdd.class));
    }

    @ResponseBody
    @GetMapping(value = "${peacetrue.paramconfig.urls.query}", params = "page")
    public Page<ParamConfigVO<Object, Object>> query(ParamConfigQuery params,
                                                     @PageableDefault(sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable) {
        logger.info("分页查询信息[{}]", params);
        return paramConfigService.query(BeanUtils.map(params, ParamConfigQuery.class), pageable);
    }

    @ResponseBody
    @GetMapping(value = "${peacetrue.paramconfig.urls.get}", params = {"!page"})
    public ParamConfigVO get(ParamConfigGet params) {
        logger.info("获取信息[{}]详情", params);
        return paramConfigService.get(BeanUtils.map(params, ParamConfigGet.class));
    }

    @ResponseBody
    @GetMapping(value = "${peacetrue.paramconfig.urls.get}/value", params = {"!page"})
    public String getValue(ParamConfigGet params) {
        logger.info("获取信息[{}]的值", params);
        return paramConfigService.getValue(BeanUtils.map(params, ParamConfigGet.class));
    }

    @ResponseBody
    @PutMapping(value = "${peacetrue.paramconfig.urls.modify}")
    public int modify(ParamConfigModify params) {
        logger.info("修改信息[{}]", params);
        return paramConfigService.modify(BeanUtils.map(params, ParamConfigModify.class));
    }

    @ResponseBody
    @DeleteMapping(value = "${peacetrue.paramconfig.urls.delete}")
    public int delete(ParamConfigDelete params) {
        logger.info("删除信息[{}]", params);
        return paramConfigService.delete(BeanUtils.map(params, ParamConfigDelete.class));
    }


}
