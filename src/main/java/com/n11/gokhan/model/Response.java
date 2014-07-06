package com.n11.gokhan.model;

import java.util.List;

public class Response {

    private Status status;
    private List<String> errors;
    private Customer customer;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static enum Status {
        OK, ERROR;
    }
}
