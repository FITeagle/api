package org.fiteagle.api.usermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name="COURSES")
public class Course implements Serializable{

  private static final long serialVersionUID = 3183722562956503240L;
  
  @Id
  @GeneratedValue
  private long id;

  private String name;
  private String description;
  
  @ManyToMany(fetch=FetchType.EAGER)
  private List<User> participants;

  public Course(String name, String description){
    this.name = name;
    this.description = description;
    this.participants = new ArrayList<User>();
  }
  
  protected Course(){
  }
  
  @PreRemove
  private void deleteCourseInUsers(){
    for(User u : participants){
      u.removeCourse(this);
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
    Course other = (Course) obj;
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
