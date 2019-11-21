package com.logdb.controller;

import com.logdb.dto.AccessDto;
import com.logdb.dto.ClientDto;
import com.logdb.entity.Event;
import com.logdb.service.AccessService;
import com.logdb.service.ClientService;
import com.logdb.service.EventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    protected static final String ACCESS_FORM = "/accesslogForm";
    protected static final String ERROR = "/error";
    protected static final String CREATE_ACCESS = "/create/accesslog";
    protected static final String ACCESS = "object";
    protected static final String REDIRECT_ACCESSS = "redirect:/accesslog/";
    protected static final String EDIT_ACCESS_ID = "/edit/accesslog/{id}";
    protected static final String ACCESS_ID = "/accesslog/{id}";
    protected static final String REDIRECT_HOME = "redirect:/home";
    protected static final String ACCESS_PAGE = "/accesslogInfo";

    @Autowired
    protected AccessService accessService;

    @Autowired
    protected ClientService clientService;

    @Autowired
    EventLogService eventLogService;

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
    @Transactional
    public String createAccessPost(Principal principal, @Valid AccessDto accessDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(ACCESS, accessDto);
            return ACCESS_FORM;
        }
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            accessDto = accessService.insert(accessDto);
            eventLogService.save(new Event("Created/updated accelog.ID:" + accessDto.getId(), clientDto.getId()));
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
    @Transactional
    public String deleteAccessById(@PathVariable Long id, Principal principal) {
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            AccessDto accessDto = accessService.findById(id);
            if (!Objects.isNull(accessDto)) {
                accessService.delete(accessDto);
                eventLogService.save(new Event("Deleted accelog.ID:" + accessDto.getId(), clientDto.getId()));
                return REDIRECT_HOME;
            }
        }
        return ERROR;
    }
}
