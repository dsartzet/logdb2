package com.logdb.controller;

import com.logdb.dto.ClientDto;
import com.logdb.dto.DataxceiverDto;
import com.logdb.entity.Event;
import com.logdb.service.ClientService;
import com.logdb.service.DataxreceiverService;
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
public class DataxreceiverController {

    protected static final String DATAXCEIVER_FORM = "/dataxreceiverlogForm";
    protected static final String ERROR = "/error";
    protected static final String CREATE_DATAXCEIVER = "/create/dataxreceiverlog";
    protected static final String DATAXCEIVER = "object";
    protected static final String REDIRECT_DATAXCEIVERS = "redirect:/dataxreceiverlog/";
    protected static final String EDIT_DATAXCEIVER_ID = "/edit/dataxreceiverlog/{id}";
    protected static final String DATAXCEIVER_ID = "/dataxreceiverlog/{id}";
    protected static final String REDIRECT_HOME = "redirect:/home";
    protected static final String DATAXCEIVER_PAGE = "/dataxreceiverlogInfo";

    @Autowired
    protected DataxreceiverService dataxceiverService;

    @Autowired
    protected ClientService clientService;

    @Autowired
    EventLogService eventLogService;



    @RequestMapping(value = CREATE_DATAXCEIVER, method = RequestMethod.GET)
    public String createDataxceiverGet(Principal principal, Model model) {
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            DataxceiverDto dataxceiverDto = new DataxceiverDto();
            model.addAttribute(DATAXCEIVER, dataxceiverDto);
            return DATAXCEIVER_FORM;
        }
        return ERROR;
    }

    @RequestMapping(value = CREATE_DATAXCEIVER, method = RequestMethod.POST)
    @Transactional
    public String createDataxceiverPost(Principal principal, @Valid DataxceiverDto dataxceiverDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return DATAXCEIVER_FORM;
        }
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            dataxceiverDto = dataxceiverService.insert(dataxceiverDto);
            eventLogService.save(new Event("Created/updated dataxreceiverlog .ID:" + dataxceiverDto.getId(), clientDto.getId()));
            return REDIRECT_DATAXCEIVERS + dataxceiverDto.getId();
        }
        return ERROR;
    }

    @RequestMapping(value = EDIT_DATAXCEIVER_ID, method = RequestMethod.GET)
    public String editDataxceiverbyIdGet(@PathVariable Long id, Principal principal, Model model) {
        DataxceiverDto dataxceiverDto = dataxceiverService.findById(id);
        if (!Objects.isNull(dataxceiverDto)) {
            model.addAttribute(DATAXCEIVER, dataxceiverDto);
            return DATAXCEIVER_FORM;
        }
        return ERROR;
    }

    @RequestMapping(value = DATAXCEIVER_ID, method = RequestMethod.GET)
    public String getDataxceiverById(@PathVariable Long id, Principal principal, Model model) {
        DataxceiverDto dataxceiverDto = dataxceiverService.findById(id);
        if (!Objects.isNull(dataxceiverDto)) {
            model.addAttribute(DATAXCEIVER, dataxceiverDto);
            return DATAXCEIVER_PAGE;
        }
        return ERROR;
    }

    @RequestMapping(value = DATAXCEIVER_ID, method = RequestMethod.DELETE)
    @Transactional
    public String deleteDataxceiverById(@PathVariable Long id, Principal principal) {
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            DataxceiverDto dataxceiverDto = dataxceiverService.findById(id);
            if (!Objects.isNull(dataxceiverDto)) {
                dataxceiverService.delete(dataxceiverDto);
                eventLogService.save(new Event("Deleted dataxreceiverlog .ID:" + dataxceiverDto.getId(), clientDto.getId()));
                return REDIRECT_HOME;
            }
        }
        return ERROR;
    }
}
