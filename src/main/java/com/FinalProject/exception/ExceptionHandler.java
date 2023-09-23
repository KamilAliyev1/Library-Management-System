package com.FinalProject.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

public class ExceptionHandler {

    public static void setExceptionMessage(Model model, HttpServletRequest request) {
        model.addAttribute("exception", request.getSession().getAttribute("exception"));
        request.getSession().setAttribute("exception", null);
    }
}
