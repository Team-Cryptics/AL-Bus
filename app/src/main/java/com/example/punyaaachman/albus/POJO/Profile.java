package com.example.punyaaachman.albus.POJO;

import java.util.List;

/**
 * Created by samarthgupta on 13/04/17.
 */

    public class Profile {

        List<Trips> tripsList;
        User user;

    public Profile() {

    }

    public Profile(User user,List<Trips> tripsList) {
        this.tripsList = tripsList;
        this.user = user;
    }

    public List<Trips> getTripsList() {
        return tripsList;
    }

    public User getUser() {
        return user;
    }
}

