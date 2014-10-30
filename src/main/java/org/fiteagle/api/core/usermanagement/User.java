package org.fiteagle.api.core.usermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

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
  
  private String password;
  @JsonView(VIEW.INTERNAL.class)
  private String passwordHash;
  @JsonView(VIEW.INTERNAL.class)
  private String passwordSalt;
  
  public static class VIEW {
    public static class PUBLIC{};
    static class INTERNAL extends PUBLIC{};
  }
  
  @JoinColumn(name="node_id")
  @ManyToOne
  private Node node;
  
  @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy="owner")
  private List<UserPublicKey> publicKeys;
  
  @JsonIgnore
  @ManyToMany(mappedBy="participants")
  private List<Class> joinedClasses;

  @JsonIgnore
  @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy="owner")
  private List<Class> ownedClasses;
  
  private final static Pattern USERNAME_PATTERN = Pattern.compile("[\\w|-|@|.]{3,200}");
  private final static Pattern EMAIL_PATTERN = Pattern.compile("[^@]+@{1}[^@]+\\.+[^@]+");
  private final static int MINIMUM_FIRST_AND_LASTNAME_LENGTH = 2;
  private final static int MINIMUM_AFFILITAION_LENGTH = 2;
  
  protected User(){
  }
  
  public User(String username, String firstName, String lastName, String email, String affiliation, Node node, String passwordHash, String passwordSalt, List<UserPublicKey> publicKeys){
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.affiliation = affiliation;
    this.role = Role.STUDENT;
    this.node = node;
    this.passwordSalt = passwordSalt;
    this.passwordHash = passwordHash;
    this.publicKeys = publicKeys;
    if(publicKeys == null){
      this.publicKeys = new ArrayList<>();
    }
    this.joinedClasses = new ArrayList<>();
    this.ownedClasses = new ArrayList<>();
    checkAttributes();
  }
  
  public static User createDefaultUser(String username, String passwordHash, String passwordSalt) {
    return new User(username, "default", "default", createDefaultEmail(username), "default", Node.defaultNode, passwordHash, passwordSalt, new ArrayList<UserPublicKey>());
  }
  
  private static String createDefaultEmail(String username) {
	  if(!EMAIL_PATTERN.matcher(username).matches()){
		  return username+"@test.org"; 
	  }else {
  		return username;
  	}
  }

  public static User createAdminUser(String username, String passwordHash, String passwordSalt) throws NotEnoughAttributesException, InValidAttributeException{
    User admin = new User(username, "default", "default", "default", "default", Node.defaultNode, passwordHash, passwordSalt, null);
    admin.setRole(Role.FEDERATION_ADMIN);
    return admin;
  }
  
  private void setOwnersOfPublicKeys(){
    if(this.publicKeys != null){
      for(UserPublicKey publicKey : this.publicKeys){
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
  public void updateTimeStampsAndPublicKeys() {
    setOwnersOfPublicKeys();
    lastModified = new Date();
    if(created == null) {
      created = new Date();
    }
  }
  
  @PreRemove
  private void deleteParticipantInClassesAndNode(){
    for(Class joinedClass : joinedClasses){
      joinedClass.removeParticipant(this);
    }
    this.node.removeUser(this);
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
        + ", node=" + node + ", publicKeys=" + publicKeys + "]";
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

  public Node getNode() {
    return node;
  }

  public void setNode(Node node) {
    this.node = node;
  }

  public String getPasswordHash(){
    return passwordHash;
  }
  
  public void setPasswordHash(String hash){
    this.passwordHash = hash;
  }
  
  public String getPasswordSalt(){
    return passwordSalt;
  }
  
  public void setPasswordSalt(String salt){
    this.passwordSalt = salt;
  }
  
  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonProperty
  protected void setPassword(String password) {
    this.password = password;
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

  public List<UserPublicKey> getPublicKeys() {
    return publicKeys;
  }

  public List<Class> getOwnedClasses() {
    return ownedClasses;
  }
  
  public List<Class> getJoinedClasses() {
    return joinedClasses;
  }

  public void setJoinedClasses(List<Class> targetClasses) {
    this.joinedClasses = targetClasses;
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
	  this.ownedClasses.add(targetClass);
  }
  
  public void removeOwnedClass(Class targetClass){
	  this.ownedClasses.remove(targetClass);
  }
  
  protected void addClass(Class targetClass){
    if(!this.joinedClasses.contains(targetClass)){
      this.joinedClasses.add(targetClass);
    }
  }
  
  protected void removeClass(Class targetClass){
    this.joinedClasses.remove(targetClass);
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

