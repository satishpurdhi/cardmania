#!/bin/bash

#curl -v --header "Content-type: application/json" --data '{"firstName": "Joey", "lastName": "Info", "email": "dave@example.com", "password": "password"}' --request POST "http://localhost:8080/api/user"  

generate_post_data()
{
  cat <<EOF
{
  "senderAddress": {
    "name": "Joe Recipient",
    "city": "Los Angeles",
    "state": "California",
    "zip_code": "60615",
    "street": "555 Hello Way"
  },
  "recipientAddress": {
    "name": "Joe Recipient",
    "city": "Los Angeles",
    "state": "California",
    "zip_code": "60615",
    "street": "555 Hello Way"
  },
  "card": {
    "id": "05b7af7c-1de5-4a72-aebf-4c9e4d9acec3"
  },
  "message": "Hi"
}
EOF
}

echo "$(generate_post_data)" > /tmp/data.txt 
curl --location --request POST 'http://localhost:8080/api/mailing' --header 'Content-type: application/json' -d @/tmp/data.txt 
 
