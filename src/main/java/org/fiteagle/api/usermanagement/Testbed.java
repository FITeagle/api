package org.fiteagle.api.usermanagement;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="COURSES")
public class Testbed {
  
  @Id
  @GeneratedValue
  private long id;

  private String name;
  
  @ManyToMany(fetch=FetchType.EAGER)
  private List<Course> courses;

  protected Testbed(){
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

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }
  
}
