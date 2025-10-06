
# 🗂️ Agenda Full Stack – Java | Spring Boot | Angular 
Este proyecto fue desarrollado como parte de una **entrevista técnica** para el puesto de desarrollador Full Stack (Angular + SpringBoot).

⭐ El siguiente proyecto fue desarrollado a lo largo de 3 días con el tiempo libre disponible entre trabajo y estudios.

El proyecto consiste en:

- una **versión de consola** en Java,  
- una **API REST** en Spring Boot,  
- y un **frontend** en Angular que consuma esa API.

---

## 🚀 Objetivo del desafío
- Registrar **personas y empresas**, vinculando contactos entre ambas.  
- Permitir **búsquedas por nombre y ciudad**, incluyendo filtros combinados.  
- Implementar **manejo de excepciones robusto**.  
- Aplicar **buenas prácticas de arquitectura**.  
- Diseñar un **diagrama de entidad-relación (ER)** representando el modelo de datos.

---

## 🧩 Estructura del proyecto

| Módulo | Descripción | Tecnología principal |
|:--|:--|:--|
| **Agenda Java (Consola)** | Versión inicial en memoria, con carga de datos manual y validaciones mediante excepciones personalizadas. | Java 17 |
| **Agenda Spring Boot (Backend)** | Implementación REST con persistencia en base de datos, manejo de excepciones global, DTOs y logger. | Spring Boot 3.1.5 |
| **Agenda Angular (Frontend)** | Interfaz web para gestión de personas, empresas y contactos. Permite listar, agregar, editar y buscar registros. | Angular 19.2.13 |

---

## 🧠 Decisiones de diseño
- Arquitectura por capas: separación entre controladores, servicios, repositorios y entidades.  
- DTOs para desacoplar las entidades del modelo de base de datos.  
- Validación de datos y manejo centralizado de excepciones (`@ControllerAdvice`).  
- Diseño simple y responsive en Angular 19.  
- Manejo de logs para trazabilidad y debugging.  
- Documentación automática de endpoints con Swagger / OpenAPI.  

---

## 🧰 Tecnologías utilizadas

### 🔹 Backend
- Java 17  
- Spring Boot 3.1.5  
- Spring Data JPA  
- H2 Database (en memoria)  
- Jakarta Validation  
- Swagger / OpenAPI 2.2.0  
- Maven  

### 🔹 Frontend
- Angular 19.2.13  
- TypeScript 5.7.2  
- RxJS 7.8  

### 🔹 General
- Git & GitHub  
- Postman / Swagger UI  
- Visual Studio Code / IntelliJ IDEA  

---

## ⚡ Ejecución

```bash
# Backend
cd agenda-springboot
mvn spring-boot:run

# Frontend
cd agenda-angular
npm install
ng serve

# Consola
cd agenda-java
javac Main.java
java Main
```

---

## 👨‍💻 Autor

**Agustín Ocampo**  
Full Stack Developer | Angular · Spring Boot · Firebase  
📍 Buenos Aires, Argentina  
📧 [oca_a@outlook.com.ar](mailto:oca_a@outlook.com.ar)  
🔗 [LinkedIn](https://www.linkedin.com/in/agustin-ocampo-5684b8182/)  
🌐 [Portfolio](https://portfolio-zs.web.app)  
