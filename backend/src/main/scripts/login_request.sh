#!/bin/bash

# Echo the request 
ret=$(curl --header "Content-type: application/json" --data '{"username": "dave@example.com", "password": "password"}' --request POST "http://localhost:8080/authenticate")
echo $ret > /tmp/out 
access_token=$( sed -e 's/^.*"token":"\([^"]*\)".*$/\1/' /tmp/out )    

# Output access token 
echo $access_token


