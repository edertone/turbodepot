package com.edertone.turbodepot_api.config.security;

import com.edertone.turbodepot_api.model.rdb.*;
import com.edertone.turbodepot_api.repository.OperationRoleRepository;
import com.edertone.turbodepot_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Custom {@link UserDetailsService} to load user details from the database.
 */
@Component
public class ApiUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final OperationRoleRepository operationRoleRepository;

    public ApiUserDetailsService(
        UserRepository userRepository, OperationRoleRepository operationRoleRepository
    ) {
        this.userRepository = userRepository;
        this.operationRoleRepository = operationRoleRepository;
    }

    /**
     * Locate the user based on the username. In this case, the username is expected to be in the format
     * "tenant###username" to support multi-tenancy.
     *
     * @param name the username identifying the user whose data is required.
     * @return a fully populated user record (never {@code null})
     * @throws UsernameNotFoundException if the user could not be found
     */
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        var tenant = UsernameTenantPasswordAuthenticationToken.requireTenant(name);
        var username = UsernameTenantPasswordAuthenticationToken.requireUsername(name);

        var user = userRepository.findByUsernameAndTenant(username, tenant)
            .orElseThrow(() -> new UsernameNotFoundException(
                String.format("User not found with username %s for tenant %s", username, tenant)));

        var roles = user.getRoles().stream().map(UserRole::getId).map(UserRoleId::getValue).toList();
        var operations = operationRoleRepository.findByRolesAndTenant(roles, user.getTenant().getName())
            .stream()
            .map(OperationRole::getId)
            .map(OperationRoleId::getOperation)
            .map(Operation::getId)
            .map(OperationId::getName)
            .collect(Collectors.toSet());

        return new ApiUserDetails(user, operations);
    }
}
