package com.hello.rest;

import java.util.zip.DataFormatException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hello.domain.Customer;
import com.hello.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/hello/v1/customers")
@Api(tags = {"customers"})
public class CustomerController extends AbstractRestHandler {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a hotel resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createCustomer(@RequestBody Customer customer,
                                 HttpServletRequest request, HttpServletResponse response) {
        Customer createdCustomer = this.customerService.createCustomer(customer);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdCustomer.getId()).toString());
    }

//    @RequestMapping(value = "",
//            method = RequestMethod.GET,
//            produces = {"application/json", "application/xml"})
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Get a paginated list of all hotels.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
//    public
//    @ResponseBody
//    Page<Hotel> getAllHotel(@ApiParam(value = "The page number (zero-based)", required = true)
//                                      @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
//                                      @ApiParam(value = "Tha page size", required = true)
//                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
//                                      HttpServletRequest request, HttpServletResponse response) {
//        return this.hotelService.getAllHotels(page, size);
//    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single customer.", notes = "You have to provide a valid customer ID.")
    public
    @ResponseBody
    Customer getCustomer(@ApiParam(value = "The ID of the customer.", required = true)
                             @PathVariable("id") String id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Customer customer = this.customerService.getCustomerById(id);
        checkResourceFound(customer);
        //todo: http://goo.gl/6iNAkz
        return customer;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a hotel resource.", notes = "You have to provide a valid customer ID in the URL and "
    		+ "in the payload. The ID attribute can not be updated.")
    public void updateCustomer(@ApiParam(value = "The ID of the existing customer resource.", required = true)
                                 @PathVariable("id") String id, @RequestBody Customer customer,
                                 HttpServletRequest request, HttpServletResponse response) throws DataFormatException {
        checkResourceFound(this.customerService.getCustomerById(id));
        if (id != customer.getId()) throw new DataFormatException("ID doesn't match!");
        this.customerService.updateCustomer(customer);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a hotel resource.", notes = "You have to provide a valid hotel ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteHotel(@ApiParam(value = "The ID of the existing hotel resource.", required = true)
                                 @PathVariable("id") String id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.customerService.getCustomerById(id));
        this.customerService.deleteCustomer(id);
    }
}
