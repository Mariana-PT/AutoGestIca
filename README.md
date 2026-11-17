# AutoGest ICA

Sistema de gestión para un taller automotriz en Ica (Java Swing + MySQL).
Combina una base de datos relacional con implementaciones propias de estructuras de datos.

## Módulos (pestañas)

| Pestaña | Estructura | Clase |
|---|---|---|
| Clientes (BD) | MySQL + JDBC (DAO) | `Datos.ClienteDAO` |
| Repuestos Rápidos | Arreglo 1D | `Estructuras.Arreglo_Unidimensional` |
| Bahías de Trabajo | Matriz 2D (5 días × 4 bahías) | `Estructuras.Arreglo_Bidimensional` |
| Órdenes | Lista enlazada simple | `Estructuras.Lista_Enlazada_Simple` |
| Clientes RAM | Lista doblemente enlazada | `Estructuras.Lista_Doblemente_Enlazada` |
| Historial | Pila (LIFO) | `Estructuras.PilaHistorialAcciones` |
| Recepción | Cola con prioridad | `Estructuras.ColaPrioridadAtencion` |
| Diagnóstico | Árbol binario de búsqueda | `Estructuras.ArbolBinarioServicios` |

Además: `Estructuras.Inventario_TAD` (TAD de inventario).

## Requisitos

- JDK 17 o superior
- MySQL 8+ (o MariaDB)
- Maven 3.8+ (opcional, para descargar el driver y generar el `.jar`)

## Puesta en marcha

1. **Crear la base de datos** (recrea las tablas y carga datos de prueba):
   ```bash
   mysql -u root -p < sql/schema.sql
   ```
2. **Configurar credenciales** (opcional si usas `root` sin contraseña):
   ```bash
   cp db.properties.example db.properties   # y edita usuario/contraseña
   ```
   También puedes usar variables de entorno: `DB_URL`, `DB_USER`, `DB_PASSWORD`.
   `db.properties` está en `.gitignore`: la contraseña no se sube a GitHub.
3. **Compilar y ejecutar**:
   ```bash
   mvn package
   java -jar target/AutoGest_ICA-1.0.jar
   ```

> Sin Maven: descarga `mysql-connector-j` y ejecuta
> `javac -d out $(find src/main/java -name '*.java')` y
> `java -cp out:mysql-connector-j-8.4.0.jar autogest_ica.AutoGest_ICA`
> (en Windows usa `;` en lugar de `:`).

## Estructura del repositorio

```
AutoGest_ICA/
├── pom.xml
├── db.properties.example
├── sql/schema.sql
└── src/main/java/
    ├── autogest_ica/   (main, interfaz, conexión)
    ├── Datos/          (DAO)
    └── Estructuras/    (estructuras de datos)
```
