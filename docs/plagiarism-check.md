```mermaid
sequenceDiagram
participant C as Клиент
participant G as Gateway
participant A as Analysis Service
participant DB as PostgreSQL

    C->>G: GET /reports/works/1/students/1
    G->>A: GET /api/reports/works/1/students/1
    A->>DB: Запрос отчета
    DB-->>A: Данные отчета
    A-->>G: ReportDto
    G-->>C: Отчет с результатом проверки