package com.github.peacetrue.paramconfig.service;

import com.github.peacetrue.core.OperatorCapableImpl;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author xiayx
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ParamConfigDelete<Id, OperatorId> extends OperatorCapableImpl<OperatorId> {

    private static final long serialVersionUID = 0L;

    @NotNull
    @Min(1)
    private Id[] id;

}
