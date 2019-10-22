package com.github.peacetrue.paramconfig.service;

import com.github.pagehelper.PageHelper;
import com.github.peacetrue.core.Range;
import com.github.peacetrue.mybatis.dynamic.MybatisDynamicUtils;
import com.github.peacetrue.pagehelper.PageHelperUtils;
import com.github.peacetrue.result.exception.ResultException;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.util.EntityNotFoundException;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.peacetrue.paramconfig.service.ParamConfigDynamicSqlSupport.*;

/**
 * @author xiayx
 */
public class ParamConfigServiceImpl implements ParamConfigService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ParamConfigMapper paramConfigMapper;

    @Override
    public <Id, OperatorId> ParamConfigVO<Id, OperatorId> add(ParamConfigAdd<OperatorId> params) {
        logger.info("新增信息[{}]", params);
        ParamConfig<Id, OperatorId> paramConfig = new ParamConfig<>();
        BeanUtils.copyProperties(params, paramConfig);
        paramConfig.setCreatorId(params.getOperatorId());
        paramConfig.setCreatedTime(new Date());
        paramConfig.setModifierId(params.getOperatorId());
        paramConfig.setModifiedTime(paramConfig.getCreatedTime());
        logger.debug("保存信息[{}]", paramConfig);
        int count = paramConfigMapper.insertSelective(paramConfig);
        logger.debug("共影响[{}]条记录", count);
        return to(paramConfig);
    }

    private <Id, OperatorId> ParamConfigVO<Id, OperatorId> to(ParamConfig<Id, OperatorId> demo) {
        ParamConfigVO<Id, OperatorId> vo = new ParamConfigVO<>();
        BeanUtils.copyProperties(demo, vo);
        return vo;
    }

    @Override
    public <Id, OperatorId> Page<ParamConfigVO<Id, OperatorId>> query(@Nullable ParamConfigQuery params, @Nullable Pageable pageable) {
        logger.info("分页查询信息[{}]", params);
        if (params == null) params = ParamConfigQuery.DEFAULT;
        if (params.getCreatedTime() == null) params.setCreatedTime(new Range.Date());
        if (pageable == null) pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "createdTime"));
        PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize());
        List<ParamConfig<Id, OperatorId>> entities = paramConfigMapper.<Id, OperatorId>selectByExample()
                .where(code, SqlBuilder.isLikeWhenPresent(MybatisDynamicUtils.likeValue(params.getCode())))
                .and(name, SqlBuilder.isEqualToWhenPresent(params.getName()))
                .and(createdTime, SqlBuilder.isGreaterThanOrEqualToWhenPresent(params.getCreatedTime().getLowerBound()))
                .and(createdTime, SqlBuilder.isLessThanWhenPresent(MybatisDynamicUtils.endDateValue(params.getCreatedTime().getUpperBound())))
                .orderBy(MybatisDynamicUtils.orders(paramConfig, pageable.getSort()))
                .build().execute();
        logger.debug("共取得'{}'条记录", entities.size());
        if (entities.isEmpty()) return new PageImpl<>(Collections.emptyList());

        List<ParamConfigVO<Id, OperatorId>> vos = entities.stream().map(this::to).collect(Collectors.toList());
        return new PageImpl<>(vos, pageable, PageHelperUtils.getTotal(entities));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Id, OperatorId> ParamConfigVO<Id, OperatorId> get(ParamConfigGet<Id, OperatorId> params) {
        logger.info("获取符合条件[{}]的信息", params);
        Object[] config = new Object[3];
        if (params.getId() != null) {
            config[0] = "id";
            config[1] = id;
            config[2] = params.getId();
        } else if (params.getCode() != null) {
            config[0] = "code";
            config[1] = code;
            config[2] = params.getCode();
        }
        return paramConfigMapper.<Id, OperatorId>selectByExample()
                .where((SqlColumn<Object>) config[1], SqlBuilder.isEqualToWhenPresent(config[2]))
                .build().execute().stream()
                .reduce((l, r) -> r)
                .map(this::to)
                .orElseThrow(() -> new EntityNotFoundException(ParamConfig.class, (String) config[0], config[2]));
    }

    @Override
    public <Id, OperatorId> String getValue(ParamConfigGet<Id, OperatorId> params) throws ResultException {
        return this.get(params).getValue();
    }

    @Override
    public <Id, OperatorId> int modify(ParamConfigModify<Id, OperatorId> params) {
        logger.info("修改信息[{}]", params);
        ParamConfig<Id, OperatorId> demo = new ParamConfig<>();
        BeanUtils.copyProperties(params, demo);
        demo.setModifierId(params.getOperatorId());
        demo.setModifiedTime(new Date());
        int count = paramConfigMapper.updateByPrimaryKeySelective(demo);
        logger.debug("共影响[{}]条记录", count);
        return count;
    }

    @Override
    public <T, OperatorId> int delete(ParamConfigDelete<T, OperatorId> params) {
        logger.info("删除信息[{}]", params);
        int count = params.getId().length == 1
                ? paramConfigMapper.deleteByPrimaryKey(params.getId()[0])
                : paramConfigMapper.deleteInPrimaryKey(Arrays.asList(params.getId()));
        logger.debug("共影响[{}]条记录", count);
        return count;
    }
}
