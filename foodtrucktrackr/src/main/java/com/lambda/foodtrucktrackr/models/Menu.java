package com.lambda.foodtrucktrackr.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "menus")
@IdClass(MenuId.class)
public class Menu extends Auditable implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "truckid")
    @JsonIgnoreProperties(value = "menus", allowSetters = true)
    private Truck truck;

    @Id
    @ManyToOne
    @JoinColumn(name = "menuitemid")
    @JsonIgnoreProperties(value = "trucks", allowSetters = true)
    private MenuItem menuitem;
}
