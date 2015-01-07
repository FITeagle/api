package org.fiteagle.api.core;

public interface IMessageBus {
	
	String TOPIC_CORE = "topic/core";
	String TOPIC_CORE_NAME = "java:/" + TOPIC_CORE;
	
	int TIMEOUT = 8000;
	int SHORT_TIMEOUT = 2000;
	
	String METHOD_TYPE = "METHOD_TYPE";
	String TYPE_GET = "GET";
	String TYPE_CREATE = "CREATE";
	String TYPE_CONFIGURE = "CONFIGURE";
	String TYPE_DELETE = "DELETE";
	String TYPE_INFORM = "INFORM";
	
	String METHOD_TARGET = "METHOD_TARGET";
	String TARGET_ORCHESTRATOR = "TARGET_ORCHESTRATOR";
	String TARGET_RESERVATION = "TARGET_RESERVATION";
	String TARGET_RESOURCE_ADAPTER_MANAGER = "TARGET_RESOURCE_ADAPTER_MANAGER";
	String TARGET_FEDERATION_MANAGER = "TARGET_FEDERATION_MANAGER";
	String TARGET_ADAPTER = "TARGET_ADAPTER";
	
  String SERIALIZATION = "serialization"; // These serializations are compatible with Apache Jena	
	String SERIALIZATION_TURTLE = "TURTLE";
	String SERIALIZATION_RDFXML = "RDF/XML";
	String SERIALIZATION_NTRIPLE = "N-TRIPLE";
	String SERIALIZATION_RDFJSON = "RDF/JSON";
	String SERIALIZATION_JSONLD = "JSON-LD";

	String SERIALIZATION_DEFAULT = SERIALIZATION_TURTLE;
	
	String GROUP = "group";
	String RESERVATION = "Reservation";
	
  String TYPE_ERROR = "ERROR";
  
  //To be refactored - currently used by usermanagement
  String TYPE_RESULT = "RESULT";
}
