package com.logdb.controller;

import com.logdb.dto.AccessDto;
import com.logdb.dto.ClientDto;
import com.logdb.service.AccessService;
import com.logdb.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@Controller
public class AccessLogController {

    protected static final String ACCESS_FORM = "/accessForm";
    protected static final String ERROR = "/error";
    protected static final String CREATE_ACCESS = "/create/access";
    protected static final String ACCESS = "access";
    protected static final String REDIRECT_ACCESSS = "redirect:/accesss/";
    protected static final String EDIT_ACCESS_ID = "/edit/access/{id}";
    protected static final String ACCESS_ID = "/access/{id}";
    protected static final String REDIRECT_HOME = "redirect:/home";
    protected static final String ACCESS_PAGE = "/access";

    @Autowired
    protected AccessService accessService;

    @Autowired
    protected ClientService clientService;


    @RequestMapping(value = CREATE_ACCESS, method = RequestMethod.GET)
    public String createAccessGet(Principal principal, Model model) {
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            AccessDto accessDto = new AccessDto();
            model.addAttribute(ACCESS, accessDto);
            return ACCESS_FORM;
        }
        return ERROR;
    }

    @RequestMapping(value = CREATE_ACCESS, method = RequestMethod.POST)
    public String createAccessPost(Principal principal, @Valid AccessDto accessDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ACCESS_FORM;
        }
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            accessService.insert(accessDto);
            return REDIRECT_ACCESSS + accessDto.getId();
        }
        return ERROR;
    }

    @RequestMapping(value = EDIT_ACCESS_ID, method = RequestMethod.GET)
    public String editAccessbyIdGet(@PathVariable Long id, Principal principal, Model model) {
        AccessDto accessDto = accessService.findById(id);
        if (!Objects.isNull(accessDto)) {
            model.addAttribute(ACCESS, accessDto);
            return ACCESS_FORM;
        }
        return ERROR;
    }

    @RequestMapping(value = ACCESS_ID, method = RequestMethod.GET)
    public String getAccessById(@PathVariable Long id, Principal principal, Model model) {
        AccessDto accessDto = accessService.findById(id);
        if (!Objects.isNull(accessDto)) {
            model.addAttribute(ACCESS, accessDto);
            return ACCESS_PAGE;
        }
        return ERROR;
    }

    @RequestMapping(value = ACCESS_ID, method = RequestMethod.DELETE)
    public String deleteAccessById(@PathVariable Long id, Principal principal) {
        AccessDto accessDto = accessService.findById(id);
        if (!Objects.isNull(accessDto)) {
                accessService.delete(accessDto);
                return REDIRECT_HOME;
        }
        return ERROR;
    }
}
