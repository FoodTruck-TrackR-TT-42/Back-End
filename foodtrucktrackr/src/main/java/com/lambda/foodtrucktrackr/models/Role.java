package com.lambda.foodtrucktrackr.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@ApiModel(value = "Role", description = "The user's role: either operator or diner")
public class Role extends Auditable {
    @ApiModelProperty(name = "role id", value = "primary key for Role (generated automatically by database)", example="7")
    @Id
    @GeneratedValue
    private long roleid;

    @ApiModelProperty(name = "name", value = "the name of this role", example="operator")
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "role", allowSetters = true)
    private Set<UserRoles> users = new HashSet<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name.toUpperCase();
    }

    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public Set<UserRoles> getUsers() {
        return users;
    }

    public void setUsers(Set<UserRoles> users) {
        this.users = users;
    }
}
