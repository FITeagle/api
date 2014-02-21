package org.fiteagle.api;

import java.util.List;

import org.fiteagle.api.User.Role;

public interface UserDB {
  
  public abstract void add(User user);
  
  public abstract User get(User user) ;
  
  public abstract User get(String username);
  
  public abstract void delete(User user);
  
  public abstract void delete(String username);
  
  public abstract void update(String username, String firstName, String lastName, String email, String affiliation,
      String password, List<UserPublicKey> publicKeys);
  
  public abstract void setRole(String username, Role role);
  
  public abstract void addKey(String username, UserPublicKey publicKey);
  
  public abstract void deleteKey(String username, String description);
  
  public abstract void renameKey(String username, String description, String newDescription);
  
  public abstract List<User> getAllUsers();
  
}