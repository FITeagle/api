package org.fiteagle.api.core;

public interface IMessageBus {
	String TYPE_RESPONSE = "response";
	// public static final String TYPE_REQUEST = "request";
	String TYPE_RESULT = "result";
	String TYPE_EXCEPTION = "exception";
	String TYPE_TARGET = "target";

	String TOPIC_CORE = "topic/core";
	// public static final String TOPIC_USERMANAGEMENT = "topic/usermanagement";
	// public static final String TOPIC_USERMANAGEMENT_NAME = "java:/" +
	// TOPIC_USERMANAGEMENT;
	String TOPIC_CORE_NAME = "java:/" + TOPIC_CORE;
	String TOPIC_ADAPTERS = "java:/topic/adapters";
	String TYPE = "rdf_type";
	String REQUEST = "fiteagle_request";
	String SERIALIZATION = "fiteagle_serialization";
	String QUERY = "fiteagle_query";
	String TARGET = "fiteagle_targets";
	int TIMEOUT = 6000;
	String SERIALIZATION_TURTLE = "TTL";
	String SERIALIZATION_JSONLD = "JSON-LD";
	String DISCOVERY = "fiteagle_discovery";
	String RESPONSE = "fiteagle_response";
	String RESULT = "fiteagle_result";

	String METHOD_TYPE = "method_type";
	String RDF = "rdf";

	String TYPE_REQUEST = "type_request";
	String TYPE_CREATE = "type_create";
	String TYPE_CONFIGURE = "type_configure";
	String TYPE_RELEASE = "type_release";
	String TYPE_INFORM = "type_inform";
	String TYPE_DISCOVER = "type_discover";

	// These serializations are compatible with Apache Jena
	String SERIALIZATION_TURTLE2 = "TURTLE";
	String SERIALIZATION_RDFXML = "RDF/XML";
	String SERIALIZATION_NTRIPLE = "N-TRIPLE";

	String SERIALIZATION_DEFAULT = SERIALIZATION_TURTLE2;

	String STATUS_400 = "400";
	String STATUS_404 = "404";
	String STATUS_408 = "408";
	String STATUS_200 = "200";
	String STATUS_201 = "201";

}
