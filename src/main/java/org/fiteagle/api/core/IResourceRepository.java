package org.fiteagle.api.core;

public interface IResourceRepository {
	String LIST_RESOURCES = "listResources";
	String PROP_SERIALIZATION = "serialization";
	String SERIALIZATION_RDFXML_PLAIN = "RDF/XML";
	String SERIALIZATION_RDFXML_ABBREV = "RDF/XML-ABBREV";
	String SERIALIZATION_NTRIPLES = "NT";
	String SERIALIZATION_N3 = "N3";
	String SERIALIZATION_RDFJSON = "RDFJSON";
	String SERIALIZATION_TRIG = "TRIG";
	String SERVICE_NAME = "fiteagle_resourceDatabase";
	String MESSAGE_FILTER = IMessageBus.TARGET + " = '"
			+ SERVICE_NAME + "' OR " + IMessageBus.TYPE + " = '"
			+ IMessageBus.DISCOVERY + "'";

	String REQUEST_FILTER = IMessageBus.TYPE_REQUEST
			+ " = '" + LIST_RESOURCES + "'";

	String listResources();

	String listResources(String type);

	String queryDatabse(String query, String type);
}
