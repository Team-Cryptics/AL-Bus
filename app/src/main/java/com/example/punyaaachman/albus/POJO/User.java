package com.example.punyaaachman.albus.POJO;

/**
 * Created by samarthgupta on 13/04/17.
 */

public class User {

        String firstName,lastName,number,email;
        double balance;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User() {

        }

        public User(String email,String firstName, String lastName, String number) {
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.number = number;
            balance=0;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getNumber() {
            return number;
        }

        public double getBalance() {
            return balance;
        }

        public String getEmail() {
            return email;
        }
    }


