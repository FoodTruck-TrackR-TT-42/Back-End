package com.lambda.foodtrucktrackr.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menuitems")
@ApiModel(value = "MenuItem", description = "A food item on a truck's menu")
public class MenuItem extends Auditable {
    @ApiModelProperty(name = "menu item id", value = "primary key for MenuItem (automatically generated by database)", example = "23")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long menuitemid;

    @ApiModelProperty(name = "item name", value = "the name of this menu item", example = "California Roll", required = true)
    @Column(nullable = false)
    private String itemname;

    @ApiModelProperty(name = "item price", value = "the price of this menu item", example = "7.99")
    private double itemprice;

    @OneToMany(mappedBy = "menuitem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "menuitem", allowSetters = true)
    private List<Truck> trucks = new ArrayList<>();

    @OneToMany(mappedBy = "menuitem", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "menuitem", allowSetters = true)
    private List<MenuItemPhoto> menuitemphotos = new ArrayList<>();

    public MenuItem() {
    }

    public MenuItem(String itemname, double itemprice) {
        this.itemname = itemname;
        this.itemprice = itemprice;
    }

    public long getMenuitemid() {
        return menuitemid;
    }

    public void setMenuitemid(long menuitemid) {
        this.menuitemid = menuitemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public double getItemprice() {
        return itemprice;
    }

    public void setItemprice(double itemprice) {
        this.itemprice = itemprice;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }
}
