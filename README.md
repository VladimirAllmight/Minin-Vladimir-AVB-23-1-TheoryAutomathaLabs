# Minin-Vladimir-AVB-23-1-TheoryAutomathaLabs
---

## 📊 Мониторинг и визуализация с Grafana (ЛР №3)

![image](https://github.com/user-attachments/assets/84fcad76-27c9-4d82-94d9-645dd50cf708)

Для визуального анализа работы конечного автомата был развернут **Grafana Dashboard**, отображающий:

- Общее количество событий
- Частоту событий по времени
- Распределение состояний автомата
- Последние зарегистрированные события

### 🗃️ Хранилище данных: PostgreSQL

Все переходы между состояниями автомата логируются в таблицу `alarm_logs` следующей структуры:

```sql
CREATE TABLE alarm_logs (
    id SERIAL PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL DEFAULT NOW(),
    state TEXT NOT NULL,
    message TEXT
);
