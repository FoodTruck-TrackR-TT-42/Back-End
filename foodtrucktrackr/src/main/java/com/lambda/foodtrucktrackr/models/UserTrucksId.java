package com.lambda.foodtrucktrackr.models;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserTrucksId implements Serializable {
    private long user;

    private long truck;

    public UserTrucksId() {
    }

    public long getUser() { return user; }

    public void setUser(long user) { this.user = user; }

    public long getTruck() { return truck; }

    public void setTruck(long truck) { this.truck = truck; }

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
        UserTrucksId that = (UserTrucksId) o;
        return user == that.user &&
                truck == that.truck;
    }

    @Override
    public int hashCode()
    {
        return 1;
    }
}
