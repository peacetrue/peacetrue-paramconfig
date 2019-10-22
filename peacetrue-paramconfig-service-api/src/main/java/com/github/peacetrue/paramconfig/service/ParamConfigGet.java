package com.github.peacetrue.paramconfig.service;

import com.github.peacetrue.core.OperatorCapableImpl;
import com.github.peacetrue.validation.constraints.multinotnull.MultiNotNull;
import lombok.*;

import javax.validation.constraints.Size;

/**
 * @author xiayx
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@MultiNotNull(propertyNames = {"id", "code"})
public class ParamConfigGet<Id, OperatorId> extends OperatorCapableImpl<OperatorId> {

    private static final long serialVersionUID = 0L;

    private Id id;
    private String code;

    public ParamConfigGet(Id id) {
        this.id = id;
    }
}
