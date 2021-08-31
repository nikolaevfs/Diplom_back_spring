package com.vkr.studentsapp.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vkr.studentsapp.model.Group;
import com.vkr.studentsapp.model.GroupMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class JwtUser implements UserDetails {
    
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final Group group;
    private final String imageURL;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String email;
    private final List<GroupMessage> groupMessages;


    public JwtUser(Long id, String firstName, String lastName, String username, Group group, String imageURL, String password, Collection<? extends GrantedAuthority> authorities, String email, List<GroupMessage> groupMessages) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.group = group;
        this.imageURL = imageURL;
        this.password = password;
        this.authorities = authorities;
        this.email = email;
        this.groupMessages = groupMessages;
    }


    public List<GroupMessage> getGroupMessages() {
        return groupMessages;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return username;
    }

    public Group getGroup() {
        return group;
    }


    public String getImageURL() {
        return imageURL;
    }

    public String getEmail() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
