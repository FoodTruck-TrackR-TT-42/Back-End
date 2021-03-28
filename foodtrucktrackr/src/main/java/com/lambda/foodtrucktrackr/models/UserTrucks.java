package com.lambda.foodtrucktrackr.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usertrucks")
@IdClass(UserTrucksId.class)
public class UserTrucks extends Auditable implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "trucks", allowSetters = true)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "truckid")
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private Truck truck;

    public UserTrucks() {
    }

    public UserTrucks(User user, Truck truck) {
        this.user = user;
        this.truck = truck;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof UserTrucks))
        {
            return false;
        }
        UserTrucks that = (UserTrucks) o;
        return ((user == null) ? 0 : user.getUserid()) == ((that.user == null) ? 0 : that.user.getUserid()) &&
                ((truck == null) ? 0 : truck.getTruckid()) == ((that.truck == null) ? 0 : that.truck.getTruckid());
    }

    @Override
    public int hashCode()
    {
        return 1;
    }
}
