package ovh.kocproz.markpages.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdvisorController {

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointer() {
        return "redirect:/";
    }

}
