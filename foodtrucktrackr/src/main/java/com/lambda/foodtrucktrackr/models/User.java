package com.lambda.foodtrucktrackr.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Entity
@Table(name = "users")
@ApiModel(value = "User", description = "A user of this application")
public class User extends Auditable {
    @ApiModelProperty(name = "user id", value = "primary key for User (generated automatically by database)", example="11")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @ApiModelProperty(name = "username", value = "the username for this user", required = true, example="Tim")
    @Column(nullable = false, unique = true)
    private String username;

    @ApiModelProperty(name = "password", value = "the password used for logging in", required = true, example="i<3Lambd4")
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ApiModelProperty(name = "email", value = "the email the user signed up with (must look like email address)", required = true, example="user@site.com")
    @Column(nullable = false)
    @Email
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Set<UserRoles> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private List<UserTrucks> trucks = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, @Email String email) {
        setUsername(username);
        setPassword(password);
        this.email = email;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public Set<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }

    public List<UserTrucks> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<UserTrucks> trucks) {
        this.trucks = trucks;
    }

    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority() {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.roles) {
            String myRole = "ROLE_" + r.getRole().getName().toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }
}
