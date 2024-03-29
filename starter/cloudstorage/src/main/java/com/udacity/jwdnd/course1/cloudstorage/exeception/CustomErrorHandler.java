package com.udacity.jwdnd.course1.cloudstorage.exeception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorHandler implements ErrorController {
    Logger logger = LoggerFactory.getLogger(CustomErrorHandler.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        if (status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            logger.error(message.toString() +" "+statusCode);
            if (statusCode == HttpStatus.NOT_FOUND.value()){
                return "error/error-404";
            }else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                return "error/error-500";
            }
        }
        if (message != null)
            logger.error(message.toString());
        return "error/index";
    }
}
