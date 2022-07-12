# Microservicios Spring Boot App PostgreSQL :desktop_computer:

En la presente guía encontrará el paso a paso y las herramientas necesarias para el despliegue en un clúster de Red Hat OpenShift de una aplicación de transacciones bancarias, que fue desarrollada siguiendo la arquitectura de microservicios y que podrá obtener al clonar el presente repositorio.

![](https://user-images.githubusercontent.com/60897075/103002680-4f226180-44fd-11eb-974e-9ddad416156d.png)

### Contenido

1.  [Crear un proyecto.](#crear-un-proyecto-pagefacingup)
2.  [Despliegue de la base de datos PostgreSQL.](#despliegue-de-la-base-de-datos-postgresql)
3.  [Despliegue de Eureka.](#despliegue-de-eureka)
4.  [Despliegue de los microservicios: Empresa-Persona-Transacciones-Zuul-CommonsTransactions.](#despliegue-de-los-microservicios-empresa-persona-transacciones-zuul-commonstransactions)
5.  [Despliegue FrontEnd.](#despliegue-frontend)
6.  [Prueba de funcionamiento.](#prueba-de-funcionamiento)
7.  [Referencias.](#referencias)
8.  [Autores.](#autores)

## Crear un proyecto :page_facing_up:

**Paso 1:** En la sección **clústeres** de la lista de recursos ingrese al suyo y dé clic en el botón **Consola web de OpenShift.** Una vez se encuentre en la consola fíjese que se encuentre como **Developer** y no como **Administrator.**

**Paso 2:** Cree un nuevo proyecto abriendo el menú desplegable de **Project** y luego de clic en **Create Project.** Proporcione un nombre relacionado con la aplicación y haga clic en **Crear.**

![](https://user-images.githubusercontent.com/60897075/103092116-35981d00-45c4-11eb-9710-e78ebcc434e9.gif)

## Despliegue de la base de datos PostgreSQL

**Paso 1:** Haga clic en **+Add** y luego elija la opción **Database.** En el menú de la izquierda puede filtrar dependiendo de la base de datos que necesite, en este caso seleccione el filtro **Postgres,** elija la primera opción **PostgreSQL** y haga clic en **Instantiate Template.**

**Paso 2:** En las variables requeridas puede dejar los valores por defecto, sin embargo es importante que modifique 3 variables con los valores mostrados en la lista a continuación. Tenga en cuenta que estos valores se configuran en el código de la aplicación y en caso de querer modificarlos puede hacerlo desde el archivo **/src/main/resources/application.properties** de cada microservicio: empresa, persona y transacciones.

*   **Database Service Name:** postgresql
*   **PostgreSQL Connection Username:** postgresadmin
*   **PostgreSQL Connection Password:** Passw0rd2020

![](https://user-images.githubusercontent.com/60897075/103158986-f61f2b80-4791-11eb-9e32-ff73de7c2fd3.gif)

**Paso 3:** Ingrese a la IBM Cloud Shell dando clic en el icono de **IBM Cloud Shell** desde su cuenta o mediante el [link.](https://cloud.ibm.com/shell). 

Inicie sesión en el cluster con el comando ```Copy login command``` que puede encontrar al dar clic en su nombre de usuario en la esquina superior derecha.

Ingrese al proyecto que creó con el siguiente comando:

```shell
oc project <nombre_proyecto>
```
Por ejemplo:
```
oc project microservicios-spring-boot-postgresql
```

**Paso 4:** Exponga la base de datos como un servicio mediante el comando:

```shell
oc expose service <nombre_servicio>
```
En este caso:
```
oc expose service postgresql
```

**Paso 5:** Obtenga la IP y el puerto en la cual fue expuesto el servicio, guárdela como \<IP\_Servicio>:

```shell
oc get svc
```

![](https://user-images.githubusercontent.com/60897075/103159159-e7397880-4793-11eb-81b8-236b8c194053.gif)

**Paso 6:** Clone el repositorio e ingrese al archivo **/src/main/resources/application.properties** de cada microservicio: empresa, persona y transacciones. 

```shell
git clone https://github.com/emeloibmco/Microservicios-Spring-Boot-App-PostgreSQL.git
```
Para editar los archivos puede realizarlo desde el editor que desee o desde la consola, con el siguiente comando:
```
vim BackEnd/<nombre_microservicio>/src/main/resources/application.properties 
```

**Paso 7:** Modifique la variable **spring.datasource.url** de la siguiente forma y guarde los cambios.

```
spring.datasource.url=jdbc:postgresql://<IP_Servicio>:54593/db_microservices_app 
```

![](https://user-images.githubusercontent.com/60897075/103159233-f7058c80-4794-11eb-91be-02322a3e39dd.gif)

## **Despliegue de Eureka**

El servidor Eureka almacenará la información de todos los microservicios registrados así como su estado. Para su despliegue se hace uso de [OpenShift Do developer CLI (odo)](https://docs.openshift.com/container-platform/4.2/cli_reference/openshift_developer_cli/understanding-odo.html) siguiendo los pasos descritos a continuación. 

**Paso 1:** Desde la CLI de IBM Cloud  ejecute el siguiente comando para listar los componentes que son compatibles con odo.

```shell
odo catalog list components
```

**Paso 2:** Ubíquese en la carpeta correspondiente al microservicio **microservicios-eureka** y ejecute el siguiente comando, teniendo en cuenta que java es el componente de la lista vista en el paso anterior que se ajusta a la aplicación que va a ser desplegada.

```shell
odo create java eureka-service
```

**Paso 3:** El paso anterior creará la carpeta oculta **odo** con el archivo **config.yaml** dentro de ella, abra el archivo mediante vim o el editor de su preferencia.

```shell
cd .odo/
```
```
vim config.yaml
```

**Paso 4:** En el archivo **config.yaml** encontrará una sección de puertos, modifíquela para que el servicio de eureka se exponga únicamente por el **puerto 8761**, para esto elimine los puertos por defecto y agregue el 8761. la sección de puertos deberá quedar así:

```shell
Ports:
- 8761/TCP 
```

*Puede realizar la edición en el editor vim usando el comando ```i``` y guardar los cambios realizados con el comando ```:wq```*

**Paso 5:** Para verificar que el paso 4 se haya ejecutado de manera exitosa puede volver a la carpeta raiz **microservicios-eureka** y correr el comando:

```shell
odo config view
```

**Paso 6:** Suba la aplicación.

```shell
odo push
```

**Paso 7:** Liste todos los servicios y despliegues en su clúster y anote la IP y el puerto del servicio de eureka.

```shell
oc get all
```

![](https://user-images.githubusercontent.com/60897075/103291795-8b9d0400-49ba-11eb-9cf5-c2b11a29179f.gif)

## Despliegue de los microservicios: Empresa-Persona-Transacciones-Zuul-CommonsTransactions

Los pasos a continuación son iguales para todos los microservicios y se aplican primero para **microservicios-empresa,** luego para **microservicios-persona,** posteriormente en **microservicios-transacciones** , en **microservicios-zuul** y finalmente **Commons-Transactions.**

**Paso 1:** Ingrese al archivo **application.properties** que se encuentra en la dirección **/src/main/resources/** de cada microservicio y en la variable **eureka.client.service-url.defaultZone** reemplace el valor de **IP_eureka** por la ip del servicio de Eureka anotada en la sección anterior de la guía. (Este paso no aplica para commons-transactions)

```shell
vim BackEnd/<nombre_microservicio>/src/main/resources/application.properties
```

```
eureka.client.service-url.defaultZone=http://<IP_eureka>:8761/eureka
```

**Paso 2:** En la carpeta raíz del microservicio ejecute el siguiente comando:

```shell
odo create java <nombre_microservicio>
```

**Paso 3:** El paso anterior creará la carpeta oculta **odo** con el archivo **config.yaml** dentro de ella, abra el archivo mediante vim o el editor de su preferencia.

```shell
cd .odo/
```
```
vim config.yaml
```

**Paso 4:** En el archivo **config.yaml** encontrará una sección de puertos, modifíquela para que el microservicio se exponga por un único puerto según la siguiente lista:

*   microservicios-empresa: 8890
*   microservicios-persona: 8060
*   microservicios-transacciones: 8020
*   microservicios-zuul: 8090
*   commons-transactions: 8443

```shell
Ports:
- 8761/TCP 
```

**Paso 5:** Regrese una carpeta con el comando ```cd ..``` y suba el microservicio:

```shell
odo push
```

![](https://user-images.githubusercontent.com/60897075/103298640-c5750700-49c8-11eb-957f-436d7d0fe644.gif)

## Despliegue FrontEnd
Para realizar el despliegue del FrontEnd de la aplicación, siga los pasos que se muestran a continuación:

**Paso 1:** 
Diríjase a la carpeta FrontEnd, mediante el comando:
```
cd FrontEnd
```
>**Nota:** Recuerde que se debe encontrar dentro de la carpeta **Microservicios-Spring-Boot-App-PostgreSQL**. Si aún se encuentra dentro de la carpeta **BackEnd** puede salir mediante el comando:
```
cd ..
```

**Paso 2:**
Implementar la aplicación en OpenShift. Para ello ejecute el siguiente comando:
```
npx nodeshift --strictSSL=false --dockerImage=nodeshift/ubi8-s2i-web-app --imageTag=10.x --build.env YARN_ENABLED=true  --expose
```
Para mayor información puede consultar <a href="https://developers.redhat.com/blog/2018/10/04/modern-web-apps-openshift-part-1/"> Modern web applications on OpenShift: Part 1 — Web apps in two commands </a>


## Prueba de funcionamiento
Para verificar el funcionamiento de la aplicación realice los siguientes pasos:

**Paso 1:** Entre al clúster de OpenShift y abra la **Consola web de OpenShift**.

**Paso 2:** Asegurese de tener seleccionado el rol de **Developer** y posteriormente diríjase a la pestaña de *Topology*.

**Paso 3:** Busque el proyecto en el cual está trabajando, seleccione el recurso **node.js** sobre el realizó la implementación del FrontEnd y de click en **Open URL**.
<p align="center"><img width="700" src="https://github.com/emeloibmco/Microservicios-Spring-Boot-App-PostgreSQL/blob/master/Open%20URL.gif"></p>

**Paso 4:** Si realizó todos los pasos correctamente debe observar la aplicación de forma similar a como se muestra en la siguiente imagen:
<p align="center"><img width="700" src="https://github.com/emeloibmco/Microservicios-Spring-Boot-App-PostgreSQL/blob/master/Aplicacion-microservicios.gif"></p>

## Referencias

La documentación sobre odo puede encontrarla en la página de [Red Hat OpenShift](https://docs.openshift.com/container-platform/4.2/cli_reference/openshift_developer_cli/understanding-odo.html).

## Autores

IBM Cloud Tech Sales :cloud:
