package com.example.frontend.controller;

import com.example.frontend.ds.Customer;
import com.example.frontend.ds.Customers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {

    @Value("${backend.app.url}")
    private String backendUrl;

    private RestTemplate restTemplate = new RestTemplate();


    @GetMapping("/customers")
    public ModelAndView listAllCustomers(){
        ResponseEntity<Customers> customersResponseEntity =
                restTemplate.getForEntity(backendUrl+"/api/customers", Customers.class);

        if (customersResponseEntity.getStatusCode().equals(HttpStatus.OK)){
            return new ModelAndView("customers","customers",customersResponseEntity.getBody().getCustomers());
        }
        else {
            throw new IllegalStateException(String.format(
                    "Unable to list customers , received status %s" ,
                    customersResponseEntity.getStatusCode()
            ));
        }
    }

    @GetMapping("/customers/delete")
    public String deleteCustomer(@RequestParam("id") int id){
        restTemplate.delete(backendUrl + "api/customers/{id}" , id);
        return "redirect:/customers";
    }

    @GetMapping("/customers/create")
    public String createCustomer(Model model){
        model.addAttribute("customer", new Customer());
        return "customerForm";
    }

    @PostMapping("/customers/create")
    public String saveCustomer(Customer customer, BindingResult result){
        if (result.hasErrors()){
            return "customerForm";
        }

      ResponseEntity<Customer> customerResponseEntity =
              restTemplate.postForEntity(backendUrl + "/api/customers",customer,Customer.class);

        if (customerResponseEntity.getStatusCode() != HttpStatus.OK){
            throw new IllegalStateException(
                    String.format(
                            "Unable to create new Customer, received status code %s"
                    )
            );
        }
        return "redirect:/customers";
    }
}
