package org.fiteagle.api.core.usermanagement;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="NODES")
public class Node implements Serializable{
  
  private static final long serialVersionUID = -8243471509830325276L;

  public static Node defaultNode;
  
  @Id
  @GeneratedValue
  private long id;

  private String name;
  
  @JsonIgnore
  @ManyToMany(mappedBy="nodes")
  private Set<Class> classes;
  
  @JsonIgnore
  @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy="node")
  private Set<User> users;

  @JsonIgnore
  @OneToMany(orphanRemoval = true, mappedBy="node")
  private Set<Resource> resources;
  
  protected Node(){
    this.users = new HashSet<>();
    this.classes = new HashSet<>();
    this.resources = new HashSet<>();
  }
  
  public Node(String name){
    this.name = name;
    this.users = new HashSet<>();
    this.classes = new HashSet<>();
    this.resources = new HashSet<>();
  }
  
  public static Node createDefaultNode(){
    Node node = new Node("TU Berlin");
    defaultNode = node;
    return node;    
  }
  
  public void addUser(User user){
    this.users.add(user);
    user.setNode(this);
  }
  
  public void removeUser(User user){
    this.users.remove(user);
  }
  
  public void addClass(Class targetClass){
    this.classes.add(targetClass);
  }
  
  public void removeClass(Class targetClass){
    this.classes.remove(targetClass);
  }
  
  public void addResource(Resource resource){
    this.resources.add(resource);
  }
  
  public void removeResource(Resource resource){
    this.resources.remove(resource);
  }
  
  public static void setDefaultNode(Node node){
    defaultNode = node;
  }
  
  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Class> getClasses(){
    return this.classes;
  }
  
  public void setClasses(Set<Class> classes) {
    this.classes = classes;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Node other = (Node) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Node [id=" + id + ", name=" + name + "]";
  }
  
}
