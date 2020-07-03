#!/bin/bash

occasion_id="a989055a-b4f4-11ea-a1d1-6a0000c30600"
curl --location --request GET "http://localhost:8080/api/card/occasion/$occasion_id" --header 'Content-type: application/json'


