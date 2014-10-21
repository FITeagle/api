package org.fiteagle.api.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageBusOntologyModelTest {
  
  @Test
  public void testLoadModel() {
    assertEquals(MessageBusOntologyModel.classResource.toString(), "http://open-multinet.info/ontology#Resource");
  }
  
}
