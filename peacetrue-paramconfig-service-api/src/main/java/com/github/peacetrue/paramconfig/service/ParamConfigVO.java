package com.github.peacetrue.paramconfig.service;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiayx
 */
@Data
public class ParamConfigVO<Id, OperatorId> implements Serializable {

    private static final long serialVersionUID = 0L;

    private Id id;
    private String code;
    private String name;
    private String value;
    private String remark;
    private OperatorId creatorId;
    private Date createdTime;
    private OperatorId modifierId;
    private Date modifiedTime;
}
