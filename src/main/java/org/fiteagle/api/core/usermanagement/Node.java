package org.fiteagle.api.core.usermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="NODES")
public class Node implements Serializable{
  
  private static final long serialVersionUID = -8243471509830325276L;

  public static Node defaultNode;
  
  @Id
  @GeneratedValue
  private long id;

  private String name;
  
  @ManyToMany(fetch=FetchType.EAGER)
  private List<Class> classes;
  
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy="node", fetch=FetchType.EAGER)
  private List<User> users;

  protected Node(){
  }
  
  public Node(String name){
    this.name = name;
    this.users = new ArrayList<User>();
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

  public void setClasses(List<Class> classes) {
    this.classes = classes;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
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
    return "Node [id=" + id + ", name=" + name + ", classes=" + classes + ", users=" + users + "]";
  }
  
}
