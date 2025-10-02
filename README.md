# Java — Order Service (TP05)

Projeto Java (Maven) para a Tarefa Prática 05, com JaCoCo e integração com SonarQube.

## Requisitos
- Java 17+
- Maven 3.9+
- SonarQube Community (opcional para análise estática via scanner Maven)

## Cobertura (JaCoCo)

mvn clean verify
# Abra o relatório HTML: target/site/jacoco/index.html


## Análise estática (SonarQube + Maven)
Crie um token no SonarQube (My Account → Security) e rode:

mvn sonar:sonar   -Dsonar.projectKey=tp05-java   -Dsonar.host.url=http://localhost:9000   -Dsonar.login=SEU_TOKEN


