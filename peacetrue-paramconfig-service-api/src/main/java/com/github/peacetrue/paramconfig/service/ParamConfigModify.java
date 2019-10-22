package com.github.peacetrue.paramconfig.service;

import com.github.peacetrue.core.OperatorCapableImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * @author xiayx
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ParamConfigModify<Id, OperatorId> extends OperatorCapableImpl<OperatorId> {

    private static final long serialVersionUID = 0L;

    private Id id;
    @Size(max = 31)
    private String code;
    @Size(max = 255)
    private String name;
    @Size(max = 255)
    private String value;
    @Size(max = 255)
    private String remark;

}
