package com.example.elk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class EKLController {
    private static final Logger LOG = Logger.getLogger(EKLController.class.getName());

    @Autowired
    RestTemplate restTemplete;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RequestMapping(value = "/elkdemo")
    public String helloWorld() {
        String response = "Hello user ! " + new Date();
        LOG.log(Level.INFO, "/elkdemo - &gt; " + response);

        return response;
    }

    @RequestMapping(value = "/elk")
    public String helloWorld1() {

//        String response = restTemplete.exchange(
//                "http://localhost:8080/elkdemo",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference() {}
//        ).getBody().toString();

        String response = restTemplete.getForObject("http://localhost:8080/elkdemo", String.class);

        LOG.log(Level.INFO, "/elk - &gt; " + response);

        try {

//            String exceptionrsp = restTemplete.exchange(
//                    "http://localhost:8080/exception",
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference() {
//                    }
//            ).getBody().toString();

//            String exceptionrsp = restTemplete.getForObject("http://localhost:8080/exception", String.class);
//
//            LOG.log(Level.INFO, "/elk trying to print exception - &gt; " + exceptionrsp);
//            response = response + " === " + exceptionrsp;
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error: " + e.getMessage());
            // exception should not reach here. Really bad practice :)
        }

        return response;
    }

    @RequestMapping(value = "/exception")
    public String exception() {
        String rsp = "";
        try {
            int i = 1 / 0;
            // should get exception
        } catch (Exception e) {
            e.printStackTrace();
//            LOG.error(e);

            LOG.log(Level.WARNING, e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            LOG.log(Level.WARNING, "Exception As String :: - &gt; " + sStackTrace);
//            LOG.error("Exception As String :: - &gt; " + sStackTrace);

            rsp = sStackTrace;
        }

        return rsp;
    }

}
