package ru.melkov.springcourse.VCRApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.melkov.springcourse.VCRApp.models.OurUser;

import java.util.Collection;
import java.util.Collections;

public class OurUserDetails implements UserDetails {

    private final OurUser ourUser;

    @Autowired
    public OurUserDetails(OurUser ourUser) {
        this.ourUser = ourUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(ourUser.getRole()));
    }

    @Override
    public String getPassword() {
        return this.ourUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.ourUser.getUsername();
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
        return true;
    }

    public OurUser getAppUser() {
        return this.ourUser;
    }
}
