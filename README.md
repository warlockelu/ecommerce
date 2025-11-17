# Ecommerce Microservices Project

This project is a simple e-commerce system built using Spring Boot, MongoDB, Cassandra, Kafka, and Docker.

It supports:
- User registration and login (JWT based)
- Item browsing
- Add to cart
- Place order
- Update order
- Cancel order
- Logout

Frontend pages are located in: src/main/resources/static

-------------------------------------

## Tech Stack

Backend:
- Spring Boot 3
- Spring Security (JWT)
- Spring Data MongoDB
- Spring Data Cassandra
- Kafka Messaging

Databases:
- MongoDB (items, accounts)
- Cassandra (orders)
- Kafka + Zookeeper (order events)
- MySQL (depending on course template)

Others:
- Docker and docker-compose

-------------------------------------

## How to Start the Project

### 1. Start all required services

Run from project root:

docker-compose up -d

This will start:
- MongoDB (27017)
- Cassandra (9042)
- Kafka
- Zookeeper
- MySQL (if included)

Check containers:

docker ps

-------------------------------------

## 2. Initialize Cassandra

Enter Cassandra shell:

docker exec -it cassandra cqlsh

Create keyspace and table:

CREATE KEYSPACE ecommerce
WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

USE ecommerce;

CREATE TABLE orders (
id text PRIMARY KEY,
username text,
itemids list<text>,
total double,
status text
);

-------------------------------------

## 3. (Optional) Reset MongoDB items

If you want Spring Boot to re-seed items:

docker exec -it mongo mongosh
use ecommerce
db.items.drop()
exit

Spring Boot will reinsert items on next startup.

-------------------------------------

## 4. Start Spring Boot Application

From IntelliJ:
Click “Run” on EcommerceApplication.

Or use command:

mvn spring-boot:run

-------------------------------------

## 5. Access the Web UI

Login page:
http://localhost:8080/login.html

Register:
http://localhost:8080/register.html

Item Catalog (requires login):
http://localhost:8080/items.html

Features:
- Add to Cart
- Place Order
- Update Last Order
- Cancel Last Order
- Logout
