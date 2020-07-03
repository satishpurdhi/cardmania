#!/bin/bash

json_token=$(sh login_request.sh)
echo "token: $json_token\n"
curl -H "Authorization: Bearer $json_token" --location --request GET "http://localhost:8080/api/users"
 


