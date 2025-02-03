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

So... let's think about something here: 

## The Architecture as-is:
* A Consumer collects contents from a source:
  * an HttpConsumer listens for data at a specific port.
  * A ScheduleConsumer generates an xml at regular intervals, to trigger processing.
  * A FileConsumer listens to the presence of files at a specific directory, to trigger processing.
  * A SSEConsumer listens to a persistent connection to the server-sent event stream and collects data.
* A Provider perform operations on the inbound data and generate an output payload.
* A Processor chains multiple providers together: it listens to the outbound data provided, and, based on specific data collected from the output, decides which provider to invoke next, or
  if the operation is final.

## The architecture as it should be:

Thinking about the following characteristics:
* We should be able to handle multiple payloads: csv data, yaml, json, binary json, xml, and then handle internally everything with a format compatible with the consumer, provider, etc.
* So we need to formalize the idea of a transform:
  * A Consumer, Provider, etc should have a specification about the required data format: If it is org.w3c.dom.Document, or if it is a HashMap<Object,Object>, or even a String.
  * Which is what SPRING does in that reactive framework... From the SSEConsumer at work:
    ```java
    import reactor.core.publisher.Flux;
    Flux<ServerSentEvent<String>> baseFlux = client.method(method).uri(url.toURI()))/* . other stuff here */.retrieve(), (response) -> {
                /* ... remove other side effects...
                response.bodyToFlux(String.class).publishOn(Schedulers.newSingle("receiveMessage")).subscribe(event -> {
                    /** do stuff with the event received*/
                });
            }).bodyToFlux(type);
    ```

    The key aspect is the ".bodyToFlux" method: that is a translator to whatever type is specified...
* Once we have the data in the format required by the provider, it will generate an output, that may be anything: Document, HashMap,String, etc.
* So, given the output type, we should have a translator rooster that collects the input data and turn it into whatever output type is needed.
* The Processor in the current version is a passive thing: it looks for a specific xpath in the output (which makes it addicted to DOM documents), and simply invokes the action specified by the xpath.

  If there is no data in that xpath, it considers processing finished.

  Perhaps we can make the processor to be an active thing? Have a way to "dialog" with the output data and then have the control placed inside the processor?

  This has a lot of advantages: 
  * Processor based variables: the processor may have an intermediate data cache that has transaction scope.
  * Possibility to invoke a provider with custom parameters.

    Today if a dev needs to keep transaction data it is needed to copy all data between each step in the processor flow: totally messy stuff.
* Finally, Providers should "share" connections: there are many providers that invoke the same database to perform different queries... HM... what is the root cause for that? BECAUSE PROVIDERS ARE DUMB.

  AND PROCESSORS ARE ALSO DUMB.



