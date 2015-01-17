package org.fiteagle.api.core;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class MessageBusOntologyModel {
    
  public static final Property maxInstances;
  public static final Property propertyFiteagleImplementedBy;
  public static final Property propertyFiteagleImplements;
  
  public static final Property partOfGroup;
  public static final Property endTime;
  public static final Property reserveInstanceFrom;
  public static final Property hasState;
  public static final Property hasReservation;
  
  public static final Property partOfFederation;
  
  public static final Resource classAdapter;
  public static final Resource classTestbed;
  public static final Resource classResource;
  
  public static final Resource classGroup;
  public static final Resource classReservation;
  
    static{
        Model fiteagle = OntologyModelUtil.loadModel("ontologies/fiteagle/ontology.ttl", "TURTLE");
        
        maxInstances = fiteagle.getProperty("http://open-multinet.info/ontology/omn#maxInstances");
        partOfGroup = fiteagle.getProperty("http://open-multinet.info/ontology/omn#partOfGroup");
        endTime = fiteagle.getProperty("http://open-multinet.info/ontology/omn#endTime");
        reserveInstanceFrom = fiteagle.getProperty("http://open-multinet.info/ontology/omn#reserveInstanceFrom");
        hasState = fiteagle.getProperty("http://open-multinet.info/ontology/omn#hasState");
        hasReservation = fiteagle.getProperty("http://open-multinet.info/ontology/omn#hasReservation");
        propertyFiteagleImplementedBy = fiteagle.getProperty("http://open-multinet.info/ontology/omn#implementedBy");
        propertyFiteagleImplements = fiteagle.getProperty("http://open-multinet.info/ontology/omn#implements");
        
        partOfFederation = fiteagle.getProperty("http://open-multinet.info/ontology/omn#partOfFederation");
        
        classAdapter = fiteagle.getResource("http://open-multinet.info/ontology/omn#Adapter");
        classTestbed = fiteagle.getResource("http://open-multinet.info/ontology/omn#Testbed");
        classResource = fiteagle.getResource("http://open-multinet.info/ontology/omn#Resource");
        classGroup = fiteagle.getResource("http://open-multinet.info/ontology/omn#Group");
        classReservation = fiteagle.getResource("http://open-multinet.info/ontology/omn#Reservation");
    }
    
}
