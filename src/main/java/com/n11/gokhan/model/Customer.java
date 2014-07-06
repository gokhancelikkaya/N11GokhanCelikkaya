package com.n11.gokhan.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {

    @Id
    private String id;

    @NotEmpty(message = "customer.firstName.notnull")
    @Size(min = 2, max = 20, message = "customer.firstName.size")
    @Pattern(regexp = "[A-Za-z ıİğĞüÜöÖçÇşŞ]+", message = "customer.firstName.invalid")
    private String firstName;

    @NotEmpty(message = "customer.lastName.notnull")
    @Size(min = 2, max = 30, message = "customer.lastName.size")
    @Pattern(regexp = "[A-Za-z ıİğĞüÜöÖçÇşŞ]+", message = "customer.lastName.invalid")
    private String lastName;

    @Size(min = 0, max = 20, message = "customer.phone.size")
    @Pattern(regexp = "[0-9 \\(\\)]*", message = "customer.phone.invalid")
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Customer() {
    }

}