package com.resustainability.recollect.entity.principal;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.tag.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserPrincipal implements UserDetails {
    private final transient IUserContext user;

    @Autowired
    public UserPrincipal(IUserContext user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (null == user) {
            return Collections.emptyList();
        }

        return Stream
                .of(
                        Boolean.TRUE.equals(user.getIsAdmin()) ? Role.ADMIN.name() : null,
                        Boolean.TRUE.equals(user.getIsCustomer()) ? Role.CUSTOMER.name() : null,
                        Boolean.TRUE.equals(user.getRoleActive()) && StringUtils.isNotBlank(user.getRoleName()) ? user.getRoleName() : null
                )
                .filter(StringUtils::isNotBlank)
                .map(value -> Default.ROLE_PREFIX + value.toUpperCase())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public IUserContext getUser() {
        return user;
    }

    public Long getUserId() {
        return null != user ? user.getId() : null;
    }

    public LocalDateTime getTokenAt() {
        return null != user ? user.getTokenAt() : null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null != user ? user.getUsername() : null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return null != user && Boolean.TRUE.equals(user.getIsActive());
    }
}
