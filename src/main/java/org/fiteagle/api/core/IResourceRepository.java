package org.fiteagle.api.core;

public interface IResourceRepository {
	public static enum Serialization {
		XML, TTL
	}

	public String listResources(Serialization type);
}
