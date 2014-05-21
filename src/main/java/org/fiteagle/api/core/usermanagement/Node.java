package org.fiteagle.api.core.usermanagement;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="COURSES")
public class Node implements Serializable{
  
  private static final long serialVersionUID = -8243471509830325276L;

  @Id
  @GeneratedValue
  private long id;

  private String name;
  
  @ManyToMany(fetch=FetchType.EAGER)
  private List<Class> courses;

  protected Node(){
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

  public void setCourses(List<Class> courses) {
    this.courses = courses;
  }
  
}
