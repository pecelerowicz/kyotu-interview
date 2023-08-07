The application is trying to read example_file.csv on a startup. \
The file should be placed in the main folder of the project. \
\
The application indexes the temp averages in memory on a startup. \
The indexing can be triggered  through an endpoint when the application is running. \
During the indexing process, the temperature endpoint is blocked. 

Mean temperature (GET):
http://localhost:8080/api/v1/temperature/{city}

Indexing trigger (POST):
http://localhost:8080/api/v1/temperature/indexing
