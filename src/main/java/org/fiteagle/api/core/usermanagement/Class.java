package org.fiteagle.api.core.usermanagement;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name="CLASSES")
public class Class implements Serializable{

  private static final long serialVersionUID = 3183722562956503240L;
  
  @Id
  @GeneratedValue
  private long id;

  private String name;
  private String description;
  
  @JoinColumn(name="owner_username")
  @ManyToOne
  private User owner;
  
  @ManyToMany(fetch=FetchType.EAGER)
  private Set<User> participants;
  
  @ManyToMany(fetch=FetchType.EAGER)
  private Set<Node> nodes;

  @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy="owningClass")
  private Set<Task> tasks;
  
  public Class(String name, String description){
    this.name = name;
    this.description = description;
    this.participants = new HashSet<>();
    this.nodes = new HashSet<>();
    this.tasks = new HashSet<>();
  }
  
  protected Class(){
    this.participants = new HashSet<>();
    this.nodes = new HashSet<>();
    this.tasks = new HashSet<>();
  }
  
  @PreRemove
  private void deleteClassInUsersAndNodes(){
    for(User u : participants){
      u.removeClass(this);
    }
    for(Node n : this.nodes){
      n.removeClass(this);
    }
  }
  
  public long getId() {
    return id;
  }
  
  public void addParticipant(User user){
    user.addClass(this);
    this.participants.add(user);
  }
  
  public void removeParticipant(User user){
    this.participants.remove(user);
  }
  
  public Set<User> getParticipants() {
    return participants;
  }

  public void setParticipants(Set<User> participants) {
    this.participants = participants;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  
  public void addNode(Node node){
    this.nodes.add(node);
  }
  
  public Set<Node> getNodes() {
    return nodes;
  }

  public void setNodes(Set<Node> nodes) {
    this.nodes = nodes;
  }
  
  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }
  
  public void addTask(Task task){
    task.setOwningClass(this);
    this.tasks.add(task);
  }
  
  public void removeTask(Task task){
    this.tasks.remove(task);
  }

  public Set<Task> getTasks() {
    return tasks;
  }

  public void setTasks(Set<Task> tasks) {
    this.tasks = tasks;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Class other = (Class) obj;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (id != other.id)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Class [id=" + id + ", name=" + name + ", description=" + description + ", owner=" + owner
        + ", participants=" + participants + ", nodes=" + nodes + ", tasks=" + tasks + "]";
  }
  
}
