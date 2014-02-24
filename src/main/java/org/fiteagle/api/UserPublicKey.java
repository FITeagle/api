package org.fiteagle.api;

import java.util.Date;
import java.security.PublicKey;

public interface UserPublicKey {
  
  public abstract String toString();
  
  public abstract PublicKey getPublicKey();
  
  public abstract String getDescription();
  
  public abstract void setDescription(String description);
  
  public abstract String getPublicKeyString();
  
  public abstract Date getCreated();
  
  public abstract User getOwner();
  
  public abstract void setOwner(User owner);
  
  public abstract int hashCode();
  
  public abstract boolean equals(Object obj);
  
}