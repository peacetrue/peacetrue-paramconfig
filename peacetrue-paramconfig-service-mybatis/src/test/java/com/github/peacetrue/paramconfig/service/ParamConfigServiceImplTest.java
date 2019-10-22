package com.github.peacetrue.paramconfig.service;

import com.github.peacetrue.spring.util.BeanUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestServiceParamConfigAutoConfiguration.class)
@Transactional
public class ParamConfigServiceImplTest {

    @Autowired
    private ParamConfigService paramConfigService;

    public static final ParamConfigAdd<Long> PARAM_CONFIG_ADD = new ParamConfigAdd<>();

    static {
        PARAM_CONFIG_ADD.setCode("2");
        PARAM_CONFIG_ADD.setName("2");
        PARAM_CONFIG_ADD.setValue("2");
        PARAM_CONFIG_ADD.setRemark("2");
        PARAM_CONFIG_ADD.setOperatorId(1L);
    }

    @Test
    public void add() {
        ParamConfigVO<Long, Long> vo = paramConfigService.add(PARAM_CONFIG_ADD);
        Assert.assertEquals(vo, paramConfigService.<Long, Long>get(new ParamConfigGet<>(vo.getId())));
    }

    @Test
    public void query() {
        Page<ParamConfigVO<Long, Long>> vos = paramConfigService.query(new ParamConfigQuery(), new PageRequest(0, 1));
        Assert.assertEquals(vos.getTotalElements(), 1);
    }

    @Test
    public void get() {
        ParamConfigVO<Long, Long> vo = paramConfigService.get(new ParamConfigGet<>(1L));
        Assert.assertNotNull(vo);
    }

    @Test
    public void modify() {
        ParamConfigVO<Long, Long> vo = paramConfigService.get(new ParamConfigGet<>(1L));
        ParamConfigModify<Long, Long> modify = new ParamConfigModify<>();
        BeanUtils.copyProperties(vo, modify);
        modify.setCode("2");
        int count = paramConfigService.modify(modify);
        Assert.assertEquals(count, 1);
        vo = paramConfigService.get(new ParamConfigGet<>(1L));
        Assert.assertEquals(modify.getCode(), vo.getCode());
    }

    @Test
    public void delete() {
        int count = paramConfigService.delete(new ParamConfigDelete<>(new Long[]{1L}));
        Assert.assertEquals(count, 1);
    }
}