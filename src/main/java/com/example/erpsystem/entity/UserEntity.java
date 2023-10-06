package com.example.erpsystem.entity;

import com.example.erpsystem.entity.enums.Permissions;
import com.example.erpsystem.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity implements UserDetails {
    {
        this.role = UserRole.USER;
    }

    private String fullName;
    @Column(unique = true)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String phoneNumber;
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    @Enumerated(value = EnumType.STRING)
    private List<Permissions> permissions;


    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>(Set.of(new SimpleGrantedAuthority("ROLE_" + role.name())));
        if (permissions != null) {
            simpleGrantedAuthorities.addAll(Arrays.stream(Permissions.values()).map(
                    permission -> new SimpleGrantedAuthority(permission.name())
            ).toList());
        }
        return simpleGrantedAuthorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return
     * <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return userName;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     * <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
