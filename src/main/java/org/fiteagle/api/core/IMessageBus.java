package org.fiteagle.api.core;

public interface IMessageBus {
	public static final String TYPE_RESPONSE = "response";
	//public static final String TYPE_REQUEST = "request";
	public static final String TYPE_RESULT = "result";
	public static final String TYPE_EXCEPTION = "exception";
	public static final String TYPE_TARGET = "target";

	public static final String TOPIC_CORE = "topic/core";
	// public static final String TOPIC_USERMANAGEMENT = "topic/usermanagement";
	// public static final String TOPIC_USERMANAGEMENT_NAME = "java:/" +
	// TOPIC_USERMANAGEMENT;
	public static final String TOPIC_CORE_NAME = "java:/" + TOPIC_CORE;
	public static final String TOPIC_ADAPTERS = "java:/topic/adapters";
	public static final String TYPE = "rdf_type";
	public static final String REQUEST = "fiteagle_request";
	public static final String SERIALIZATION = "fiteagle_serialization";
	public static final String QUERY = "fiteagle_query";
	public static final String TARGET = "fiteagle_targets";
	public static final int TIMEOUT = 2000;
	public static final String SERIALIZATION_TURTLE = "TTL";
	public static final String SERIALIZATION_JSONLD = "JSON-LD";
	public static final String DISCOVERY = "fiteagle_discovery";
	public static final String RESPONSE = "fiteagle_response";
	public static final String RESULT = "fiteagle_result";


	public static final String METHOD_TYPE = "method_type";
	public static final String RDF = "rdf";

	public static final String TYPE_REQUEST = "type_request";
	public static final String TYPE_CREATE = "type_create";
	public static final String TYPE_CONFIGURE = "type_configure";
	public static final String TYPE_RELEASE = "type_release";
	public static final String TYPE_INFORM = "type_inform";
	public static final String TYPE_DISCOVER = "type_discover";
	
	        
	// These serializations are compatible with Apache Jena        
	public static final String SERIALIZATION_TURTLE2 = "TURTLE";
  public static final String SERIALIZATION_RDFXML = "RDF/XML";
  public static final String SERIALIZATION_NTRIPLE = "N-TRIPLE";
  
  public static final String SERIALIZATION_DEFAULT = SERIALIZATION_TURTLE2;
  
  public static final String STATUS_400 = "400";
  public static final String STATUS_404 = "404";
  public static final String STATUS_408 = "408";
  public static final String STATUS_200 = "200";
  
}
