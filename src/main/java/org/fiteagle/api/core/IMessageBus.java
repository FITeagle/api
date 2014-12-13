package org.fiteagle.api.core;

public interface IMessageBus {
	
	String TOPIC_CORE = "topic/core";
	String TOPIC_CORE_NAME = "java:/" + TOPIC_CORE;
	
	String TYPE = "rdf_type";
	String REQUEST = "fiteagle_request";
	String SERIALIZATION = "fiteagle_serialization";
	String QUERY = "fiteagle_query";
	String TARGET = "fiteagle_targets";
	
	int TIMEOUT = 8000;
	int SHORT_TIMEOUT = 2000;
	
	String SERIALIZATION_JSONLD = "JSON-LD";
	
	String METHOD_TYPE = "method_type";
	String RDF = "rdf";

	String TYPE_REQUEST = "REQUEST";
	String TYPE_CREATE = "CREATE";
	String TYPE_CONFIGURE = "CONFIGURE";
	String TYPE_RELEASE = "RELEASE";
	String TYPE_INFORM = "INFORM";
	String TYPE_DISCOVER = "DISCOVER";
	
	String TYPE_RESPONSE = "RESPONSE";
  String TYPE_RESULT = "RESULT";
  String TYPE_ERROR = "ERROR";
  String TYPE_TARGET = "TARGET";

	// These serializations are compatible with Apache Jena
	String SERIALIZATION_TURTLE = "TURTLE";
	String SERIALIZATION_RDFXML = "RDF/XML";
	String SERIALIZATION_NTRIPLE = "N-TRIPLE";
	String SERIALIZATION_RDFJSON = "RDF/JSON";

	String SERIALIZATION_DEFAULT = SERIALIZATION_TURTLE;
	
}
