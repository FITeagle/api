package org.fiteagle.api.core;

public interface IResourceRepository {
	public static enum Serialization {
		XML, TTL
	}

	public static String LIST_RESOURCES = "listResources";
	public static String PROP_SERIALIZATION = "serialization";
	public static String SERIALIZATION_TTL = "ttl";
	public static String SERIALIZATION_XML = "xml";
	public static String MESSAGE_FILTER = IMessageBus.TYPE_REQUEST + " = '" + LIST_RESOURCES + "'";

	public String listResources(Serialization type);
}
