package com.n11.gokhan.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.n11.gokhan.model.Customer;
import com.n11.gokhan.model.Response;
import com.n11.gokhan.util.PropertyUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PropertyUtils propertyUtils;

    @RequestMapping(value = "/ajax", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteCustomer(@RequestBody Customer customer, BindingResult result) {
        Response response = new Response();

        String id = customer.getId();
        if (id == null || id == "") {
            response.setStatus(Response.Status.ERROR);
            List<String> errorList = new ArrayList<>();
            errorList.add(propertyUtils.readFromProperty("customer.id.notnull"));
            response.setErrors(errorList);
        } else {
            Customer c = mongoTemplate.findById(id, Customer.class);

            if (c == null) {
                response.setStatus(Response.Status.ERROR);
                List<String> errorList = new ArrayList<>();
                errorList.add(propertyUtils.readFromProperty("customer.notFound"));
                response.setErrors(errorList);
            } else {
                mongoTemplate.remove(c);
                response.setStatus(Response.Status.OK);
            }
        }

        return toJSON(response);
    }

    @RequestMapping(value = "/ajax", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        Response response = new Response();

        String id = customer.getId();
        if (id == null || id == "") {
            response.setStatus(Response.Status.ERROR);
            List<String> errorList = new ArrayList<>();
            errorList.add(propertyUtils.readFromProperty("customer.id.notnull"));
            response.setErrors(errorList);
        } else {
            Customer c = mongoTemplate.findById(id, Customer.class);

            if (c == null) {
                response.setStatus(Response.Status.ERROR);
                List<String> errorList = new ArrayList<>();
                errorList.add(propertyUtils.readFromProperty("customer.notFound"));
                response.setErrors(errorList);
            } else {
                mongoTemplate.save(customer);
                response.setStatus(Response.Status.OK);
                response.setCustomer(customer);
            }
        }

        return toJSON(response);
    }

    @RequestMapping(value = "/ajax", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addCustomer(@Valid @RequestBody Customer customer, BindingResult result) {

        Response response = new Response();

        if (result.hasErrors()) {
            response.setStatus(Response.Status.ERROR);
            List<String> errorList = new ArrayList<>();

            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                errorList.add(propertyUtils.readFromProperty(objectError.getDefaultMessage()));
            }
            response.setErrors(errorList);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("firstName").is(customer.getFirstName()).and("lastName")
                    .is(customer.getLastName()));
            Customer c = mongoTemplate.findOne(query, Customer.class);

            if (c == null) {

                this.mongoTemplate.save(customer);

                response.setStatus(Response.Status.OK);
                response.setCustomer(customer);
            } else {
                response.setStatus(Response.Status.ERROR);
                List<String> errorList = new ArrayList<>();
                errorList.add(propertyUtils.readFromProperty("customer.exists"));
                response.setErrors(errorList);
            }
        }

        return toJSON(response);

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView displayCustomerForm() {

        ModelAndView model = new ModelAndView("Main");

        List<Customer> customers = mongoTemplate.findAll(Customer.class);

        model.addObject("customers", customers);

        return model;

    }

    public String toJSON(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        String responseString = "";
        try {
            responseString = mapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return responseString;
    }

}