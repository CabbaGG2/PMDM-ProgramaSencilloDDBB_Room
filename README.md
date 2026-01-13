# PrimeraAppRoom

> [!NOTE]
> Este README está escrito en galego segundo as instrucións do proxecto.

Breve descrición
----------------
PrimeraAppRoom é unha aplicación Android de exemplo construída con Android Studio e Gradle. O seu obxectivo é servir como base didáctica para traballar con Room, arquitectura moderna de Android e boas prácticas de proxecto.

Características principais
--------------------------
- Exemplo de uso de Room para persistencia local.
- Configuración estándar con Gradle e Android Studio.
- Estrutura de proxecto preparada para tests e extensibilidade.

Índice
------
- [Requisitos](#requisitos)
- [Instalación e execución](#instalaci%C3%B3n-e-execucci%C3%B3n)
- [Estructura do proxecto](#estructure-do-proxecto)
- [Assets / Logos](#assets--logos)
- [Como contribuír](#como-contribu%C3%ADr)
- [Autoría](#autor%C3%ADa)

Requisitos
----------
- JDK 11 ou superior (segundo configuración do proxecto).
- Android Studio (recomendado) ou SDK Android + Gradle.
- Conexión a internet para descargar dependencias na primeira compilación.

Instalación e execución
-----------------------
1. Abre o proxecto en Android Studio: selecciona a carpeta raíz `PrimeraAppRoom`.
2. Para compilar desde terminal:

```bash
./gradlew assembleDebug
```

3. Para instalar e executar no dispositivo/emulador conectado:

```bash
./gradlew installDebug
```

4. Alternativamente, usa o menú Run de Android Studio para executar a aplicación.

Estructura do proxecto
----------------------
- `app/` — módulo principal da aplicación.
  - `src/main/java` — código fonte da aplicación.
  - `src/main/res` — recursos (layouts, drawables, mipmaps, values).
  - `build.gradle.kts` — configuración de Gradle para o módulo.
- `gradle/`, `gradlew`, `settings.gradle.kts` — configuración do construtor.

Assets / Logos
---------------
> [!IMPORTANT]
> Non se atopou un cartafol `assets` na raíz do proxecto; en vez diso, usei os iconos e recursos presentes en `app/src/main/res`.

Os logos e iconos principais están en:

- `app/src/main/res/mipmap-anydpi/` (ex.: `ic_launcher.xml`, `ic_launcher_round.xml`)
- `app/src/main/res/drawable/` (ex.: `ic_launcher_foreground.xml`, `ic_launcher_background.xml`)

Podes empregar eses ficheiros como imaxe representativa do proxecto. Exemplo de inserción nun README (nota: GitHub pode non renderizar XML como imaxe, normalmente usarás PNG/SVGs en `assets` ou `res`):

```
![Ícone do proxecto](app/src/main/res/mipmap-anydpi/ic_launcher.xml)
```

Como contribuír
----------------
- Abre unha issue para propoñer cambios ou corrixir fallos.
- Para cambios de código, crea un fork e envía un pull request coas descricións e tests necesarios.
- Mantén as modificacións pequenas e documentadas.

Notas para desenvolvedores
--------------------------
- Verifica configuracións de Kotlin/Gradle antes de actualizar dependencias.
- Engade tests unitarios en `app/src/test` e instrumentados en `app/src/androidTest` cando sexa posible.

Autoría
-------
Profesor Damián Nogueiras

> [!TIP]
> Se precisas que inclúa un logo específico ou un ficheiro na carpeta `assets`, indícao e podo engadilo ao proxecto e actualizar o README en consecuencia.

