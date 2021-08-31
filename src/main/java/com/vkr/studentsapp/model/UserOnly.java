package com.vkr.studentsapp.model;

import java.util.List;

public class UserOnly{
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Group group;
    private String imageURL;
    private String email;
    private String password;
    private List<Role> roles;

    public UserOnly() {

    }

    public UserOnly(Long id, String firstName, String lastName, String username, Group group, String imageURL, String email, String password, List<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.group = group;
        this.imageURL = imageURL;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }


    public UserOnly getOnlyUser(User user){
        UserOnly userOnly = new UserOnly(user.getId(), user.getFirstName(), user.getLastName(),user.getUsername(), user.getGroup(),user.getImageURL(),user.getEmail(),user.getPassword(),user.getRoles());

        return userOnly;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
