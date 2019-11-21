package com.logdb.controller;

import com.logdb.dto.NamesystemDto;
import com.logdb.dto.ClientDto;
import com.logdb.entity.Event;
import com.logdb.service.EventLogService;
import com.logdb.service.NameSystemService;
import com.logdb.service.ClientService;
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
public class NameSystemController {

    protected static final String NAMESYSTEM_FORM = "/namesystemlogForm";
    protected static final String ERROR = "/error";
    protected static final String CREATE_NAMESYSTEM = "/create/namesystemlog";
    protected static final String NAMESYSTEM = "object";
    protected static final String REDIRECT_NAMESYSTEMS = "redirect:/namesystemlog/";
    protected static final String EDIT_NAMESYSTEM_ID = "/edit/namesystemlog/{id}";
    protected static final String NAMESYSTEM_ID = "/namesystemlog/{id}";
    protected static final String REDIRECT_HOME = "redirect:/home";
    protected static final String NAMESYSTEM_PAGE = "/namesystemlogInfo";

    @Autowired
    protected NameSystemService namesystemService;

    @Autowired
    protected ClientService clientService;

    @Autowired
    EventLogService eventLogService;


    @RequestMapping(value = CREATE_NAMESYSTEM, method = RequestMethod.GET)
    public String createNamesystemGet(Principal principal, Model model) {
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            NamesystemDto namesystemDto = new NamesystemDto();
            model.addAttribute(NAMESYSTEM, namesystemDto);
            return NAMESYSTEM_FORM;
        }
        return ERROR;
    }

    @RequestMapping(value = CREATE_NAMESYSTEM, method = RequestMethod.POST)
    @Transactional
    public String createNamesystemPost(Principal principal, @Valid NamesystemDto namesystemDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return NAMESYSTEM_FORM;
        }
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            namesystemDto = namesystemService.insert(namesystemDto);
            eventLogService.save(new Event("Created/updated namesystemlog .ID:" + namesystemDto.getId(), clientDto.getId()));
            return REDIRECT_NAMESYSTEMS + namesystemDto.getId();
        }
        return ERROR;
    }

    @RequestMapping(value = EDIT_NAMESYSTEM_ID, method = RequestMethod.GET)
    public String editNamesystembyIdGet(@PathVariable Long id, Principal principal, Model model) {
        NamesystemDto namesystemDto = namesystemService.findById(id);
        if (!Objects.isNull(namesystemDto)) {
            model.addAttribute(NAMESYSTEM, namesystemDto);
            return NAMESYSTEM_FORM;
        }
        return ERROR;
    }

    @RequestMapping(value = NAMESYSTEM_ID, method = RequestMethod.GET)
    public String getNamesystemById(@PathVariable Long id, Principal principal, Model model) {
        NamesystemDto namesystemDto = namesystemService.findById(id);
        if (!Objects.isNull(namesystemDto)) {
            model.addAttribute(NAMESYSTEM, namesystemDto);
            return NAMESYSTEM_PAGE;
        }
        return ERROR;
    }

    @RequestMapping(value = NAMESYSTEM_ID, method = RequestMethod.DELETE)
    @Transactional
    public String deleteNamesystemById(@PathVariable Long id, Principal principal) {
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            NamesystemDto namesystemDto = namesystemService.findById(id);
            if (!Objects.isNull(namesystemDto)) {
                namesystemService.delete(namesystemDto);
                eventLogService.save(new Event("Deleted namesystemlog .ID:" + namesystemDto.getId(), clientDto.getId()));
                return REDIRECT_HOME;
            }
        }
        return ERROR;
    }
}
