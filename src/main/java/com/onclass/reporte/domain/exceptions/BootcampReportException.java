package com.onclass.reporte.domain.exceptions;

public class BootcampReportException extends RuntimeException {

    private final String code;

    public BootcampReportException(String message) {
        super(message);
        this.code = BootcampReportExceptionCodes.REPORT_ERROR;
    }

    public BootcampReportException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
