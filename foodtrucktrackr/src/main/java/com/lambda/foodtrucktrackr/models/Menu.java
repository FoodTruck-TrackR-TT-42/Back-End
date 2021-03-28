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

    public Menu() {
    }

    public Menu(Truck truck, MenuItem menuitem) {
        this.truck = truck;
        this.menuitem = menuitem;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public MenuItem getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(MenuItem menuitem) {
        this.menuitem = menuitem;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Menu))
        {
            return false;
        }
        Menu that = (Menu) o;
        return ((truck == null) ? 0 : truck.getTruckid()) == ((that.truck == null) ? 0 : that.truck.getTruckid()) &&
                ((menuitem == null) ? 0 : menuitem.getMenuitemid()) == ((that.menuitem == null) ? 0 : that.menuitem.getMenuitemid());
    }

    @Override
    public int hashCode()
    {
        return 1;
    }
}
