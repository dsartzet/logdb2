package com.logdb.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.logdb.dto.ClientDto;
import com.logdb.service.ClientService;

@Controller
public class RegistrationController {

    protected static final String REGISTRATION = "/registration";
    protected static final String CLIENT = "client";
    protected static final String EMAIL = "email";
    protected static final String ERROR_CLIENT = "error.clientService";
    protected static final String THERE_IS_ALREADY_A_CLIENT_REGISTERED_WITH_THE_EMAIL_PROVIDED = "There is already a clientService registered with the email provided";
    protected static final String SUCCESS_MESSAGE = "successMessage";
    protected static final String CLIENT_HAS_BEEN_REGISTERED_SUCCESSFULLY = "Client has been registered successfully";
    
    @Autowired
    protected ClientService clientService;

    @RequestMapping(value = REGISTRATION, method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute(CLIENT, new ClientDto());
        return REGISTRATION;
    }

    @RequestMapping(value = REGISTRATION, method = RequestMethod.POST)
    public String createClient(@Valid ClientDto clientDto, BindingResult bindingResult, Model model) {
        if (!Objects.isNull(clientService.findAllByEmail(clientDto.getEmail()))) {
            bindingResult.rejectValue(EMAIL, ERROR_CLIENT, THERE_IS_ALREADY_A_CLIENT_REGISTERED_WITH_THE_EMAIL_PROVIDED);
            model.addAttribute(CLIENT, clientDto);
        }
        if (!bindingResult.hasErrors()) {
            clientService.save(clientDto);
            model.addAttribute(SUCCESS_MESSAGE, CLIENT_HAS_BEEN_REGISTERED_SUCCESSFULLY);
            model.addAttribute(CLIENT, new ClientDto());
        }
        return REGISTRATION;
    }
}
