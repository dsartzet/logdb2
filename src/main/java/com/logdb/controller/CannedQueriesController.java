package com.logdb.controller;

import com.logdb.dto.ClientDto;
import com.logdb.service.ClientService;
import org.hibernate.Session;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.ResultSetOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class CannedQueriesController {

    @PersistenceContext
    private EntityManager em;

    protected static final String CANNED_QUERIES = "/canned-queries";
    protected static final String FIRST_QUERY = "/canned-queries/query_1";
    protected static final String SECOND_QUERY = "/canned-queries/query_2";
    protected static final String THIRD_QUERY = "/canned-queries/query_3";
    protected static final String QUERY_RESULT = "/queryResult";
    protected static final String QUERIES_FORM = "/cannedQueriesForm";
    protected static final String ERROR = "/error";
    protected static final String HEADERS = "headers";
    protected static final String ROWS = "rows";
    protected static final String SIZE = "size";

    @Autowired
    protected ClientService clientService;

    @RequestMapping(value = CANNED_QUERIES, method = RequestMethod.GET)
    public String cannedQueriesGet(Principal principal, Model model) {
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            return QUERIES_FORM;
        }
        return ERROR;
    }

    @RequestMapping(value = FIRST_QUERY, method = RequestMethod.GET)
    public String firstQueryGet(@RequestParam(value = "start") String start,
                                @RequestParam(value = "end") String end,
                                Principal principal,
                                Model model) {
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            if (StringUtils.countOccurrencesOf(start, ":") < 2) { start = start.concat(":00"); }
            if (StringUtils.countOccurrencesOf(end, ":") < 2) { end = end.concat(":00"); }
            Session session = em.unwrap(Session.class);
            ProcedureCall procedureCall = session.createStoredProcedureCall("query_1");
            procedureCall.registerParameter("START", Timestamp.class, ParameterMode.IN)
                    .bindValue(Timestamp.valueOf(start.replace("T"," ")));
            procedureCall.registerParameter("END", Timestamp.class, ParameterMode.IN)
                    .bindValue(Timestamp.valueOf(end.replace("T"," ")));
            submitQuery(model, procedureCall,"Type", "LogCount");
            session.close();
            return QUERY_RESULT;
        }
        return ERROR;
    }

    @RequestMapping(value = SECOND_QUERY, method = RequestMethod.GET)
    public String secondQueryGet(@RequestParam(value = "start") String start,
                                 @RequestParam(value = "end") String end,
                                 @RequestParam(value = "type") String type,
                                 Principal principal,
                                 Model model) {
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            if (StringUtils.countOccurrencesOf(start, ":") < 2) { start = start.concat(":00"); }
            if (StringUtils.countOccurrencesOf(end, ":") < 2) { end = end.concat(":00"); }
            Session session = em.unwrap(Session.class);
            ProcedureCall procedureCall = session.createStoredProcedureCall("query_2");
            procedureCall.registerParameter("START", Timestamp.class, ParameterMode.IN)
                    .bindValue(Timestamp.valueOf(start.replace("T"," ")));
            procedureCall.registerParameter("END", Timestamp.class, ParameterMode.IN)
                    .bindValue(Timestamp.valueOf(end.replace("T"," ")));
            procedureCall.registerParameter("TYPE", String.class, ParameterMode.IN).bindValue(type);
            submitQuery(model, procedureCall,"Date", "LogCount");
            session.close();
            return QUERY_RESULT;
        }
        return ERROR;
    }

    @RequestMapping(value = THIRD_QUERY, method = RequestMethod.GET)
    public String thirdQueryGet(@RequestParam(value = "date") String date,
                                Principal principal, Model model) {
        ClientDto clientDto = clientService.findAllByEmail(principal.getName());
        if (!Objects.isNull(clientDto)) {
            Session session = em.unwrap(Session.class);
            ProcedureCall procedureCall = session.createStoredProcedureCall("query_3");
            procedureCall.registerParameter("DATE", Date.class, ParameterMode.IN)
                    .bindValue(Date.valueOf(date));
            submitQuery(model, procedureCall,"Source IP", "LogCount");
            session.close();
            return QUERY_RESULT;
        }
        return ERROR;
    }

    private static void submitQuery(Model model, ProcedureCall procedureCall, String... headersv) {
        ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
        ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
        List results = resultSetOutput.getResultList();

        List<String> headers = Arrays.asList(headersv);
        List<List<String>> rows = new ArrayList<>();

        for (Object result : results) {
            Object[] objects = (Object[]) result;
            rows.add(Arrays.asList(objects[0].toString(), objects[1].toString()));
        }
        model.addAttribute(HEADERS, headers);
        model.addAttribute(ROWS, rows);
        model.addAttribute(SIZE, rows.size());
    }
}
