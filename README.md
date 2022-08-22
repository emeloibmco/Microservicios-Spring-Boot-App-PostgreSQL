# Microservicios Spring Boot App PostgreSQL :desktop_computer:

En la presente guía encontrará el paso a paso y las herramientas necesarias para el despliegue en un clúster de Red Hat OpenShift de una aplicación de transacciones bancarias, que fue desarrollada siguiendo la arquitectura de microservicios y que podrá obtener al clonar el presente repositorio.

![](https://user-images.githubusercontent.com/60897075/103002680-4f226180-44fd-11eb-974e-9ddad416156d.png)

### Contenido

1.  [Pre-requisitos](#Pre-Requisitos-pencil)
2.  [Crear un proyecto.](#crear-un-proyecto-pagefacingup)
3.  [Despliegue de la base de datos PostgreSQL.](#despliegue-de-la-base-de-datos-postgresql)
4.  [Creacion de las imágenes](#creación-de-las-imágenes)
5.  [Despliegue de los microservicios](#des)
6.  [Despliegue FrontEnd.](#despliegue-frontend)
7.  [Prueba de funcionamiento.](#prueba-de-funcionamiento)
8.  [Referencias.](#referencias)
9.  [Autores.](#autores)

## Pre-Requisitos :pencil:
* Tener una cuenta en IBM Cloud
* Contar con un clúster de RedHat Openshift
* Tener instalado [kompose](https://kompose.io/)

## Crear un proyecto :page_facing_up:

**Paso 1:** En la sección **clústeres** de la lista de recursos ingrese al suyo y dé clic en el botón **Consola web de OpenShift.** Una vez se encuentre en la consola fíjese que se encuentre como **Developer** y no como **Administrator.**

**Paso 2:** Cree un nuevo proyecto abriendo el menú desplegable de **Project** y luego dé clic en **Create Project.** Proporcione un nombre relacionado con la aplicación y haga clic en **Crear.**

![](https://user-images.githubusercontent.com/60897075/103092116-35981d00-45c4-11eb-9710-e78ebcc434e9.gif)

## Despliegue de la base de datos PostgreSQL

**Paso 1:** Haga clic en **+Add** y luego elija la opción **Database.** En el menú de la izquierda puede filtrar dependiendo de la base de datos que necesite, en este caso seleccione el filtro **Postgres**, elija la primera opción **PostgreSQL** y haga clic en **Instantiate Template.**

**Paso 2:** En las variables requeridas puede dejar los valores por defecto, sin embargo es importante que modifique 3 variables con los valores mostrados en la lista a continuación. Tenga en cuenta que estos valores se configuran en el código de la aplicación y en caso de querer modificarlos puede hacerlo desde el archivo **/src/main/resources/application.properties** de cada microservicio: empresa, persona y transacciones.

*   **Database Service Name:** postgresql
*   **PostgreSQL Connection Username:** postgresadmin
*   **PostgreSQL Connection Password:** Passw0rd2020

![](https://user-images.githubusercontent.com/60897075/103158986-f61f2b80-4791-11eb-9e32-ff73de7c2fd3.gif)

**Paso 3:** Ingrese a la IBM Cloud Shell dando clic en el ícono de **IBM Cloud Shell** desde su cuenta o mediante el [link.](https://cloud.ibm.com/shell). 

Inicie sesión en el clúster con el comando ```Copy login command``` que puede encontrar al dar clic en su nombre de usuario en la esquina superior derecha.

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

**Paso 5:** Obtenga la IP y el puerto en la cual fue expuesto el servicio, guárdela como \<IP\_Servicio>

```shell
oc get svc
```

![](https://user-images.githubusercontent.com/60897075/103159159-e7397880-4793-11eb-81b8-236b8c194053.gif)



## **Creación de las imágenes**
Con el fin de desplegar los microservicios usando la base de datos desplegada previamente, es necesario crear las imágenes respectivas y subirlas a un repositorio de docker.

**Paso 1:** 

Muevase a la carpeta **BackEnd** donde encontrará el archivo ```docker-compose.yml```, el cual creará todas las imágenes configuradas para el despliegue, luego en la consola ejecute el siguiente comando:

```
docker-compose up
```

Puede verificar que las imágenes se construyeron usando el siguiente comando 

```
docker images
```
O usando docker desktop:

![image](img/docker.png)


**Paso 2**

Ahora deberá taggear cada imagen para subirlas al repositorio de su elección, en este caso usamos el repositorio docker hub.

Para cada imagen de cada microservicio el nombre tiene que tener la siguiente estructura:

```
<nombre de usuario docker>/<nombre_imagen_microservicio>:v1
```

Por ejemplo si deseamos taggear la imagen de backend_eureka-server debe ser de la siguiente manera:

```
docker tag backend_eureka-server sebas1411/backend_eureka-server:v1
```

![image](img/tag.png)

Este paso deberá repertilo para cada imagen de cada microservicio (backend_eureka-server, microservicios-empresa, microservicios-persona, microservicios-transacciones, microservicios-zuul).

**Paso 3**

A continuación debe hacer push al repositorio de sus imágenes recientemente taggeadas. Para lo anterior deberá ejecutar el comando:

```
docker login
```
Ingrese sus credenciales y finalmente haga push de sus imágenes usando este comando:

```
docker push <usuario docker>/<nombre imagen microservicio>:<version>
```
![image](img/push.PNG)

**Paso 4**
Finalmente, deberá modificar el archivo **docker-compose-deploy.yml** que encontrará en la ruta ```Backend/deploy```.
Para cada microservicio deberá cambiar la línea
```
image: 
```

En las secciones de lo smicroservicios **microservicio-empresa**, **microservicio-persona** y **microservicio-transaccion** agregue la IP de su servicio de base de datos en el atributo ```spring.datasource.url```
```
spring.datasource.url: jdbc:postgresql://<IP_servicio>:5432/postgresql
```

## Despliegue de los microservicios

Los pasos a continuación son iguales para todos los microservicios y se aplican primero para **eureka-server**,**microservicios-empresa,** luego para **microservicios-persona,** posteriormente en **microservicios-transacciones** , en **microservicios-zuul**.

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

Si los pasos anteriores no funcionaron puede usar el siguiente metodo para desplegar el frontend:

**Paso 1:**
Entre al clúster de OpenShift y asegurese de que estar en el rol de **Developer** y diríjase a la pestaña de *+add* y luego ingrese al apartado de **Container images**.

![deploy1](https://user-images.githubusercontent.com/53380760/180847794-21fd24c3-6ad1-411d-9fba-f679c734ef25.png)

**Paso 2:**

Seleccione la opción de *Image name from external registry* y en el campo de texto pegue la siguiente URL:

```
sebas1411/front-roks:v1    
```

Cambie el *runtime icon* por el de node.js. Por ultimo en el campo *Name* asigne un nombre para distinguir el componente y sus recursos asociados. En este caso el nombre será: *frontend-microservice*.

![deploy2](https://user-images.githubusercontent.com/53380760/180848649-5d573107-171c-4c75-8a03-fd2d1e994a46.png)


Click en el botón *Create* y el despliegue empezará de manera automática.

![deploy3](https://user-images.githubusercontent.com/53380760/180847886-493f3fae-f143-4c82-8e10-7b6d57b1d50a.png)

**Nota:** Las opciones de configuración que no se mencionan en este apartado no necesitan ser modificadas.


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
