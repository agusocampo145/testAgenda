# 🗂️ Agenda Full Stack – Java | Spring Boot | Angular

Este proyecto fue desarrollado como parte de una **entrevista técnica** para el puesto de desarrollador Full Stack (Angular + SpringBoot).       

El siguiente proyecto fue desarrollado a lo largo de 3 días con el tiempo libre disponible entre trabajo y estudios.

El desafío consistía en implementar una **aplicación de agenda** con tres versiones progresivas:  
- una **versión de consola** en Java,  
- una **API REST** en Spring Boot,  
- y un **frontend** en Angular que consuma esa API.

---

## 🚀 Objetivo del desafío

Diseñar y construir una aplicación capaz de:
- Registrar **personas y empresas**, vinculando contactos entre ambas.  
- Permitir **búsquedas por nombre y ciudad**, incluyendo filtros combinados (ej: *todos los "Juan Pérez" de Buenos Aires y Córdoba*).  
- Implementar un **manejo de excepciones robusto**.  
- Utilizar **buenas prácticas de arquitectura**, tanto en back como en front.  
- Diseñar un **diagrama de entidad-relación (ER)** representando el modelo de datos.

---

## 🧩 Estructura del proyecto

| Módulo | Descripción | Tecnología principal |
|:--|:--|:--|
| **Agenda Java (Consola)** | Versión inicial en memoria, con carga de datos manual y validaciones mediante excepciones personalizadas. | Java |
| **Agenda Spring Boot (Backend)** | Implementación REST con persistencia en base de datos (JPA/Hibernate), manejo de excepciones global, DTOs y logger. | Spring Boot |
| **Agenda Angular (Frontend)** | Interfaz web para gestión de personas, empresas y contactos. Permite listar, agregar, editar y buscar registros. | Angular |

---

## 🧠 Decisiones de diseño

- **Arquitectura por capas:** separación entre controladores, servicios, repositorios y entidades.  
- **DTOs** para desacoplar las entidades del modelo de base de datos.  
- **Validación de datos** y manejo centralizado de excepciones (`@ControllerAdvice`).  
- **Diseño simple y responsive** en Angular, enfocado en funcionalidad más que en estilo.  
- **Manejo de logs** para trazabilidad y debugging.

---

## 🧮 Diagrama Entidad-Relación (ER)

