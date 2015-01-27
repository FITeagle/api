package org.fiteagle.api.core;

import info.openmultinet.ontology.vocabulary.Omn;
import info.openmultinet.ontology.vocabulary.Omn_federation;
import info.openmultinet.ontology.vocabulary.Omn_lifecycle;
import info.openmultinet.ontology.vocabulary.Omn_resource;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class MessageBusOntologyModel {
    
  public static final Property maxInstances;
  
  
//  public static final Property propertyFiteagleImplementedBy;
//  Property implementedBy = Omn_lifecycle.implementedBy;
  
//  public static final Property propertyFiteagleImplements;
//  Property implement = Omn_lifecycle.implements_;
  
//  public static final Property partOfGroup;
//  Property isReservOf = Omn.isReservationOf;
  
  
  public static final Property endTime;  
//  Property endTim = ‚
  
  
//  public static final Property reserveInstanceFrom; 
//  Property partOfGrou = Omn.isResourceOf;
  
//  public static final Property hasState;
//  Property hasStatei = Omn_lifecycle.hasState;
//  Resource ReservState = Omn_lifecycle.hasReservationState;
  
  
//  public static final Property hasReservation;
//  Property hasRes = Omn.hasReservation;
  
  
//  public static final Property partOfFederation;
//  Property partOfFed = Omn_federation.partOfFederation;
  
  public static final Resource classAdapter;
  
//  public static final Resource classTestbed;
//  Resource classTestb = Omn_federation.Federation;
  
//  public static final Resource classResource;
//  Resource resou = Omn.Resource;
  
//  public static final Resource classGroup;
//  Resource grou = Omn.Group;
  
//  public static final Resource classReservation;
//  Resource classRes = Omn.Reservation;
  
    static{
        Model fiteagle = OntologyModelUtil.loadModel("ontologies/fiteagle/ontology.ttl", "TURTLE");
        
        maxInstances = fiteagle.getProperty("http://open-multinet.info/ontology/omn#maxInstances");
//        partOfGroup = fiteagle.getProperty("http://open-multinet.info/ontology/omn#partOfGroup");
        endTime = fiteagle.getProperty("http://open-multinet.info/ontology/omn#endTime");
//        reserveInstanceFrom = fiteagle.getProperty("http://open-multinet.info/ontology/omn#reserveInstanceFrom");
//        hasState = fiteagle.getProperty("http://open-multinet.info/ontology/omn#hasState");
//        hasReservation = fiteagle.getProperty("http://open-multinet.info/ontology/omn#hasReservation");
//        propertyFiteagleImplementedBy = fiteagle.getProperty("http://open-multinet.info/ontology/omn#implementedBy");
//        propertyFiteagleImplements = fiteagle.getProperty("http://open-multinet.info/ontology/omn#implements");
        
//        partOfFederation = fiteagle.getProperty("http://open-multinet.info/ontology/omn#partOfFederation");
        
        classAdapter = fiteagle.getResource("http://open-multinet.info/ontology/omn#Adapter");
//        classTestbed = fiteagle.getResource("http://open-multinet.info/ontology/omn#Testbed");
//        classResource = fiteagle.getResource("http://open-multinet.info/ontology/omn#Resource");
//        classGroup = fiteagle.getResource("http://open-multinet.info/ontology/omn#Group");
//        classReservation = fiteagle.getResource("http://open-multinet.info/ontology/omn#Reservation");
    }
    
}
