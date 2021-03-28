package com.lambda.foodtrucktrackr.models;

import javax.persistence.*;

@Entity
@Table(name = "menuitems")
public class MenuItem extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long menuitemid;

    private String itemname;

    private double itemprice;
}
