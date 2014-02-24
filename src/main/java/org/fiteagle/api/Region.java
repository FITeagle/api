package org.fiteagle.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;


//@XmlRootElement
@Entity
public class Region extends LinkableEntity {
	
	@Id
	@Column(name="ID")
	@GeneratedValue
	long id;
	String country;
	String latitude;
	String longitude;
	String adminUsername;
	@OneToOne(cascade=CascadeType.ALL, targetEntity=RegionStatus.class)
	@PrimaryKeyJoinColumn
	RegionStatus regionStatus;
	String nodeType;

	@Transient
	Map<String, List<LinkInfo>> _links;
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#get_links()
	 */
	
	public Map<String,List<LinkInfo>> get_links() {
		return _links;
	}
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#set_links(java.util.Map)
	 */
	
	public void set_links(Map<String, List<LinkInfo>> links) {
		this._links = links;
	}
	
	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#addLink(java.lang.String, org.fiteagle.xifi.api.model.LinkInfo)
	 */
	
	public void addLink(String name,LinkInfo l){
		List<LinkInfo> list;
		if(this._links == null){
			_links = new HashMap<>();
			list = new ArrayList<>();
			list.add(l);
		}else{
			list = _links.get(name);
			if(list == null)
				list = new ArrayList<>();
			list.add(l);
		}
		_links.put(name,list);
	}
	
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER,targetEntity=ContactInformation.class)
	@JoinColumn(name="region_id", referencedColumnName="ID")
	private List<ContactInformation> contacts;
	

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#getContacts()
	 */
	
	@XmlTransient
	public List<ContactInformation> getContacts() {
		return contacts;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#setContacts(java.util.List)
	 */
	
	public void setContacts(List<ContactInformation> contacts) {
		this.contacts = contacts;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#getId()
	 */
	
	public long getId() {
		return id;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#getCountry()
	 */
	
	public String getCountry() {
		return country;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#setCountry(java.lang.String)
	 */
	
	public void setCountry(String country) {
		this.country = country;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#getLatitude()
	 */
	
	public String getLatitude() {
		return latitude;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#setLatitude(java.lang.String)
	 */
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#getLongitude()
	 */
	
	public String getLongitude() {
		return longitude;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#setLongitude(java.lang.String)
	 */
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#getAdminUsername()
	 */
	
	public String getAdminUsername() {
		return adminUsername;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#setAdminUsername(java.lang.String)
	 */
	
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#getRegionStatus()
	 */
	
	@XmlTransient
	public RegionStatus getRegionStatus() {
		return regionStatus;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#setRegionStatus(org.fiteagle.api.IRegionStatus)
	 */
	
	public void setRegionStatus(RegionStatus registrationStatus) {
		
		this.regionStatus = registrationStatus;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#getNodeType()
	 */
	
	public String getNodeType() {
		return nodeType;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#setNodeType(java.lang.String)
	 */
	
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}


	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#setId(long)
	 */
	
	public void setId(long id) {
		this.id = id;
		if(null!= regionStatus){
			regionStatus.setRegion(id);
		}
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#addContact(org.fiteagle.xifi.api.model.ContactInformation)
	 */
	
	public void addContact(ContactInformation contactInfo) {
		if(this.contacts == null)
			contacts = new ArrayList<>();
			
		contacts.add(contactInfo);
		
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#addLinksWithId(java.lang.String)
	 */
	
	public void addLinksWithId(String uriInfo) {
		this.addLink("self",  new LinkInfo(trimURI(uriInfo)+ "/" + this.getId()));
		this.addLink("status" , new LinkInfo(trimURI(uriInfo)+ "/" + this.getId()+"/status/"));
		if(this.getContacts() != null && this.getContacts().size() > 0){
			for(ContactInformation contactInformation : this.getContacts()){
				this.addLink("contacts",  new LinkInfo(trimURI(uriInfo)+ "/" + this.getId()+"/contacts/" + contactInformation.getId()));
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see org.fiteagle.xifi.api.model.IRegion#addLinksWithoutId(java.lang.String)
	 */
	
	public void addLinksWithoutId(String uriInfo) {
		this.addLink("self",  new LinkInfo(trimURI(uriInfo)));
		this.addLink("status" , new LinkInfo(trimURI(uriInfo)+"/status/"));
		if(this.getContacts() != null && this.getContacts().size() > 0){
			for(ContactInformation contactInformation : this.getContacts()){
				this.addLink("contacts",  new LinkInfo(trimURI(uriInfo) + "/contacts/" + contactInformation.getId()));
			}
		}
		
	}
	
	

}
