# How to set your API Key

This app requires an API Key to be defined for the NEoWs API provided by NASA. For that purpose a 
file "ApiKey.kt" must be created in the directory "app/src/main/java/com/udacity/asteroidradar/api".
The file content has to be:

    package com.udacity.asteroidradar.api

    const val API_KEY = <your_api_key_as_string>
