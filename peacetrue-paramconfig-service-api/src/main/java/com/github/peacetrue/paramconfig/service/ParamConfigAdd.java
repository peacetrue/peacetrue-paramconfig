package com.github.peacetrue.paramconfig.service;

import com.github.peacetrue.core.OperatorCapableImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author xiayx
 */
@Getter
@Setter
@ToString(callSuper = true)
public class ParamConfigAdd<OperatorId> extends OperatorCapableImpl<OperatorId> {

    private static final long serialVersionUID = 0L;

    @NotNull
    @Size(min = 1, max = 31)
    private String code;
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @NotNull
    @Size(min = 1, max = 255)
    private String value;
    @Size(min = 1, max = 255)
    private String remark;

}
