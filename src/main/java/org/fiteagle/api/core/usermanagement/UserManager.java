package org.fiteagle.api.core.usermanagement;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.fiteagle.api.core.IMessageBus;
import org.fiteagle.api.core.usermanagement.User.PublicKeyNotFoundException;
import org.fiteagle.api.core.usermanagement.User.Role;

public interface UserManager {
  
  public static final String GET_ALL_USERS = "getAllUsers";
  public static final String GET_USER = "getUser";
  public static final String ADD_USER = "addUser";
  public static final String UPDATE_USER = "updateUser";
  public static final String DELETE_USER = "deleteUser";
  public static final String SET_ROLE = "setRole";
  public static final String ADD_PUBLIC_KEY = "addPublicKey";
  public static final String DELETE_PUBLIC_KEY = "deletePublicKey";
  public static final String RENAME_PUBLIC_KEY = "renamePublicKey";
  public static final String CREATE_USER_CERT_AND_PRIVATE_KEY = "createUserCertAndPrivateKey";
  public static final String GET_USER_CERT_FOR_PUBLIC_KEY = "getUserCertForPublicKey";
  public static final String GET_ALL_CLASSES_FROM_USER = "getAllClassesFromUser";
  public static final String GET_ALL_CLASSES_OWNED_BY_USER = "getAllClassesOwnedByUser";
  public static final String SIGN_UP_FOR_CLASS = "signUpForClass";
  public static final String LEAVE_CLASS = "leaveClass";
  public static final String GET_CLASS = "getClass";
  public static final String ADD_CLASS = "addClass";
  public static final String DELETE_CLASS = "deleteClass";
  public static final String ADD_TASK = "addTask";
  public static final String REMOVE_TASK = "removeTask";
  public static final String GET_ALL_CLASSES = "getAllClasses";
  public static final String VERIFY_CREDENTIALS = "verifyCredentials";
  public static final String GET_ALL_NODES = "getAllNodes";
  public static final String ADD_NODE = "addNode";

  public static final String TYPE_PARAMETER_USERNAME = "parameter_username";
  public static final String TYPE_PARAMETER_FIRSTNAME = "parameter_firstname";
  public static final String TYPE_PARAMETER_LASTNAME = "parameter_lastname";
  public static final String TYPE_PARAMETER_EMAIL = "parameter_email";
  public static final String TYPE_PARAMETER_AFFILIATION = "parameter_affiliation";
  public static final String TYPE_PARAMETER_PASSWORD = "parameter_password";
  public static final String TYPE_PARAMETER_PUBLIC_KEY = "parameter_public_key";
  public static final String TYPE_PARAMETER_PUBLIC_KEY_DESCRIPTION = "parameter_public_key_description";
  public static final String TYPE_PARAMETER_PUBLIC_KEY_DESCRIPTION_NEW = "parameter_public_key_description_new";
  public static final String TYPE_PARAMETER_PUBLIC_KEYS = "parameter_public_keys";
  public static final String TYPE_PARAMETER_ROLE = "parameter_role";
  public static final String TYPE_PARAMETER_PASSPHRASE = "parameter_passphrase";
  public static final String TYPE_PARAMETER_CLASS_ID = "parameter_class_id";
  public static final String TYPE_PARAMETER_TASK_ID = "parameter_task_id";
  
  public static final String TYPE_PARAMETER_USER_JSON = "parameter_user_json";
  public static final String TYPE_PARAMETER_CLASS_JSON = "parameter_class_json";
  public static final String TYPE_PARAMETER_NODE_JSON = "parameter_node_json";
  public static final String TYPE_PARAMETER_TASK_JSON = "parameter_task_json";
  
  public static final String TARGET = "usermanagement";
  
  public static final String MESSAGE_FILTER = IMessageBus.TYPE_TARGET + " = '" + TARGET + "'";
  
  public abstract void addUser(User user);
  
  public abstract User getUser(User user) ;
  
  public abstract User getUser(String username);
  
  public abstract void deleteUser(User user);
  
  public abstract void deleteUser(String username);
  
  public abstract void updateUser(String username, String firstName, String lastName, String email, String affiliation,
      String password, List<UserPublicKey> publicKeys);
  
  public abstract void setRole(String username, Role role);
  
  public abstract void addKey(String username, UserPublicKey publicKey);
  
  public abstract void deleteKey(String username, String description);
  
  public abstract void renameKey(String username, String description, String newDescription);
  
  public abstract String createUserKeyPairAndCertificate(String username, String passphrase) throws Exception;
  
  public abstract String createUserCertificateForPublicKey(String username, String description) throws Exception, PublicKeyNotFoundException;
  
  public abstract boolean verifyCredentials(String username, String password) throws NoSuchAlgorithmException, IOException, UserNotFoundException;
  
  public abstract List<User> getAllUsers();
  
  public static class UserNotFoundException extends RuntimeException {    
    private static final long serialVersionUID = 2315125279537534064L;
    
    public UserNotFoundException(){
      super("no user with this username could be found in the database");
    }
  }
  

  public static class DuplicateUsernameException extends RuntimeException {
    private static final long serialVersionUID = -7242105025265481986L;   
    
    public DuplicateUsernameException(){
      super("another user with the same username already exists in the database");
    }
  }
  
  public static class DuplicateEmailException extends RuntimeException {
    private static final long serialVersionUID = 5986984055945876422L;
    
    public DuplicateEmailException(){
      super("another user with the same email already exists in the database");
    }
  }
  
  public static class DuplicatePublicKeyException extends RuntimeException {
    private static final long serialVersionUID = -8863826365649086008L; 
    
    public DuplicatePublicKeyException(){
      super("either this public key already exists or another public key with the same description already exists for this user");
    }
  }
  
  public static class DuplicateClassException extends RuntimeException {
	private static final long serialVersionUID = 7701360611614483760L;

	public DuplicateClassException(){
      super("this class has been already added to the user");
    }
  }
  
  public abstract Class addClass(String ownerUsername, Class targetClass);
  
  public abstract Class getClass(Class targetClass);
  
  public abstract Class getClass(long id);
  
  public abstract void deleteClass(Class targetClass);
  
  public abstract void deleteClass(long id);
  
  public abstract Task addTask(long id, Task task);
  
  public abstract void removeTask(long classId, long taskId);
  
  public abstract void addParticipant(long id, String username);
  
  public abstract void removeParticipant(long id, String username);

  public abstract List<Class> getAllClassesFromUser(String username);

  public abstract List<Class> getAllClassesOwnedByUser(String username);
  
  public abstract List<Class> getAllClasses();
  
  public static class FiteagleClassNotFoundException extends RuntimeException {    
    
    private static final long serialVersionUID = 5952413074712514371L;

    public FiteagleClassNotFoundException(){
      super("no class with this id could be found in the database");
    }
  }
  
 public static class TaskNotFoundException extends RuntimeException {    
    
    private static final long serialVersionUID = 5952413074712514371L;

    public TaskNotFoundException(){
      super("no task with this id could be found in the database");
    }
  }
  
  public Node addNode(Node node);
  
  public Node getNode(long id) throws NodeNotFoundException;
  
  public List<Node> getAllNodes();

  public static class NodeNotFoundException extends RuntimeException {    
    
    private static final long serialVersionUID = 3977041585248360400L;

    public NodeNotFoundException(){
      super("no node with this id could be found in the database");
    }
  }
  
  public abstract void deleteAllEntries();

}