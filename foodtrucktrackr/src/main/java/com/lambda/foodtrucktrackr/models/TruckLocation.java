package com.lambda.foodtrucktrackr.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "trucklocations")
@ApiModel(value = "TruckLocation", description = "An object with data about a truck's location")
public class TruckLocation extends Auditable {
    @ApiModelProperty(name = "current location id", value = "primary key for CurrentLocation (generated automatically by the database", example = "29")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long trucklocationid;

    @ApiModelProperty(name = "latitude", value = "the truck's latitude", required = true)
    @Column(nullable = false)
    private String latitude;

    @ApiModelProperty(name = "longitude", value = "the truck's longitude", required = true)
    @Column(nullable = false)
    private String longitude;

    @ApiModelProperty(name = "departure time", value = "the time the truck will be leaving this location")
    private String departuretime;

    @OneToOne(mappedBy = "currentlocation")
    private Truck truck;

    public TruckLocation() {
    }

    public TruckLocation(String latitude, String longitude, String departuretime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.departuretime = departuretime;
    }

    public TruckLocation(String latitude, String longitude, String departuretime, Truck truck) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.departuretime = departuretime;
        this.truck = truck;
    }

    public long getTrucklocationid() {
        return trucklocationid;
    }

    public void setTrucklocationid(long trucklocationid) {
        this.trucklocationid = trucklocationid;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDeparturetime() {
        return departuretime;
    }

    public void setDeparturetime(String departuretime) {
        this.departuretime = departuretime;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }
}
