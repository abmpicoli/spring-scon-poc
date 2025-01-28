# spring-scon-poc
At work, there is this Simplified Connectors framework:

This framework is used to collect and transform data into different formats / API calls. 

Some examples: 
* Collect incident data from Service-Now and deliver it to an API, that adds the records to a kafka topic;
* Collect incident data from a MAXIMO database table, and send it to kafka;
* Collect data from an API that provides an Server-Sent-Event stream and send notifications to Netcool;

This project is an study on how to perform something similar using spring.

## Framework features:
### No need of a java IDE or java knowledge, to create an integration:
* All a developer needs are a property file, bridge.properties, plus a series of xslt transforms.
### All payloads are converted to xml, so they can be transformed:
I consider this more a hindrance than a feature, to be honest, but this is how the cookie crumbles: to convert a json content, 
the XSL 3.0 feature json-to-xml is used. To convert the data back to json to send to an api, xml-to-json is used.

Which makes some xsl transforms absurdly difficult to understand/follow, and incredibly verbose.



