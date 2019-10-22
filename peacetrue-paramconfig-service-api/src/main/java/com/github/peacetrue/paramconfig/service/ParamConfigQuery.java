package com.github.peacetrue.paramconfig.service;

import com.github.peacetrue.core.Range;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiayx
 */
@Data
@NoArgsConstructor
public class ParamConfigQuery implements Serializable {

    public static final ParamConfigQuery DEFAULT = new ParamConfigQuery();

    private static final long serialVersionUID = 0L;

    private String code;
    private String name;
    private Range.Date createdTime;


}
