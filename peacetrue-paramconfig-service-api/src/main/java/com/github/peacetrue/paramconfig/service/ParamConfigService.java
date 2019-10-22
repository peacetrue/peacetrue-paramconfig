package com.github.peacetrue.paramconfig.service;

import com.github.peacetrue.result.exception.ResultException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nullable;

/**
 * @author xiayx
 */
public interface ParamConfigService {

    /** 新增 */
    <Id, OperatorId> ParamConfigVO<Id, OperatorId> add(ParamConfigAdd<OperatorId> params) throws ResultException;

    /** 分页查询 */
    <Id, OperatorId> Page<ParamConfigVO<Id, OperatorId>> query(@Nullable ParamConfigQuery params, @Nullable Pageable pageable) throws ResultException;

    /** 获取 */
    <Id, OperatorId> ParamConfigVO<Id, OperatorId> get(ParamConfigGet<Id, OperatorId> params) throws ResultException;

    /** 获取值 */
    <Id, OperatorId> String getValue(ParamConfigGet<Id, OperatorId> params) throws ResultException;

    /** 获取值 */
    default String getValue(String code) throws ResultException {
        return this.getValue(new ParamConfigGet<>(null, code));
    }

    /** 修改 */
    <Id, OperatorId> int modify(ParamConfigModify<Id, OperatorId> params) throws ResultException;

    /** 删除 */
    <Id, OperatorId> int delete(ParamConfigDelete<Id, OperatorId> params) throws ResultException;
}
