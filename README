# Postgresql & PgAdmin & RabbitMQ & Zipkin powered by compose

IL faut demarrer EUREKA SEVER en Premier

## Requirements:
* docker >= 17.12.0+
* docker-compose

## Quick Start
* Clone or download this repository
* Go inside of directory,  `cd demo2`
* Run this command `docker-compose up -d`
* Run this command `docker compose down` to  stop


## Environments
This Compose file contains the following environment variables:

* `POSTGRES_USER` the default value is **postgres**
* `POSTGRES_PASSWORD` the default value is **changeme**
* `PGADMIN_PORT` the default value is **5050**
* `PGADMIN_DEFAULT_EMAIL` the default value is pgadmin4@pgadmin.org
* `PGADMIN_DEFAULT_PASSWORD` the default value is **admin**

## Access to postgres: 
* `localhost:5432`
* **Username:** postgres (as a default)
* **Password:** changeme (as a default)

## Access to PgAdmin: 
* **URL:** `http://localhost:5050`
* **Username:** pgadmin4@pgadmin.org (as a default)
* **Password:** admin (as a default)
* il faut parametrer le server, je met n'importe quel nom pour le server(le nom specifier pour le conteneur postgres et pas localhost:1234..) et je mets le pwd et login que j'ai configuré dans le fichier docker compose
* Penser à stoper le service postgres deja installer sur windows msconfig chercher postgres

## Add a new server in PgAdmin:
* **Host name/address** `postgres`
* **Port** `5432`
* **Username** as `POSTGRES_USER`, by default: `postgres`
* **Password** as `POSTGRES_PASSWORD`, by default `changeme`

## Logging

There are no easy way to configure pgadmin log verbosity and it can be overwhelming at times. It is possible to disable pgadmin logging on the container level.

Add the following to `pgadmin` service in the `docker-compose.yml`:

```
logging:
  driver: "none"
```

## Access to Server Config cloud: 
* **URL:** `http://localhost:8888/[fileName]/master`
* Exemple : `http://localhost:8888/application/master`
* il faut qu'il soit demarrer prealablement
* il faut crrer un repot git (git init), et faire un commite des differents fichier properties pour que ça marche


## Access to Zipkin: 
* **URL:** `http://localhost:9411`


## Access to RabbitMQ: 
* **URL:** `http://localhost:15672`
* **Username:** guest
* **Password:** guest
* guid for application RabbitMQ : https://spring.io/guides/gs/messaging-rabbitmq/


## Access to KAFKA: 
* **URL:** `http://localhost:15672`
* **Username:** guest
* **Password:** guest
* Guid for kafka : https://kafka.apache.org/quickstart
* **STEP 1: Download the latest Kafka release and extract it**
* `$ tar -xzf kafka_2.13-3.5.0.tgz`
* `$ cd kafka_2.13-3.5.0`
* **STEP 2: START THE KAFKA ENVIRONMENT**
* **STEP 2-1 Start the ZooKeeper service**
* `$ bin/zookeeper-server-start.sh config/zookeeper.properties`
* **STEP 2-2 Start the Kafka broker service**
* `$ bin/kafka-server-start.sh config/server.properties`
* Once all services have successfully launched, you will have a basic Kafka environment running and ready to use.
* **STEP 3: READ THE EVENTS**
* `$ bin/kafka-console-consumer.sh --topic topic.customer --from-beginning --bootstrap-server localhost:9092`
