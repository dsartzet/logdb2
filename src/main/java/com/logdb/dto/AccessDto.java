package com.logdb.dto;

import java.sql.Timestamp;

public class AccessDto
{
    private Long id;
    private SessionDto sessionDto;
    private RequestDto requestDto;
    private ResponseDto responseDto;
    private Timestamp timestamp;
    private String referer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SessionDto getSessionDto() {
        return sessionDto;
    }

    public void setSessionDto(SessionDto sessionDto) {
        this.sessionDto = sessionDto;
    }

    public RequestDto getRequestDto() {
        return requestDto;
    }

    public void setRequestDto(RequestDto requestDto) {
        this.requestDto = requestDto;
    }

    public ResponseDto getResponseDto() {
        return responseDto;
    }

    public void setResponseDto(ResponseDto responseDto) {
        this.responseDto = responseDto;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }
}
