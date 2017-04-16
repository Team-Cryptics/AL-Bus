package com.example.punyaaachman.albus.POJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samarthgupta on 13/04/17.
 */

    public class Profile {

        ArrayList<Trips> tripsList;
        User user;

    public void setTripsList(ArrayList<Trips> tripsList) {
        this.tripsList = tripsList;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile() {
        user= null;
        tripsList=null;

    }

    public Profile(User user) {
        tripsList = new ArrayList<>();
        this.user = user;
    }

    public ArrayList<Trips> getTripsList() {
        return tripsList;
    }

    public User getUser() {
        return user;
    }
}

