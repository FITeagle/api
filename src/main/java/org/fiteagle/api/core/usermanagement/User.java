package org.fiteagle.api.core.usermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="USERS", uniqueConstraints=@UniqueConstraint(name="EMAIL", columnNames={"email"}))
public class User implements Serializable{

  private static final long serialVersionUID = -8580256972066486588L;
  
  public enum Role {
    FEDERATION_ADMIN, NODE_ADMIN, CLASSOWNER, STUDENT 
  }
  
  @Id
  @Column(updatable = false)
  private String username;
  
  private String firstName;
  private String lastName;
  private String email;
  private String affiliation;
  private Role role;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Column(updatable = false)
  private Date created;
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModified;
  
  private String passwordHash;
  private String passwordSalt;
  
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="owner", fetch=FetchType.EAGER)
  private List<UserPublicKey> publicKeys;
  
  @ManyToMany(mappedBy="participants", fetch=FetchType.EAGER)
  private List<Class> classes;
  
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="owner", fetch=FetchType.EAGER)
  private List<Class> classesOwned;
  
  private final static Pattern USERNAME_PATTERN = Pattern.compile("[\\w|-|@|.]{3,200}");
  private final static Pattern EMAIL_PATTERN = Pattern.compile("[^@]+@{1}[^@]+\\.+[^@]+");
  private final static int MINIMUM_FIRST_AND_LASTNAME_LENGTH = 2;
  private final static int MINIMUM_AFFILITAION_LENGTH = 2;
  
  protected User(){
  }
  
  public User(String username, String firstName, String lastName, String email, String affiliation, String passwordHash, String passwordSalt, List<UserPublicKey> publicKeys){
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.affiliation = affiliation;
    this.role = Role.STUDENT;
    this.passwordSalt = passwordSalt;
    this.passwordHash = passwordHash;
    this.publicKeys = publicKeys;
    if(publicKeys == null){
      this.publicKeys = new ArrayList<>();
    }
    this.classes = new ArrayList<>();
    this.classesOwned = new ArrayList<>();
    setOwners(publicKeys);
    checkAttributes();
  }
  
  public static User createDefaultUser(String username, String passwordHash, String passwordSalt) {
    return new User(username, "default", "default", createDefaultEmail(username), "default", passwordHash, passwordSalt, new ArrayList<UserPublicKey>());
  }
  
  private static String createDefaultEmail(String username2) {
	  if(!EMAIL_PATTERN.matcher(username2).matches()){
		  return username2+"@test.org"; 
	  }else {
		return username2;
	}
}

public static User createAdminUser(String username, String passwordHash, String passwordSalt) throws NotEnoughAttributesException, InValidAttributeException{
    User admin = new User(username, "default", "default", "default", "default", passwordHash, passwordSalt, null);
    admin.setRole(Role.FEDERATION_ADMIN);
    return admin;
  }
  
  private void setOwners(List<UserPublicKey> publicKeys){
    if(publicKeys != null){
      for(UserPublicKey publicKey : publicKeys){
        publicKey.setOwner(this);
      }
    }
  }
  
  private void checkAttributes() throws NotEnoughAttributesException, InValidAttributeException {  
    if(username == null){
      throw new NotEnoughAttributesException("no username given");
    }
    if(firstName == null){
      this.firstName = "default";
    }
    if(lastName == null){
      this.lastName = "default";
    }
    if(email == null){
      this.email = "default";
    }
    if(affiliation == null){
      this.affiliation = "default";
    }  
    if(passwordHash == null || passwordSalt == null || passwordHash.equals("") || passwordSalt.equals("")){
        throw new NotEnoughAttributesException("no password given or password too short");
    }
    if(!USERNAME_PATTERN.matcher(username).matches()){
      throw new InValidAttributeException("invalid username, only letters, numbers, \"@\", \".\", \"_\", and \"-\" is allowed and the username has to be from 3 to 200 characters long");
    }
    if(firstName.length() < MINIMUM_FIRST_AND_LASTNAME_LENGTH){
      throw new InValidAttributeException("firstName too short");
    }
    if(lastName.length() < MINIMUM_FIRST_AND_LASTNAME_LENGTH){
      throw new InValidAttributeException("lastName too short");
    }
    if(!EMAIL_PATTERN.matcher(email).matches() && !email.equals("default")){
      throw new InValidAttributeException("an email needs to contain \"@\" and \".\"");
    }
    if(affiliation.length() < MINIMUM_AFFILITAION_LENGTH){
      throw new InValidAttributeException("affiliation too short");
    }
  }
  
  @PreUpdate
  @PrePersist
  public void updateTimeStamps() {
    lastModified = new Date();
    if(created == null) {
      created = new Date();
    }
  }
  
  @PreRemove
  private void deleteParticipantInCourses(){
    for(Class course : classes){
      course.removeParticipant(this);
    }
  }
  
  @SuppressWarnings("unchecked")
  public void updateAttributes(String firstName, String lastName, String email, String affiliation, String passwordHash, String passwordSalt, List<UserPublicKey> publicKeys) {
    if(firstName != null){
     this.firstName = firstName;
    }
    if(lastName != null){
      this.lastName = lastName;
    }
    if(publicKeys != null && publicKeys.size() != 0){
      this.publicKeys = (List<UserPublicKey>)(List<?>) publicKeys;
    }
    if(email != null){
      this.email = email;
    }
    if(affiliation != null){
      this.affiliation = affiliation;
    }
    if(passwordHash != null && passwordSalt != null && !passwordHash.equals("") && !passwordSalt.equals("")){
      this.passwordHash = passwordHash;
      this.passwordSalt = passwordSalt;
    }
    
    checkAttributes();      
  } 
  
  public void addPublicKey(UserPublicKey publicKey){
    publicKey.setOwner(this);
    this.publicKeys.add((UserPublicKey) publicKey);
  }
  
  public void deletePublicKey(String description){
    UserPublicKey keyToRemove = null;
    for(UserPublicKey key : this.publicKeys){
      if(key.getDescription().equals(description)){
        keyToRemove = key;
      }
    }    
    if(keyToRemove != null){
      this.publicKeys.remove(keyToRemove);
    }
  }
  
  public void renamePublicKey(String description, String newDescription){
    for(UserPublicKey key : publicKeys){
      if(key.getDescription().equals(description)){
        key.setDescription(newDescription);
        return;
      }
    }   
    throw new PublicKeyNotFoundException();
  }
  
  public UserPublicKey getPublicKey(String description){
    for(UserPublicKey key : publicKeys){
      if(key.getDescription().equals(description)){
        return key;
      }
    }
    throw new PublicKeyNotFoundException();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    User other = (User) obj;
    if (affiliation == null) {
      if (other.affiliation != null)
        return false;
    } else if (!affiliation.equals(other.affiliation))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (passwordHash == null) {
      if (other.passwordHash != null)
        return false;
    } else if (!passwordHash.equals(other.passwordHash))
      return false;
    if (passwordSalt == null) {
      if (other.passwordSalt != null)
        return false;
    } else if (!passwordSalt.equals(other.passwordSalt))
      return false;
    if (publicKeys == null) {
      if (other.publicKeys != null)
        return false;
    } else if (!publicKeys.containsAll(other.publicKeys) || publicKeys.size() != other.publicKeys.size())
      return false;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "User [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
        + ", affiliation=" + affiliation + ", role=" + role + ", created=" + created + ", lastModified=" + lastModified
        + ", publicKeys=" + publicKeys + "]";
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    if(username == null || !USERNAME_PATTERN.matcher(username).matches()){
      throw new InValidAttributeException("invalid username, only letters, numbers, \"@\", \".\", \"_\" and \"-\" is allowed and the username has to be from 3 to 200 characters long");
    }
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    if(email == null || (!EMAIL_PATTERN.matcher(email).matches() && !email.equals("default"))){
      throw new InValidAttributeException("an email needs to contain \"@\" and \".\"");
    }
    this.email = email;
  }

  public String getAffiliation() {
    return affiliation;
  }

  public String hash(){
    return passwordHash;
  }
  
  public String salt(){
    return passwordSalt;
  }
  
  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    if(role != null){
      this.role = role;
    }
  }

  public Date getCreated() {
    return created;
  }

  public Date getLastModified() {
    return lastModified;
  }

  @SuppressWarnings("unchecked")
  public List<UserPublicKey> getPublicKeys() {
    return (List<UserPublicKey>)(List<?>) publicKeys;
  }
 
  public List<Class> classesOwned() {
    return classesOwned;
  }

  public List<Class> joinedClasses() {
    return classes;
  }

  public void setCourses(List<Class> courses) {
    this.classes = courses;
  }

  public boolean hasKeyWithDescription(String description){
    for(UserPublicKey key: publicKeys){
      if(key.getDescription().equals(description)){
        return true;
      }
    }
    return false;
  }
  
  public void addOwnedClass(Class targetClass){
	  targetClass.setOwner(this);
	  this.classesOwned.add(targetClass);
  }
  
  public void removeOwnedClass(Class targetClass){
	  this.classesOwned.remove(targetClass);
  }
  
  protected void addClass(Class targetClass){
    this.classes.add(targetClass);
  }
  
  protected void removeClass(Class targetClass){
    this.classes.remove(targetClass);
  }
  
  public class PublicKeyNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4906415519200726744L;  
    
    public PublicKeyNotFoundException(){
      super("no public key with this description could be found in the database");
    }
  }

  public static class NotEnoughAttributesException extends RuntimeException {
    private static final long serialVersionUID = -8279867183643310351L;
    
    public NotEnoughAttributesException(){
      super();
    }
    
    public NotEnoughAttributesException(String message){
      super(message);
    }
  }

  public static class InValidAttributeException extends RuntimeException {
    private static final long serialVersionUID = -1299121776233955847L;
    
    public InValidAttributeException(){
      super();
    }
    
    public InValidAttributeException(String message){
      super(message);      
    }
  }

}