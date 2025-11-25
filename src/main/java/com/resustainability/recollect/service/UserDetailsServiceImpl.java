package com.resustainability.recollect.service;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.commons.ValidationUtils;
import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.entity.principal.UserPrincipal;
import com.resustainability.recollect.exception.UnauthorizedException;
import com.resustainability.recollect.repository.AdminUserRepository;
import com.resustainability.recollect.repository.CustomerRepository;
import com.resustainability.recollect.repository.ProviderRepository;
import com.resustainability.recollect.tag.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final AdminUserRepository adminUserRepository;
    private final ProviderRepository providerRepository;

    @Autowired
    public UserDetailsServiceImpl(
            CustomerRepository customerRepository,
            AdminUserRepository adminUserRepository,
            ProviderRepository providerRepository
    ) {
        this.customerRepository = customerRepository;
        this.adminUserRepository = adminUserRepository;
        this.providerRepository = providerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final IUserContext user = loadUser(username, null);
        return new UserPrincipal(user);
    }

    public UserDetails loadUserAndValidateUAT(String username, String rol, Long uat) throws UsernameNotFoundException {
        final IUserContext user = loadUser(username, rol);

        if (null == user || null == user.getTokenAt() || null != uat && uat != user.getTokenAt().atZone(ZoneId.systemDefault()).toEpochSecond()) {
            throw new UnauthorizedException(true);
        }

        return new UserPrincipal(user);
    }

    public IUserContext loadUser(String username, String rol) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(rol)) {
            throw new UsernameNotFoundException(Default.ERROR_NOT_FOUND_USER);
        }

        final IUserContext user = switch (Role.fromAbbreviation(rol)) {
            case CUSTOMER -> customerRepository
                    .loadUserByUsername(username)
                    .filter(usr -> !Boolean.TRUE.equals(usr.getIsDeleted()))
                    .orElseThrow(() -> new UsernameNotFoundException(Default.ERROR_NOT_FOUND_USER));
            case ADMIN -> adminUserRepository
                    .loadUserByUsername(username)
                    .filter(usr -> !Boolean.TRUE.equals(usr.getIsDeleted()))
                    .orElseThrow(() -> new UsernameNotFoundException(Default.ERROR_NOT_FOUND_USER));
            case PROVIDER -> providerRepository
                    .loadUserByUsername(username)
                    .filter(usr -> !Boolean.TRUE.equals(usr.getIsDeleted()))
                    .orElseThrow(() -> new UsernameNotFoundException(Default.ERROR_NOT_FOUND_USER));
        };

        ValidationUtils.validateUserActiveStatus(user::getIsActive);

        return user;
    }
}
