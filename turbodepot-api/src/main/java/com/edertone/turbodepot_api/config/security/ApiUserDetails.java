package com.edertone.turbodepot_api.config.security;

import com.edertone.turbodepot_api.model.rdb.User;
import com.edertone.turbodepot_api.model.rdb.UserPassword;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom {@link UserDetails} for authenticated users.
 * <p>
 * Details are based on one {@link User} model.
 */
public class ApiUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = -5845306093173135762L;

    private final User user;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public ApiUserDetails(User user, Set<String> operations) {
        this.user = user;
        this.password = Optional.ofNullable(user.getPassword()).map(UserPassword::getPassword).orElse(null);

        // Note that it's important to use Collections.unmodifiableSet() to Jackson deserialization mechanism
        // to work with this class, because this method return type is registered in the CoreJackson2Module class.
        this.authorities = operations
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toUnmodifiableSet());
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof ApiUserDetails other)) {
            return false;
        } else {
            return Objects.equals(user, other.user);
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
