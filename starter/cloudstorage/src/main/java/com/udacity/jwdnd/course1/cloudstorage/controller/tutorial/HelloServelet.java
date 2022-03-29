package com.udacity.jwdnd.course1.cloudstorage.controller.tutorial;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//@RestController
@WebServlet(name="helloServlet", urlPatterns ="/helloServlet")
public class HelloServelet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Running Hello Servlet doGet method");
    }
}
