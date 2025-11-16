package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.entity.principal.UserPrincipal;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.AdminUserRepository;
import com.resustainability.recollect.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final AdminUserRepository adminUserRepository;

    @Autowired
    public UserDetailsServiceImpl(
            CustomerRepository customerRepository,
            AdminUserRepository adminUserRepository
    ) {
        this.customerRepository = customerRepository;
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final IUserContext user = loadUser(username);
        return new UserPrincipal(user);
    }

    public UserDetails loadUserAndValidateUAT(String username, Long uat) throws UsernameNotFoundException {
        final IUserContext user = loadUser(username);

        if (null == user || null == user.getTokenAt() || null != uat && uat != user.getTokenAt().atZone(ZoneId.systemDefault()).toEpochSecond()) {
            throw new UnauthorizedException();
        }

        return new UserPrincipal(user);
    }

    public IUserContext loadUser(String username) {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException(Default.ERROR_NOT_FOUND_USER);
        }

        final IUserContext user = customerRepository
                .loadUserByUsername(username)
                .orElseGet(() -> adminUserRepository
                        .loadUserByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(Default.ERROR_NOT_FOUND_USER))
                );

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new AuthenticationServiceException(Default.ERROR_ACCOUNT_DISABLED);
        }

        return user;
    }
}
