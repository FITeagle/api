package org.fiteagle.api.core.usermanagement;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.fiteagle.api.core.usermanagement.User.NotEnoughAttributesException;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="PUBLICKEYS", uniqueConstraints=@UniqueConstraint(columnNames={"owner_username", "description"}))
public class UserPublicKey implements Serializable{
  
  private static final long serialVersionUID = -374246341434116808L;
  
  private final static Pattern KEY_DESCRIPTION_PATTERN = Pattern.compile("[\\w|\\s]+");
  
  @Column(length=1024)
  private PublicKey publicKey;  
  
  private String description;
  
  @Id
  @JoinColumn(name="owner_username")
  @ManyToOne
  @JsonIgnore
  private User owner;
  
  @Id
  @Column(length=1024)
  private String publicKeyString; 
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date created;    
  
   
  protected UserPublicKey() {
  } 
  
  public UserPublicKey(PublicKey publicKey, String description, String publicKeyString) throws NotEnoughAttributesException {
    this.publicKey = publicKey;
    
    checkPublicKeyString(publicKeyString);
    this.publicKeyString = publicKeyString;
    
    checkDescription(description); 
    this.description = description;
  } 
  
  private void checkDescription(String description) throws NotEnoughAttributesException {
    if(description == null || description.length() == 0){
      throw new User.NotEnoughAttributesException("no description for public key given");
    }
    if(!KEY_DESCRIPTION_PATTERN.matcher(description).matches()){
      throw new User.InValidAttributeException("empty or invalid key description, only letters, numbers and whitespace is allowed: "+description);
    }
  }   
  
  private void checkPublicKeyString(String publicKeyString) throws NotEnoughAttributesException {
    if(publicKeyString == null || publicKeyString.length() == 0){
      throw new User.NotEnoughAttributesException("no publicKeyString given");
    }
  }   
  
  @PrePersist
  public void updateTimeStamps() {
    if(created == null) {
      created = new Date();
    }
  }
  
  @Override
  public String toString() {
    return "PublicKey [publicKey=" + publicKey + ", description=" + description + ", created=" + created + "]";
  }
  
  public PublicKey publicKey() {
    return publicKey;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    checkDescription(description);
    this.description = description;
  }

  public String getPublicKeyString() {
    return publicKeyString;
  }

  public Date getCreated() {
    return created;
  }

  protected User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((publicKey == null) ? 0 : publicKey.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    UserPublicKey other = (UserPublicKey) obj;
    if (publicKey == null) {
      if (other.publicKey != null)
        return false;
    } else if (!publicKey.equals(other.publicKey))
      return false;
    return true;
  }

}
