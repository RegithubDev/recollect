package com.resustainability.recollect.service;

import com.resustainability.recollect.dto.response.IUserContext;
import com.resustainability.recollect.entity.principal.UserPrincipal;
import com.resustainability.recollect.exception.InvalidSessionException;
import com.resustainability.recollect.repository.AdminUserRepository;
import com.resustainability.recollect.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class SecurityService {
    private final CustomerRepository customerRepository;
    private final AdminUserRepository adminUserRepository;

    @Autowired
    public SecurityService(
            CustomerRepository customerRepository,
            AdminUserRepository adminUserRepository
    ) {
        this.customerRepository = customerRepository;
        this.adminUserRepository = adminUserRepository;
    }

    /**
     * Retrieves the currently authenticated User entity.
     *
     * <p>This method first fetches the authenticated {@link UserPrincipal} from the security context
     * and then extracts the associated {@link IUserContext} entity.</p>
     *
     * @return an {@link Optional} containing the authenticated {@link IUserContext} if available,
     *         otherwise an empty {@link Optional}.
     */
    public Optional<IUserContext> getCurrentUser() {
        return getCurrentContextUser()
                .flatMap(contextUser -> {
                    if (Boolean.TRUE.equals(contextUser.getIsAdmin())) {
                        return adminUserRepository.loadUserById(contextUser.getId());
                    }
                    if (Boolean.TRUE.equals(contextUser.getIsCustomer())) {
                        return customerRepository.loadUserById(contextUser.getId());
                    }
                    return Optional.empty();
                })
                .map(user -> {
                    final LocalDateTime userTokenAt = user.getTokenAt();
                    final LocalDateTime sessionTokenAt = getCurrentUserTokenAt()
                            .orElseThrow(InvalidSessionException::new);
                    if (null == userTokenAt || !userTokenAt
                            .truncatedTo(ChronoUnit.SECONDS)
                            .equals(sessionTokenAt.truncatedTo(ChronoUnit.SECONDS))
                    ) {
                        throw new InvalidSessionException();
                    }
                    return user;
                });
    }

    public Optional<IUserContext> getCurrentContextUser() {
        return getCurrentUserPrincipal().map(UserPrincipal::getUser);
    }

    public Optional<Long> getCurrentUserId() {
        return getCurrentUserPrincipal().map(UserPrincipal::getUserId);
    }

    public Optional<Long> getCurrentUserDistrictId() {
        return getCurrentUserPrincipal().map(UserPrincipal::getUser).map(IUserContext::getDistrictId);
    }

    public Optional<LocalDateTime> getCurrentUserTokenAt() {
        return getCurrentUserPrincipal().map(UserPrincipal::getTokenAt);
    }

    public Optional<String> getCurrentUserEmail() {
        return getCurrentUserPrincipal().map(UserPrincipal::getUsername);
    }

    /**
     * Retrieves the authenticated UserPrincipal from SecurityContext.
     *
     * @return an Optional containing the UserPrincipal if authenticated, otherwise empty.
     */
    public Optional<UserPrincipal> getCurrentUserPrincipal() {
        return getCurrentAuthentication()
                .map(Authentication::getPrincipal)
                .filter(UserPrincipal.class::isInstance)
                .map(UserPrincipal.class::cast);
    }

    /**
     * Retrieves the currently authenticated user's principal name.
     *
     * @return an Optional containing the principal's name if authenticated, otherwise empty.
     */
    public Optional<String> getCurrentPrincipalName() {
        return getCurrentAuthentication().map(Principal::getName);
    }

    /**
     * Retrieves the current Authentication object from SecurityContext.
     *
     * @return an Optional containing the Authentication if authenticated, otherwise empty.
     */
    public Optional<Authentication> getCurrentAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated);
    }
}
