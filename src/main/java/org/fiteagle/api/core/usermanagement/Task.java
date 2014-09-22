package org.fiteagle.api.core.usermanagement;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.fiteagle.api.core.usermanagement.User.NotEnoughAttributesException;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TASKS")
public class Task implements Serializable{
  
  private final static Pattern TASK_NAME_PATTERN = Pattern.compile("[\\w|\\s]+");
  
  private static final long serialVersionUID = 845421382194272905L;

  @Id
  @GeneratedValue
  private long id;
  
  private String name;
  private String description;
  
  @JoinColumn(name="class_id")
  @ManyToOne
  @JsonIgnore
  private Class owningClass;
   
  protected Task() {
  } 
  
  public Task(String name, String description) {
    checkName(name);
    this.name = name;
    checkDescription(description); 
    this.description = description;
  } 
  
  private void checkDescription(String description) throws NotEnoughAttributesException {
    if(description == null || description.length() == 0){
      throw new User.NotEnoughAttributesException("no description for task given");
    }
  }   
  
  private void checkName(String name) throws NotEnoughAttributesException {
    if(name == null || name.length() == 0){
      throw new User.NotEnoughAttributesException("no name for task given");
    }
    if(!TASK_NAME_PATTERN.matcher(name).matches()){
      throw new User.InValidAttributeException("empty or invalid task name, only letters, numbers and whitespace is allowed: "+name);
    }
  }   
  
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    checkDescription(description);
    this.description = description;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    checkName(name);
    this.name = name;
  }

  public Class getOwningClass() {
    return owningClass;
  }

  public void setOwningClass(Class owningClass) {
    this.owningClass = owningClass;
  }

  @Override
  public String toString() {
    return "Task [id=" + id + ", name=" + name + ", description=" + description + "]";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Task other = (Task) obj;
    if (id != other.id)
      return false;
    if (owningClass == null) {
      if (other.owningClass != null)
        return false;
    } else if (!owningClass.equals(other.owningClass))
      return false;
    return true;
  }

}
