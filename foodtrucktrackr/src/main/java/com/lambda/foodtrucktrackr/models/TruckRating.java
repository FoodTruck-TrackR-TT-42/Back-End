package com.lambda.foodtrucktrackr.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "truckratings")
public class TruckRating extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long truckratingid;

    private int score;

    @ManyToOne
    @JoinColumn(name = "truckid")
    @JsonIgnoreProperties(value = "truckratings", allowSetters = true)
    private Truck truck;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "truckratings", allowSetters = true)
    private User user;

    public TruckRating() {
    }

    public TruckRating(int score, Truck truck, User user) {
        this.score = score;
        this.truck = truck;
        this.user = user;
    }

    public long getTruckratingid() {
        return truckratingid;
    }

    public void setTruckratingid(long truckratingid) {
        this.truckratingid = truckratingid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
