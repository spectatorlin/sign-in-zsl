package com.sign.in.entity.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sign.in.entity.IUser;
import lombok.Data;

/**
 * @author 邹松林
 * @version 1.0
 * @Title: IUserVO
 * @Description: TODO
 * @date 2024/4/16 2:17
 */
@Data
public class IUserVO {
    @JsonProperty(value = "iUser")
    private IUser iUser;

    private String code;
}
