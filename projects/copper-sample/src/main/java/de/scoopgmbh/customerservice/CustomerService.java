/*
 * Copyright 2002-2011 SCOOP Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.scoopgmbh.customerservice;

import java.util.concurrent.Future;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.Response;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2011-10-12T09:59:16.203+02:00
 * Generated source version: 2.4.2
 * 
 */
@WebService(targetNamespace = "http://customerservice.scoopgmbh.de/", name = "CustomerService")
@XmlSeeAlso({ObjectFactory.class})
public interface CustomerService {

    @RequestWrapper(localName = "resetMailbox", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.ResetMailbox")
    @ResponseWrapper(localName = "resetMailboxResponse", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.ResetMailboxResponse")
    @WebMethod(operationName = "resetMailbox")
    public Response<de.scoopgmbh.customerservice.ResetMailboxResponse> resetMailboxAsync(
        @WebParam(name = "customerId", targetNamespace = "")
        int customerId
    );

    @RequestWrapper(localName = "resetMailbox", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.ResetMailbox")
    @ResponseWrapper(localName = "resetMailboxResponse", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.ResetMailboxResponse")
    @WebMethod(operationName = "resetMailbox")
    public Future<?> resetMailboxAsync(
        @WebParam(name = "customerId", targetNamespace = "")
        int customerId,
        @WebParam(name = "asyncHandler", targetNamespace = "")
        AsyncHandler<de.scoopgmbh.customerservice.ResetMailboxResponse> asyncHandler
    );

    @RequestWrapper(localName = "resetMailbox", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.ResetMailbox")
    @WebMethod
    @ResponseWrapper(localName = "resetMailboxResponse", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.ResetMailboxResponse")
    public void resetMailbox(
        @WebParam(name = "customerId", targetNamespace = "")
        int customerId
    ) throws NoSuchCustomerException;

    @RequestWrapper(localName = "getCustomersByName", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.GetCustomersByName")
    @ResponseWrapper(localName = "getCustomersByNameResponse", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.GetCustomersByNameResponse")
    @WebMethod(operationName = "getCustomersByName")
    public Response<de.scoopgmbh.customerservice.GetCustomersByNameResponse> getCustomersByNameAsync(
        @WebParam(name = "name", targetNamespace = "")
        java.lang.String name
    );

    @RequestWrapper(localName = "getCustomersByName", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.GetCustomersByName")
    @ResponseWrapper(localName = "getCustomersByNameResponse", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.GetCustomersByNameResponse")
    @WebMethod(operationName = "getCustomersByName")
    public Future<?> getCustomersByNameAsync(
        @WebParam(name = "name", targetNamespace = "")
        java.lang.String name,
        @WebParam(name = "asyncHandler", targetNamespace = "")
        AsyncHandler<de.scoopgmbh.customerservice.GetCustomersByNameResponse> asyncHandler
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getCustomersByName", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.GetCustomersByName")
    @WebMethod
    @ResponseWrapper(localName = "getCustomersByNameResponse", targetNamespace = "http://customerservice.scoopgmbh.de/", className = "de.scoopgmbh.customerservice.GetCustomersByNameResponse")
    public de.scoopgmbh.customerservice.Customer getCustomersByName(
        @WebParam(name = "name", targetNamespace = "")
        java.lang.String name
    ) throws NoSuchCustomerException;
}