# Пользовательские сценарии

## 1. Загрузка студенческой работы

```mermaid
sequenceDiagram
    participant C as Клиент
    participant G as Gateway (8080)
    participant S as Storage Service (9090)
    participant A as Analysis Service (9091)
    participant DB as PostgreSQL
    participant S3 as MinIO

    C->>G: POST /files/works/1/students/1/upload
    Note over C,G: Multipart файл
    
    G->>S: POST /api/files/works/1/students/1/upload
    S->>DB: Сохранить метаданные файла
    S->>S3: Загрузить файл в bucket
    S-->>G: FileDto с fileId
    
    G->>A: POST /analyze (асинхронно)
    Note over G,A: Анализ в фоновом режиме
    
    A->>S: GET /files/{fileId}/earlier
    A->>S: GET /files/{fileId}
    A->>DB: Сохранить отчет анализа