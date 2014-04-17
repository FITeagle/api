package org.fiteagle.api.usermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
  private List<User> participants;
  
  @ManyToMany(mappedBy="courses", fetch=FetchType.EAGER)
  private List<Node> nodes;

  public Class(String name, String description){
    this.name = name;
    this.description = description;
    this.participants = new ArrayList<User>();
    this.nodes = new ArrayList<Node>();
  }
  
  protected Class(){
  }
  
  @PreRemove
  private void deleteClassInUsers(){
    for(User u : participants){
      u.removeClass(this);
    }
  }
  
  public long getId() {
    return id;
  }
  
  public void addParticipant(User user){
    user.addCourse(this);
    this.participants.add(user);
  }
  
  public void removeParticipant(User user){
    this.participants.remove(user);
  }
  
  public List<User> getParticipants() {
    return participants;
  }

  public void setParticipants(List<User> participants) {
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
  
  public List<Node> getNodes() {
    return nodes;
  }

  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
  }
  
  public User owner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }
  
  @Override
  public String toString() {
    return "Course [id=" + id + ", name=" + name + ", description=" + description + ", participants=" + participants
        + "]";
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
  
}
