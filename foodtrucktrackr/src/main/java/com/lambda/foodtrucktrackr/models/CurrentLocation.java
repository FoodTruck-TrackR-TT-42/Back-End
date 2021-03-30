package com.lambda.foodtrucktrackr.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name = "currentlocations")
@ApiModel(value = "CurrentLocation", description = "An object with data about a truck's location")
public class CurrentLocation extends Auditable {
    @ApiModelProperty(name = "current location id", value = "primary key for CurrentLocation (generated automatically by the database", example = "29")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long currentlocationid;

    @ApiModelProperty(name = "location", value = "the location of the truck", required = true)
    @Column(nullable = false)
    private String location;

    @ApiModelProperty(name = "departure time", value = "the time the truck will be leaving this location")
    private String departuretime;


}
