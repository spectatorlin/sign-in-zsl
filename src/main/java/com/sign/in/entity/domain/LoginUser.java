package com.sign.in.entity.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import com.sign.in.entity.SysUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;


/**
 * 登录用户身份权限
 *
 * @author ruoyi
 */
@Data
@AllArgsConstructor
public class LoginUser  {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户唯一标识
     */
    private String token;


    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;


    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 资源列表
     */
    private Set<String> resources;

    /**
     * 用户信息
     */
    private SysUserEntity user;

    public LoginUser(Long userId, SysUserEntity user, Set<String> permissions, Set<String> resources) {
        this.userId = userId;
        this.user = user;
        this.permissions = permissions;
        this.resources = resources;
    }


    @JSONField(serialize = false)
    public String getPassword() {
        return user.getPassword();
    }


    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return true;
    }

}
