package com.onclass.reporte.domain.exception;

import com.onclass.reporte.infrastructure.constants.ApiConstants;

public class BootcampReportException extends RuntimeException {

    private String code;

    public BootcampReportException(String message) {
        super(message);
        this.code = ApiConstants.REPORT_ERROR_CODE;
    }

    public BootcampReportException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
