package com.lambda.foodtrucktrackr.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "menuratings")
public class MenuRating extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long menuratingid;

    private int score;

    @ManyToOne
    @JoinColumn(name = "menuitemid")
    @JsonIgnoreProperties(value = "menuratings", allowSetters = true)
    private MenuItem menuitem;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties(value = "menuratings", allowSetters = true)
    private User user;

    public MenuRating() {
    }

    public MenuRating(int score) {
        this.score = score;
    }

    public long getMenuratingid() {
        return menuratingid;
    }

    public void setMenuratingid(long menuratingid) {
        this.menuratingid = menuratingid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public MenuItem getMenuitem() {
        return menuitem;
    }

    public void setMenuitem(MenuItem menuitem) {
        this.menuitem = menuitem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
