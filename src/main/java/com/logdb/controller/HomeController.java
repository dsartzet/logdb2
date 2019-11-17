package com.logdb.controller;


import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import com.logdb.service.ClientService;
import com.logdb.utils.Pager;

@Controller
public class HomeController {

    protected static final String PAGER = "pager";
    protected static final String HOME = "/home";
    protected static final String ROOT = "/";
    protected static final String DEFAULT_PAGE = "0";

    @Autowired
    protected AccessService accessService;

    @Autowired
    protected NameSystemService nameSystemService;

    @Autowired
    protected DataxreceiverService dataxreceiverService;


    @Autowired
    protected ClientService clientService;


    @GetMapping({ROOT, HOME})
    public String home(@RequestParam(defaultValue = DEFAULT_PAGE) int page,Model model) {
        List<AccessDto> accessDtos = accessService.findAll();
        List<DataxceiverDto> dataxceiverDtos = dataxreceiverService.findAll();
        List<NamesystemDto> namesystemDtos =nameSystemService.findAll();
        List<LogDto> logList = new ArrayList<>();
        logList.addAll(accessDtos);
        logList.addAll(dataxceiverDtos);
        logList.addAll(namesystemDtos);
        Page<LogDto> logDtos = new PageImpl<>(logList, PageRequest.of(Pager.subtractPageByOne(page), 5), logList.size());
        Pager pager = new Pager<>(logDtos);
        model.addAttribute(PAGER, pager);
        return HOME;
    }

}
