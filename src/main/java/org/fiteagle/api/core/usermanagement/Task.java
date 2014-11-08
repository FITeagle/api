package org.fiteagle.api.core.usermanagement;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
  
  @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy="task")
  private Set<Resource> resources;
   
  protected Task() {
    this.resources = new HashSet<>();
  } 
  
  public Task(String name, String description, Set<Resource> resources) {
    checkName(name);
    this.name = name;
    checkDescription(description); 
    this.description = description;
    this.resources = resources;
    if(this.resources == null){
      this.resources = new HashSet<>();
    }
    setTasksOfResources();
  }
  
  private void setTasksOfResources(){
    for(Resource resource: this.resources){
      resource.setTask(this);
    }
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
    return "Task [id=" + id + ", name=" + name + ", description=" + description + ", resources=" + resources + "]";
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

  public Set<Resource> getResources() {
    return resources;
  }

  public void setResources(Set<Resource> resources) {
    this.resources = resources;
    setTasksOfResources();
  }

}
