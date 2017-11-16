package ovh.kocproz.markpages.controller;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AdvisorController {

    private Logger logger;

    public AdvisorController(Logger logger) {
        this.logger = logger;
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleNullPointer(HttpServletRequest request, Exception ex) {
        logger.error("NullPointerException at " + request.getRequestURI(), ex);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

}
