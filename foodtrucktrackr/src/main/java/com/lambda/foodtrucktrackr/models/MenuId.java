package com.lambda.foodtrucktrackr.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MenuId implements Serializable {
    private long truck;
    private long menuitem;

    public MenuId() {
    }

    public long getTruck() {
        return truck;
    }

    public void setTruck(long truck) {
        this.truck = truck;
    }

    public long getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(long menuitem) {
        this.menuitem = menuitem;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        MenuId that = (MenuId) o;
        return truck == that.truck &&
                menuitem == that.menuitem;
    }

    @Override
    public int hashCode()
    {
        return 1;
    }
}
