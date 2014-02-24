package org.fiteagle.api;

import java.util.List;

public interface IRegionDAO {

	public IRegion createRegion(IRegion region);

	public IRegion findRegion(long regionid);

	public List<? extends IRegion> findRegions(String country);

	public IRegion updateRegion(IRegion r);

	public void deleteRegion(long regionid);

	public IRegionStatus findRegionStatusForId(long regionid);

	public IRegionStatus updateRegionStatus(IRegionStatus status);

	public IContactInformation addContactInforamtion(long regionid,
			IContactInformation contactInfo);

	public List<? extends IContactInformation> getContacts(long regionid, String type);

	public IContactInformation getContactInfo(long contactId);

	public IContactInformation updateContactInformation(long contactId,
			IContactInformation updated);

	public void deleteContact(long contactId);

}