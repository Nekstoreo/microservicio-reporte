package com.onclass.reporte.infrastructure.constants;

public final class ApiConstants {

    public static final String REPORT_NOT_FOUND_MESSAGE = "Report not found for bootcamp: ";
    public static final String INVALID_REQUEST_MESSAGE = "Invalid request";
    public static final String REPORT_ERROR_CODE = "REPORT_ERROR";
    public static final String REPORT_NOT_FOUND_CODE = "REPORT_NOT_FOUND";

    public static final String REPORTS_BOOTCAMPS_PATH = "/reports/bootcamps";
    public static final String REPORTS_BOOTCAMPS_ENROLLMENT_PATH = "/{bootcampId}/enrollment-count";

    public static final String API_TITLE = "Microservicio Reporte";
    public static final String API_VERSION = "0.0.1";
    public static final String API_DESCRIPTION = "API para gestión de reportes del sistema";

    public static final String MONGODB_COLLECTION_NAME = "bootcamp_reports";

    public static final String OPENAPI_TAG_BOOTCAMP_REPORTS = "Bootcamp Reports";
    public static final String OPENAPI_TAG_DESCRIPTION = "API para gestionar reportes de bootcamps con datos enriquecidos";

    public static final String OPENAPI_REGISTER_REPORT_SUMMARY = "Registrar reporte de bootcamp";
    public static final String OPENAPI_REGISTER_REPORT_DESCRIPTION = "Registra un nuevo reporte de bootcamp con datos enriquecidos de capacidades, tecnologías y matriculaciones";
    public static final String OPENAPI_REGISTER_REPORT_201 = "Reporte registrado exitosamente";
    public static final String OPENAPI_REGISTER_REPORT_400 = "Solicitud inválida - datos faltantes o mal formados";
    public static final String OPENAPI_REGISTER_REPORT_401 = "No autenticado - se requiere autenticación básica";

    public static final String OPENAPI_UPDATE_ENROLLMENT_SUMMARY = "Actualizar contador de matriculaciones";
    public static final String OPENAPI_UPDATE_ENROLLMENT_DESCRIPTION = "Actualiza el contador de matriculaciones activas para un bootcamp específico";
    public static final String OPENAPI_UPDATE_ENROLLMENT_204 = "Contador actualizado exitosamente";
    public static final String OPENAPI_UPDATE_ENROLLMENT_400 = "Solicitud inválida - parámetros mal formados";
    public static final String OPENAPI_UPDATE_ENROLLMENT_401 = "No autenticado - se requiere autenticación básica";
    public static final String OPENAPI_UPDATE_ENROLLMENT_404 = "Reporte no encontrado para el bootcamp especificado";

    public static final String OPENAPI_PARAM_BOOTCAMP_ID_DESCRIPTION = "ID único del bootcamp";
    public static final String OPENAPI_PARAM_BOOTCAMP_ID_EXAMPLE = "1";
    public static final String OPENAPI_PARAM_COUNT_DESCRIPTION = "Nuevo contador de matriculaciones";
    public static final String OPENAPI_PARAM_COUNT_EXAMPLE = "15";

    private ApiConstants() {
    }
}

