package com.logdb.controller;


import java.util.Objects;

import com.logdb.dto.AccessDto;
import com.logdb.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.logdb.dto.ClientDto;
import com.logdb.service.ClientService;
import com.logdb.utils.Pager;

@Controller
public class HomeController {

    protected static final String URL_EXTENTION = "urlExtention";
    protected static final String SORT_BY = "?sortBy=";
    protected static final String DIRECTION = "&direction=";
    protected static final String PAGER = "pager";
    protected static final String CLIENT = "client";
    protected static final String ERROR = "/error";
    protected static final String ACCESSES = "/accesses";
    protected static final String HOME = "/home";
    protected static final String ROOT = "/";
    protected static final String ACCESSES_CLIENT_ID = "/accesses/{clientId}";
    protected static final String ASC = "asc";
    protected static final String CREATED_AT = "createdAt";
    protected static final String DEFAULT_PAGE = "0";
    protected static final String MYACCESSES_EMAIL = "/myaccesses/{email}";
    protected static final String REDIRECT_ACCESSES = "redirect:/accesses/";

    @Autowired
    protected AccessService accessService;

    @Autowired
    protected ClientService clientService;


    @GetMapping({ROOT, HOME})
    public String home(@RequestParam(defaultValue = DEFAULT_PAGE) int page,@RequestParam(defaultValue = CREATED_AT) String sortBy,  @RequestParam(defaultValue = ASC) String direction,Model model) {
        Page<AccessDto> accessDtos = accessService.findAll(page);
        Pager pager = new Pager<>(accessDtos);
        model.addAttribute(PAGER, pager);
        model.addAttribute(URL_EXTENTION, SORT_BY + sortBy + DIRECTION + direction);
        return HOME;
    }

    @GetMapping(value = ACCESSES_CLIENT_ID)
    public String accessesForId(@PathVariable long clientId, @RequestParam(defaultValue = DEFAULT_PAGE) int page, @RequestParam(defaultValue = CREATED_AT) String sortBy,  @RequestParam(defaultValue = ASC) String direction , Model model) {
        Page<AccessDto> accessDtos = accessService.findAll(page);
        ClientDto clientDto = clientService.findAllById(clientId);
        if (!Objects.isNull(clientDto)) {
            Pager pager = new Pager<>(accessDtos);
            model.addAttribute(PAGER, pager);
            model.addAttribute(CLIENT, clientDto);
            model.addAttribute(URL_EXTENTION, SORT_BY + sortBy + DIRECTION + direction);
            return ACCESSES;
        }
        return ERROR;
    }

    @GetMapping(value = MYACCESSES_EMAIL)
    public String accessesForEmail(@PathVariable String email) {
        ClientDto clientDto = clientService.findAllByEmail(email);
        if (!Objects.isNull(clientDto)) {
            return REDIRECT_ACCESSES + clientDto.getId();
        }
        return ERROR;
    }

}
