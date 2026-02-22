package com.onclass.reporte.domain.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BootcampReportExceptionTest {

    @Test
    void constructor_withMessage_shouldUseDefaultCode() {
        BootcampReportException ex = new BootcampReportException("Something went wrong");

        assertEquals("Something went wrong", ex.getMessage());
        assertEquals(BootcampReportExceptionCodes.REPORT_ERROR, ex.getCode());
    }

    @Test
    void constructor_withCodeAndMessage_shouldSetBoth() {
        BootcampReportException ex = new BootcampReportException(
                BootcampReportExceptionCodes.REPORT_NOT_FOUND,
                "Report not found for bootcamp: 1"
        );

        assertEquals("Report not found for bootcamp: 1", ex.getMessage());
        assertEquals(BootcampReportExceptionCodes.REPORT_NOT_FOUND, ex.getCode());
    }
}
