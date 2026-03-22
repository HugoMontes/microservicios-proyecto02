## COMANDOS PARA EJECUTAR EL AGENTE DE OPENTELEMETRY

- Command agent for "api-gateway"
```shell
-javaagent:D:\otel\opentelemetry-javaagent.jar -Dotel.service.name=api-gateway -Dotel.traces.exporter=zipkin -Dotel.exporter.zipkin.endpoint=http://localhost:9411/api/v2/spans -Dotel.metrics.exporter=none -Dotel.logs.exporter=none
```
- Command agent for "inventario-service"
```shell
-javaagent:D:\otel\opentelemetry-javaagent.jar -Dotel.service.name=inventario-service -Dotel.traces.exporter=zipkin -Dotel.exporter.zipkin.endpoint=http://localhost:9411/api/v2/spans -Dotel.metrics.exporter=none -Dotel.logs.exporter=none
```
- Command agent for "order-service"
```shell
-javaagent:D:\otel\opentelemetry-javaagent.jar -Dotel.service.name=order-service -Dotel.traces.exporter=zipkin -Dotel.exporter.zipkin.endpoint=http://localhost:9411/api/v2/spans -Dotel.metrics.exporter=none -Dotel.logs.exporter=none
```
- Command agent for "producto-service"
```shell
-javaagent:D:\otel\opentelemetry-javaagent.jar -Dotel.service.name=producto-service -Dotel.traces.exporter=zipkin -Dotel.exporter.zipkin.endpoint=http://localhost:9411/api/v2/spans -Dotel.metrics.exporter=none -Dotel.logs.exporter=none
```
- Command agent for "notification-service"
```shell
-javaagent:D:\otel\opentelemetry-javaagent.jar -Dotel.service.name=notification-service -Dotel.traces.exporter=zipkin -Dotel.exporter.zipkin.endpoint=http://localhost:9411/api/v2/spans -Dotel.metrics.exporter=none -Dotel.logs.exporter=none
```

