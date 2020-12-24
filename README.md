# Microservicios Spring Boot App PostgreSQL

En la presente guía encontrará el paso a paso y las herramientas necesarias para el despliegue en un clúster de Red Hat OpenShift de una aplicación de transacciones bancarias, que fue desarrollada siguiendo la arquitectura de microservicios y que podrá obtener al clonar el presente repositorio.

![](https://user-images.githubusercontent.com/60897075/103002680-4f226180-44fd-11eb-974e-9ddad416156d.png)

## Crear un proyecto

**Paso 1:** En la sección **clústeres** de la lista de recursos ingrese al suyo y de clic en el botón **Consola web de OpenShift.** Una vez se encuentre en la consola fíjese que se encuentre como **Developer** y no como **Administrator.**

**Paso 2:** Cree un nuevo proyecto abriendo el menú desplegable de **Project** y luego de clic en **Create Project.** Proporcione un nombre relacionado con la aplicación y haga clic en **Crear.**

![](https://user-images.githubusercontent.com/60897075/103092116-35981d00-45c4-11eb-9710-e78ebcc434e9.gif)

## Despliegue de la base de datos PostgreSQL

**Paso 1:** Haga clic en **+Add** y luego elija la opción **Database.** En el menú de la izquierda puede filtrar dependiendo de la base de datos que necesite, en este caso seleccione el filtro **Postgres,** elija la primera opción **PostgreSQL** y haga clic en **Instantiate Template.**

**Paso 2:** En las variables requeridas puede dejar los valores por defecto, sin embargo es importante que modifique 3 variables con los valores mostrados en la lista a continuación. Tenga en cuenta que estos valores se configuran en el código de la aplicación y en caso de querer modificarlos puede hacerlo desde el archivo **/src/main/resources/application.properties** de cada microservicio: empresa, persona y transacciones.

*   **Database Service Name:** db\_microservices\_app
*   **PostgreSQL Connection Username:** admin
*   **PostgreSQL Connection Password:** sasa

**Paso 3:** Ingrese a la IBM Cloud Shell dando clic en el icono de **IBM Cloud Shell** desde su cuenta o mediante el [link.](https://cloud.ibm.com/shell) Ejecute el comando mostrado a continuación para ingresar al proyecto creado.

```shell
oc project <nombre_proyecto>
oc project microservicios-spring-boot-postgresql
```

**Paso 4:** Exponga la base de datos como un servicio mediante el comando:

```
oc expose service <nombre_servicio>
oc expose service postgresql
```

**Paso 5:** Ingrese al archivo **/src/main/resources/application.properties** de cada microservicio: empresa, persona y transacciones. Modifique la variable **spring.datasource.url** de la siguiente forma: **spring.datasource.url=jdbc:postgresql://\<IP\_Servicio>:54593/db\_microservices\_app** y guarde los cambios.

## **Despliegue de Eureka**
