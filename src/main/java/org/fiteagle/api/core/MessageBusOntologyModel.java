package org.fiteagle.api.core;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class MessageBusOntologyModel {
    
  public static final Property maxInstances;
  public static final Property propertyFiteagleImplementedBy;
  public static final Property propertyFiteagleImplements;
  
  public static final Property partOf;
  public static final Property endTime;
  public static final Property reserveInstanceFrom;
  public static final Property hasState;
  
  public static final Resource classAdapter;
  public static final Resource classTestbed;
  public static final Resource classResource;
  
  public static final Resource classGroup;
  public static final Resource classReservation;
  
    static{
        Model fiteagle = OntologyModelUtil.loadModel("ontologies/fiteagle/ontology.ttl", "TURTLE");
        
        maxInstances = fiteagle.getProperty("http://open-multinet.info/ontology/omn#maxInstances");
        partOf = fiteagle.getProperty("http://open-multinet.info/ontology/omn#partOf");
        endTime = fiteagle.getProperty("http://open-multinet.info/ontology/omn#endTime");
        reserveInstanceFrom = fiteagle.getProperty("http://open-multinet.info/ontology/omn#reserveInstanceFrom");
        hasState = fiteagle.getProperty("http://open-multinet.info/ontology/omn#hasState");
        propertyFiteagleImplementedBy = fiteagle.getProperty("http://open-multinet.info/ontology/omn#implementedBy");
        propertyFiteagleImplements = fiteagle.getProperty("http://open-multinet.info/ontology/omn#implements");
        
        classAdapter = fiteagle.getResource("http://open-multinet.info/ontology/omn#Adapter");
        classTestbed = fiteagle.getResource("http://open-multinet.info/ontology/omn#Testbed");
        classResource = fiteagle.getResource("http://open-multinet.info/ontology/omn#Resource");
        classGroup = fiteagle.getResource("http://open-multinet.info/ontology/omn#Group");
        classReservation = fiteagle.getResource("http://open-multinet.info/ontology/omn#Reservation");
    }
    
}
