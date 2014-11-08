package org.fiteagle.api.core.usermanagement;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="RESOURCES")
public class Resource implements Serializable{
  
  private static final long serialVersionUID = -8832566679043417291L;

  private String type;
  private int amount;
  
  @ManyToOne
  @JoinColumn(name="task_id")
  @JsonIgnore
  private Task task;

  @ManyToOne
  @JoinColumn(name="node_id")
  private Node node;
  
  @Id
  @GeneratedValue
  private long id;
    
  protected Resource() {
  }
  
  public Resource(String type, int amount, Node node) {
    this.setType(type);
    this.setAmount(amount);
    this.setNode(node);
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }
  
  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public Node getNode() {
    return node;
  }

  public void setNode(Node node) {
    this.node = node;
  } 
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Resource other = (Resource) obj;
    if (amount != other.amount)
      return false;
    if (node == null) {
      if (other.node != null)
        return false;
    } else if (!node.equals(other.node))
      return false;
    if (task == null) {
      if (other.task != null)
        return false;
    } else if (!task.equals(other.task))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Resource [type=" + type + ", amount=" + amount + ", node=" + node + ", id=" + id + "]";
  }

}
