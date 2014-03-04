package org.fiteagle.api.core;

public interface IResourceRepository {
	public static enum Serialization {
		XML, TTL
	}
	public static String LIST_RESOURCES = "listResources";
	public static String TYPE_SERIALIZATION = "serialization";
	public static String SERIALIZATION_TTL = "ttl";
	public static String SERIALIZATION_XML = "xml";
	
	public String listResources(Serialization type);
}
