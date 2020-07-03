#!/bin/bash


curl -v --header "Content-type: application/json" --data '{"email": "dave@example.com", "password": "password", "firstName": "Tom", "lastName": "Info"}' --request POST "http://localhost:8080/api/user"  
 
