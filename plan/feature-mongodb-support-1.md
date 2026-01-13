---
goal: Implementar soporte para MongoDB como opción alternativa de almacenamento
version: 1.0
date_created: 2026-01-13
last_updated: 2026-01-13
owner: Equipo de Desenvolvemento
status: 'Planned'
tags: ['feature', 'backend', 'mongodb', 'arquitectura']
---

# Introdución

![Status: Planned](https://img.shields.io/badge/status-Planned-blue)

Plan de implementación para integrar MongoDB como opción alternativa de almacenamento na aplicación Android `PMDM-ProgramaSencilloDDBB_Room`. Actualmente a app utiliza Room (SQLite) como capa de persistencia. Este plan establece as tarefas, dependencias e estratexia para implementar MongoDB manténdose a compatibilidade con Room.

## 1. Requisitos e Restricións

### Requisitos Funcionales
- **REQ-001**: A aplicación debe conectarse a unha instancia de MongoDB usando variables de entorno ou arquivo de configuración
- **REQ-002**: Implementar operacións CRUD (crear, ler, actualizar, borrar) para a entidade `User` en MongoDB
- **REQ-003**: Manter a compatibilidade e funcionalidade actual con Room (SQLite)
- **REQ-004**: Permitir cambiar entre Room e MongoDB mediante configuración (flag ou DI)
- **REQ-005**: Validar todas as operacións críticas con tests unitarios e de integración
- **REQ-006**: Documentar a configuración de MongoDB en README.md

### Requisitos de Seguridade
- **SEC-001**: Non hardcodear credenciais de MongoDB. Usar variables de entorno ou arquivo de configuración seguro
- **SEC-002**: Soportar connection strings con autenticación e TLS

### Requisitos Técnicos
- **TEC-001**: Usar KMongo ou MongoDB Java Driver oficial para a integración
- **TEC-002**: Implementar patrón de repositorio para abstraer a capa de persistencia
- **TEC-003**: Usar Kotlin Coroutines para operacións asincrónicas
- **TEC-004**: Manter compatibilidade con versión de Kotlin e Java actual (Java 11, Kotlin)

### Restricións
- **CON-001**: Non eliminar nin romper a funcionalidade actual de Room
- **CON-002**: Manterse dentro do scope de cambios minimizados para evitar regressions
- **CON-003**: Non aumentar significativamente o tamaño do APK (analizar dependencias)

### Patróns a Seguir
- **PAT-001**: Patrón Repositorio para abstraer a persistencia
- **PAT-002**: Inyección de Dependencias (Dagger/Hilt) para cambiar a implementación
- **PAT-003**: Naming convention: `[Entity]Repository`, `[Entity]RepositoryRoom`, `[Entity]RepositoryMongo`

---

## 2. Pasos de Implementación

### Fase de Implementación 1: Investigación e Configuración de Dependencias

- **GOAL-001**: Avaliar librerías de MongoDB e establecer a stack técnica, engadindo as dependencias necesarias ao proxecto

| Tarefa | Descrición | Completada | Data |
|--------|-----------|-----------|------|
| TASK-001 | Avaliar librerías: KMongo vs MongoDB Java Driver. Decidir cal usar en base a compatibilidade con Kotlin Coroutines | | |
| TASK-002 | Engadir dependencias de MongoDB a `app/build.gradle.kts` (recomendado: `org.mongodb:mongodb-driver-sync:4.11.0` ou KMongo) | | |
| TASK-003 | Engadir dependencia de Testcontainers para tests de integración: `testImplementation("org.testcontainers:mongodb:1.19.3")` | | |
| TASK-004 | Crear arquivo `local.properties` ou `gradle.properties` con variábeis de configuración: `MONGODB_URI`, `STORAGE_BACKEND` | | |
| TASK-005 | Verificar que as dependencias engadidas non aumentan significativamente o tamaño do APK (executar `./gradlew assembleDebug`) | | |

### Fase de Implementación 2: Abstración da Capa de Persistencia con Patrón Repositorio

- **GOAL-002**: Crear arquitectura de repositorios que permita cambiar entre Room e MongoDB sen cambios na lóxica de negocio

| Tarefa | Descrición | Completada | Data |
|--------|-----------|-----------|------|
| TASK-006 | Crear interface `UserRepository` en `app/src/main/java/jc/dam/primeraapproom/data/repository/UserRepository.kt` con métodos: `getAll()`, `findById(id: Int)`, `findByName(first: String, last: String)`, `insert(user: User)`, `update(user: User)`, `delete(user: User)` | | |
| TASK-007 | Crear clase `UserRepositoryRoom` que implemente `UserRepository` usando o `UserDao` existente en `app/src/main/java/jc/dam/primeraapproom/data/repository/UserRepositoryRoom.kt` | | |
| TASK-008 | Crear clase `UserRepositoryMongo` que implemente `UserRepository` usando MongoDB en `app/src/main/java/jc/dam/primeraapproom/data/repository/UserRepositoryMongo.kt` | | |
| TASK-009 | Crear factory `RepositoryFactory` en `app/src/main/java/jc/dam/primeraapproom/data/RepositoryFactory.kt` que devolva a implementación correcta baseada en variable `STORAGE_BACKEND` | | |
| TASK-010 | Refactorizar `MainActivity.kt` e outras clases para usar `UserRepository` en lugar de `UserDao` directamente | | |

### Fase de Implementación 3: Implementación de MongoDB

- **GOAL-003**: Implementar a integración completa con MongoDB, incluíndo modelos, cliente e operacións CRUD

| Tarefa | Descrición | Completada | Data |
|--------|-----------|-----------|------|
| TASK-011 | Crear clase `MongoConfig` en `app/src/main/java/jc/dam/primeraapproom/data/mongo/MongoConfig.kt` que manexe a conexión a MongoDB baseada en `MONGODB_URI` | | |
| TASK-012 | Crear data class `UserDocument` en `app/src/main/java/jc/dam/primeraapproom/data/mongo/models/UserDocument.kt` que mapee a entidade `User` ao documento de MongoDB con `@BsonProperty` anotacións | | |
| TASK-013 | Crear mapper `UserMapper` en `app/src/main/java/jc/dam/primeraapproom/data/mongo/mappers/UserMapper.kt` para converter entre `User` e `UserDocument` | | |
| TASK-014 | Implementar `UserRepositoryMongo` coas operacións CRUD: usar `MongoCollection<UserDocument>` con operacións de `insertOne`, `find`, `updateOne`, `deleteOne` | | |
| TASK-015 | Implementar manexo de IDs: MongoDB usa `ObjectId` por defecto, mapear a `Int` da entidade `User` se é necesario ou restructurar o modelo | | |
| TASK-016 | Implementar logging e manexo de erros en todas as operacións de MongoDB | | |

### Fase de Implementación 4: Tests

- **GOAL-004**: Implementar tests unitarios e de integración para validar a funcionalidade de MongoDB

| Tarefa | Descrición | Completada | Data |
|--------|-----------|-----------|------|
| TASK-017 | Crear tests unitarios en `app/src/test/java/jc/dam/primeraapproom/data/mappers/UserMapperTest.kt` para validar conversións entre `User` e `UserDocument` | | |
| TASK-018 | Crear tests de integración en `app/src/androidTest/java/jc/dam/primeraapproom/data/mongo/UserRepositoryMongoTest.kt` usando Testcontainers con MongoDB en Docker | | |
| TASK-019 | Implementar test case: `testInsertAndRetrieveUser()` - inserir usuario e recuperalo | | |
| TASK-020 | Implementar test case: `testFindByName()` - buscar usuario por nome | | |
| TASK-021 | Implementar test case: `testUpdateUser()` - actualizar datos de usuario | | |
| TASK-022 | Implementar test case: `testDeleteUser()` - eliminar usuario | | |
| TASK-023 | Crear tests de switcher de backend en `app/src/test/java/jc/dam/primeraapproom/data/RepositoryFactoryTest.kt` para validar que ámbalas dúas implementacións funcionan | | |

### Fase de Implementación 5: Documentación e Exemplos

- **GOAL-005**: Documentar a nova funcionalidade e proporcionar guías de configuración

| Tarefa | Descrición | Completada | Data |
|--------|-----------|-----------|------|
| TASK-024 | Crear `docker-compose.yml` na raíz do proxecto para levantar MongoDB localmente (mongo:7.0, porto 27017, volumen persistente) | | |
| TASK-025 | Actualizar `README.md` coas seguintes seccións: "MongoDB Support", "Configuración", "Variables de Entorno", "Exemplo de Connection String" | | |
| TASK-026 | Crear arquivo `MONGODB_SETUP.md` con instrucións detalladas: como levantar MongoDB con docker-compose, como configurar a app, como cambiar entre Room e MongoDB | | |
| TASK-027 | Engadir exemplo de arquivo `.env.example` coas variables necesarias: `MONGODB_URI=mongodb://localhost:27017/primeraapp`, `STORAGE_BACKEND=mongodb` | | |

### Fase de Implementación 6: Revisión, QA e Optimización

- **GOAL-006**: Revisar, probar e optimizar a implementación antes de merge

| Tarefa | Descrición | Completada | Data |
|--------|-----------|-----------|------|
| TASK-028 | Executar `./gradlew lint` e `./gradlew detekt` para validar calidade do código | | |
| TASK-029 | Executar todos os tests: `./gradlew testDebug` e `./gradlew connectedAndroidTest` | | |
| TASK-030 | Probar manualmente na aplicación: inserir usuarios, cambiar de backend, verificar que Room e MongoDB producen resultados iguales | | |
| TASK-031 | Analizar tamaño do APK: `./gradlew assembleDebug --info` e comparar antes/despois de engadir MongoDB | | |
| TASK-032 | Revisar documentación en README.md e MONGODB_SETUP.md para claridade e corrección | | |
| TASK-033 | Preparar PR coa rama `feature/mongodb-support` con descrición completa | | |

---

## 3. Alternativas Consideradas

- **ALT-001**: Usar Firebase Firestore en lugar de MongoDB. Non foi escolido porque requeriría máis cambios arquitectónicos e menos flexibilidade para auto-hospedaxe
- **ALT-002**: Usar Realm en lugar de Room e MongoDB. Non foi escolido porque Room xa está implementado e Realm sería outro cambio de stack
- **ALT-003**: Usar abordaxe sincrona en lugar de Coroutines. Non foi escolido porque Coroutines son máis eficientes para operacións de rede/BDD en Android
- **ALT-004**: Implementar MongoDB directamente sen patrón repositorio. Non foi escolido porque violaría SOLID principles e dificultaría o mantemento

---

## 4. Dependencias

### Dependencias Técnicas
- **DEP-001**: KMongo `1.8.0` (se se elixe) ou MongoDB Java Driver `4.11.0` - para integración con MongoDB
- **DEP-002**: Testcontainers MongoDB `1.19.3` - para tests de integración
- **DEP-003**: Kotlin Coroutines (xa presente en libs.versions.toml) - para operacións asincrónicas
- **DEP-004**: Dagger/Hilt (analizar se xa está presente) - para inyección de dependencias

### Dependencias Organizacionais
- **DEP-005**: Docker debe estar instalado localmente para executar `docker-compose.yml`
- **DEP-006**: Acceso ao repositorio con rama `feature/mongodb-support`

### Dependencias de Fases
- Fase 2 depende de Fase 1 (as dependencias deben estar engadidas)
- Fase 3 depende de Fase 2 (a arquitectura de repositorios debe estar en lugar)
- Fase 4 depende de Fase 3 (o código a testar debe estar implementado)
- Fase 5 e 6 son paralelas a Fase 4

---

## 5. Ficheiros

### Ficheiros a Crear

- **FILE-001**: `app/src/main/java/jc/dam/primeraapproom/data/repository/UserRepository.kt` - Interface de repositorio
- **FILE-002**: `app/src/main/java/jc/dam/primeraapproom/data/repository/UserRepositoryRoom.kt` - Implementación con Room
- **FILE-003**: `app/src/main/java/jc/dam/primeraapproom/data/repository/UserRepositoryMongo.kt` - Implementación con MongoDB
- **FILE-004**: `app/src/main/java/jc/dam/primeraapproom/data/RepositoryFactory.kt` - Factory para seleccionar implementación
- **FILE-005**: `app/src/main/java/jc/dam/primeraapproom/data/mongo/MongoConfig.kt` - Configuración de MongoDB
- **FILE-006**: `app/src/main/java/jc/dam/primeraapproom/data/mongo/models/UserDocument.kt` - Modelo de documento MongoDB
- **FILE-007**: `app/src/main/java/jc/dam/primeraapproom/data/mongo/mappers/UserMapper.kt` - Mapper entre modelos
- **FILE-008**: `app/src/test/java/jc/dam/primeraapproom/data/mappers/UserMapperTest.kt` - Tests de mapper
- **FILE-009**: `app/src/androidTest/java/jc/dam/primeraapproom/data/mongo/UserRepositoryMongoTest.kt` - Tests de integración
- **FILE-010**: `app/src/test/java/jc/dam/primeraapproom/data/RepositoryFactoryTest.kt` - Tests de factory
- **FILE-011**: `docker-compose.yml` - Composición para MongoDB local
- **FILE-012**: `MONGODB_SETUP.md` - Guía de configuración de MongoDB
- **FILE-013**: `.env.example` - Exemplo de variables de entorno

### Ficheiros a Modificar

- **FILE-014**: `app/build.gradle.kts` - Engadir dependencias de MongoDB e Testcontainers
- **FILE-015**: `gradle.properties` - Engadir variables de configuración
- **FILE-016**: `app/src/main/java/jc/dam/primeraapproom/MainActivity.kt` - Refactorizar para usar repositorio
- **FILE-017**: `README.md` - Engadir sección de MongoDB

---

## 6. Tests

### Tests Unitarios

- **TEST-001**: `UserMapperTest.testMapUserToDocument()` - Validar conversión de User a UserDocument
- **TEST-002**: `UserMapperTest.testMapDocumentToUser()` - Validar conversión de UserDocument a User
- **TEST-003**: `RepositoryFactoryTest.testGetRepositoryRoom()` - Validar que factory devolve UserRepositoryRoom cando STORAGE_BACKEND=room
- **TEST-004**: `RepositoryFactoryTest.testGetRepositoryMongo()` - Validar que factory devolve UserRepositoryMongo cando STORAGE_BACKEND=mongodb

### Tests de Integración

- **TEST-005**: `UserRepositoryMongoTest.testInsertAndRetrieveUser()` - Inserir usuario en MongoDB e recuperalo
- **TEST-006**: `UserRepositoryMongoTest.testFindByName()` - Buscar usuario por nome
- **TEST-007**: `UserRepositoryMongoTest.testUpdateUser()` - Actualizar usuario
- **TEST-008**: `UserRepositoryMongoTest.testDeleteUser()` - Eliminar usuario
- **TEST-009**: `UserRepositoryMongoTest.testGetAll()` - Obter todos os usuarios
- **TEST-010**: `UserRepositoryMongoTest.testFindByIds()` - Obter usuarios por lista de IDs

### Criterios de Aceptación dos Tests

- Todos os tests deben pasar con cobertura >80% das nuevas clases
- Tests de integración deben executarse contra instancia real de MongoDB (Testcontainers)
- Tests deben ser deterministicos (non depender de estado externo)
- Tempo de ejecución dos tests debe ser <5 segundos para unitarios, <30 segundos para integración

---

## 7. Riscos e Suposicións

### Riscos

- **RISK-001**: Inconsistencia de datos entre Room e MongoDB se ámbalas dúas se usan simultaneamente. Mitigación: Usar flag de configuración para habilitar soamente un backend
- **RISK-002**: O driver de MongoDB pode aumentar significativamente o tamaño do APK. Mitigación: Usar ProGuard/R8 para minimizar e analizar antes de merge
- **RISK-003**: Erros de conectividade con MongoDB en tiempo de execución (ej. servidor caído). Mitigación: Implementar retry logic e fallback a modo local
- **RISK-004**: Cambios en API de MongoDB entre versións. Mitigación: Fixar versión exacta de driver en build.gradle.kts
- **RISK-005**: Mapeo incorrecto entre tipos de datos (ej. ObjectId vs Int). Mitigación: Tests exhaustivos de mapeo
- **RISK-006**: Performance inferior con MongoDB en operacións de lectura/escritura. Mitigación: Optimizar queries e usar índices en MongoDB

### Suposicións

- **ASSUMPTION-001**: A aplicación xa ten soporte para variables de entorno ou arquivo de configuración
- **ASSUMPTION-002**: O equipo ten acceso a Docker para executar MongoDB localmente
- **ASSUMPTION-003**: A entidade User é a única que necesita migración a MongoDB inicialmente (non hai outras entidades complexas)
- **ASSUMPTION-004**: Non hai requisitos de soportar síncronización de datos entre Room e MongoDB
- **ASSUMPTION-005**: A instancia de MongoDB estará sempre dispoñible na URL configurada en `MONGODB_URI`

---

## 8. Referencias e Lecturas Relacionadas

- [MongoDB Java Driver Documentation](https://www.mongodb.com/docs/drivers/java/)
- [KMongo Documentation](https://litote.org/kmongo/)
- [Android Room Database](https://developer.android.com/training/data-storage/room)
- [Testcontainers with MongoDB](https://www.testcontainers.org/modules/databases/mongodb/)
- [Repository Pattern in Android](https://developer.android.com/topic/architecture/data-layer)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)

