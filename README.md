# Hair Salon

#### Database Basics Independent Project for Epicodus, 05/06/2016

#### By **Kevin Deganos**

## Description

Website for a hair salon owner. The owner can add stylists, add clients to a stylist, and later update client information. The site will also track an overall rating for each stylist based on a client score.

## Setup/Installation Requirements

* This website was created with Java, Spark Framework, Apache Velocity, Bootstrap, Animate.css, and PostgreSQL
* In PSQL:
  * CREATE DATABASE hair\_salon;
  * CREATE TABLE stylists (id serial PRIMARY KEY, name varchar, rating float, review\_total float, review\_counter int);
  * CREATE TABLE clients (id serial PRIMARY KEY, name varchar, stylist_id int, rating int);


### License

*Code released under the MIT license.*

Copyright (c) 2016 **Kevin Deganos**
