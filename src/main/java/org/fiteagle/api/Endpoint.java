package org.fiteagle.api;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class Endpoint implements Serializable{

	/**
	 * 
	 */
	@Transient
	@XmlTransient
	private static final long serialVersionUID = -7561461571103631142L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@XmlElement(name="interface")
	private String interfaceType;
	private long service_id;
	private String name;
	private String url;
	@Transient
	private Map<String,LinkInfo> _links;	
	@XmlElement(name="region")
	private long regionId;
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#getId()
	 */
	
	public long getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#setId(long)
	 */
	
	public void setId(long id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#getInterfaceType()
	 */
	
	@XmlElement(name="interface")
	public String getInterfaceType() {
		return interfaceType;
	}
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#setInterfaceType(java.lang.String)
	 */
	
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#getService_id()
	 */
	
	public long getService_id() {
		return service_id;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#setService_id(long)
	 */
	
	public void setService_id(long serviceId) {
		this.service_id = serviceId;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#getName()
	 */
	
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#setName(java.lang.String)
	 */
	
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#get_links()
	 */
	
	public Map<String, LinkInfo> get_links() {
		return _links;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#set_links(java.util.Map)
	 */
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#addLink(java.lang.String, org.fiteagle.xifi.api.model.LinkInfo)
	 */
	
	private void addLink(String name,LinkInfo l){
		if(this._links == null)
			_links =  new HashMap<String, LinkInfo>();
			
		_links.put(name,l);
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#getRegionId()
	 */
	
	public long getRegionId() {
		return regionId;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#setRegionId(long)
	 */
	
	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#getUrl()
	 */
	
	public String getUrl() {
		return url;
	}
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IEndpoint#setUrl(java.lang.String)
	 */
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void addLink(String name, String string) {
		addLink(name, new LinkInfo(string));
		
	}
	
	
	
	
	
	
	
}
