package com.logdb.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.logdb.dto.*;
import com.logdb.service.AccessService;
import com.logdb.service.DataxreceiverService;
import com.logdb.service.NameSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.logdb.service.ClientService;
import com.logdb.utils.Pager;

@Controller
public class HomeController {

    protected static final String PAGER = "pager";
    protected static final String HOME = "/home";
    protected static final String ROOT = "/";
    protected static final String DEFAULT_PAGE = "0";
    protected static final String REDIRECT_HOME = "redirect:/home";
    public static final String SEARCH = "search";


    @Autowired
    protected AccessService accessService;

    @Autowired
    protected NameSystemService nameSystemService;

    @Autowired
    protected DataxreceiverService dataxreceiverService;


    @Autowired
    protected ClientService clientService;
    private String search = "";


    @GetMapping({ROOT, HOME})
    public String home(@RequestParam(defaultValue = DEFAULT_PAGE) int page, Model model) {
        Page<AccessDto> accessDtos;
        Page<DataxceiverDto> dataxceiverDtos;
        Page<NamesystemDto> namesystemDtos;
        if(search.isEmpty()) {
            accessDtos = accessService.findAll(page);
            dataxceiverDtos = dataxreceiverService.findAll(page);
            namesystemDtos =nameSystemService.findAll(page);
        } else {
            accessDtos = accessService.findByIp(page, search);
            dataxceiverDtos = dataxreceiverService.findByIp(page, search);
            namesystemDtos =nameSystemService.findByIp(page, search);
        }
        List<LogDto> logList = new ArrayList<>();
        logList.addAll(accessDtos.stream().collect(Collectors.toList()));
        logList.addAll(dataxceiverDtos.stream().collect(Collectors.toList()));
        logList.addAll(namesystemDtos.stream().collect(Collectors.toList()));
        Page<LogDto> logDtos = new PageImpl<>(logList, PageRequest.of(Pager.subtractPageByOne(page), 3), accessDtos.getTotalElements() + dataxceiverDtos.getTotalElements() + namesystemDtos.getTotalElements());
        Pager pager = new Pager<>(logDtos);
        model.addAttribute(PAGER, pager);
        model.addAttribute(SEARCH, this.search);
        return HOME;
    }


    @PostMapping(HOME)
    public String search(@RequestParam(defaultValue = "")  String search) {
        this.search = search;
        return REDIRECT_HOME;
    }

}

