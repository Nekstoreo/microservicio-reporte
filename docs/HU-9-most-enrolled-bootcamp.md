# HU-9: Mostrar el bootcamp con mayor cantidad de personas

## Descripción
Endpoint para obtener el bootcamp con la mayor cantidad de personas inscritas.

## Endpoint

```
GET /reports/bootcamps/most-enrolled
```

### Autenticación
Requiere autenticación básica HTTP.

### Respuesta Exitosa (200 OK)

```json
{
  "reportId": "uuid",
  "bootcampId": "1",
  "bootcampName": "Java Bootcamp",
  "bootcampDescription": "Learn Java programming",
  "releaseDate": "2024-03-01",
  "duration": 90,
  "capabilities": [
    {
      "id": "1",
      "name": "Backend Development",
      "description": "Backend skills",
      "technologies": [
        {
          "id": "1",
          "name": "Java",
          "description": "Java programming language"
        }
      ]
    }
  ],
  "technologies": [
    {
      "id": "1",
      "name": "Java",
      "description": "Java programming language"
    }
  ],
  "enrollments": [
    {
      "personId": 1,
      "name": "John Doe",
      "email": "john@example.com",
      "enrollmentDate": "2024-03-15T10:30:00"
    },
    {
      "personId": 2,
      "name": "Jane Smith",
      "email": "jane@example.com",
      "enrollmentDate": "2024-03-16T14:20:00"
    }
  ],
  "capabilityCount": 1,
  "technologyCount": 1,
  "enrollmentCount": 2,
  "createdAt": "2024-03-01T09:00:00",
  "updatedAt": "2024-03-16T14:20:00"
}
```

### Respuesta Sin Datos (404 Not Found)
Cuando no hay bootcamps registrados en el sistema.

## Criterios de Aceptación Cumplidos

- ✅ Retorna toda la información del bootcamp
- ✅ Incluye nombre y correo de cada persona inscrita
- ✅ Incluye cada una de las capacidades asociadas
- ✅ Incluye cada una de las tecnologías asociadas

## Implementación

### Capa de Dominio
- `GetMostEnrolledBootcampUseCase`: Interfaz del caso de uso
- `BootcampReportPersistencePort.findBootcampWithMostEnrollments()`: Método en el puerto de persistencia

### Capa de Aplicación
- `GetMostEnrolledBootcampService`: Implementación del caso de uso

### Capa de Infraestructura
- `BootcampReportMongoAdapter.findBootcampWithMostEnrollments()`: Consulta a MongoDB
- `BootcampReportController.getMostEnrolledBootcamp()`: Endpoint REST
- `SecurityConfig`: Regla de seguridad para el endpoint

### Tests
- `GetMostEnrolledBootcampServiceTest`: Tests del servicio
- `BootcampReportMongoAdapterMostEnrolledTest`: Tests del adaptador
- `BootcampReportControllerMostEnrolledTest`: Tests del controlador

## Ejemplo de Uso

```bash
curl -X GET http://localhost:8085/reports/bootcamps/most-enrolled \
  -u admin:admin123 \
  -H "Content-Type: application/json"
```

## Notas Técnicas

- La búsqueda se realiza en memoria comparando el tamaño de la lista de enrollments
- Si hay empate, se retorna el primero encontrado
- El endpoint requiere autenticación para proteger información sensible
- Usa programación reactiva con Reactor (Mono)
