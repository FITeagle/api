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

	String TYPE_REQUEST = "type_request";
	String TYPE_CREATE = "type_create";
	String TYPE_CONFIGURE = "type_configure";
	String TYPE_RELEASE = "type_release";
	String TYPE_INFORM = "type_inform";
	String TYPE_DISCOVER = "type_discover";
	
	String TYPE_RESPONSE = "response";
  String TYPE_RESULT = "result";
  String TYPE_ERROR = "error";
  String TYPE_TARGET = "target";

	// These serializations are compatible with Apache Jena
	String SERIALIZATION_TURTLE = "TURTLE";
	String SERIALIZATION_RDFXML = "RDF/XML";
	String SERIALIZATION_NTRIPLE = "N-TRIPLE";
	String SERIALIZATION_RDFJSON = "RDF/JSON";

	String SERIALIZATION_DEFAULT = SERIALIZATION_TURTLE;
	
}
