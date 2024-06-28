package com.patientassistant.home.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SuccessResponseHandler {
    private String message;
    private Object body;
    private boolean ok;
    private int status;
}