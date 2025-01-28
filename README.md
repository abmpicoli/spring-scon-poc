# spring-scon-poc
At work, there is this Simplified Connectors framework:

This framework is used to collect and transform data into different formats / API calls. 

Some examples: 
* Collect incident data from Service-Now and deliver it to an API, that adds the records to a kafka topic;
* Collect incident data from a MAXIMO database table, and send it to kafka;
* Collect data from an API that provides an Server-Sent-Event stream and send notifications to Netcool;


