package org.fiteagle.api;

import java.util.List;

public interface IRegionDAO {

	public Region createRegion(Region region);

	public Region findRegion(long regionid);

	public List<? extends Region> findRegions(String country);

	public Region updateRegion(Region r);

	public void deleteRegion(long regionid);

	public RegionStatus findRegionStatusForId(long regionid);

	public RegionStatus updateRegionStatus(RegionStatus status);

	public ContactInformation addContactInforamtion(long regionid,
			ContactInformation contactInfo);

	public List<? extends ContactInformation> getContacts(long regionid, String type);

	public ContactInformation getContactInfo(long contactId);

	public ContactInformation updateContactInformation(long contactId,
			ContactInformation updated);

	public void deleteContact(long contactId);

}