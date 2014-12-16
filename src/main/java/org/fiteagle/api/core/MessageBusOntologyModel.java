package org.fiteagle.api.core;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class MessageBusOntologyModel {
    
  public static final Resource propertyFiteagleCreate;
  public static final Resource propertyFiteagleInform;
  public static final Resource propertyFiteagleConfigure;
  public static final Resource propertyFiteagleDiscover;
  public static final Resource propertyFiteagleRelease;
  public static final Resource propertyFiteagleRequest;
  
  public static final Property maxInstances;
  public static final Property propertyFiteagleImplementedBy;
  public static final Property propertyFiteagleImplements;
  public static final Property propertyFiteagleContainsAdapter;
  public static final Property propertySparqlQuery;
  
  public static final Property methodReleases;
  public static final Property methodRestores;
  
  public static final Resource classAdapter;
  public static final Resource classTestbed;
  public static final Resource classResource;
  
  public static final Resource internalMessage;
  
    static{
        Model fiteagle = OntologyModelUtil.loadModel("ontologies/fiteagle/ontology.ttl", "TURTLE");
        
        propertyFiteagleCreate = fiteagle.getResource("http://open-multinet.info/ontology/omn#Create");
        propertyFiteagleInform = fiteagle.getResource("http://open-multinet.info/ontology/omn#Inform");
        propertyFiteagleConfigure = fiteagle.getResource("http://open-multinet.info/ontology/omn#Configure");
        propertyFiteagleDiscover = fiteagle.getResource("http://open-multinet.info/ontology/omn#Discover");
        propertyFiteagleRelease = fiteagle.getResource("http://open-multinet.info/ontology/omn#Release");
        propertyFiteagleRequest = fiteagle.getResource("http://open-multinet.info/ontology/omn#Request");
        
        maxInstances = fiteagle.getProperty("http://open-multinet.info/ontology/omn#maxInstances");
        propertyFiteagleImplementedBy = fiteagle.getProperty("http://open-multinet.info/ontology/omn#implementedBy");
        propertyFiteagleImplements = fiteagle.getProperty("http://open-multinet.info/ontology/omn#implements");
        propertyFiteagleContainsAdapter = fiteagle.getProperty("http://open-multinet.info/ontology/omn#containsAdapter");
        
        methodReleases = fiteagle.getProperty("http://open-multinet.info/ontology/omn#releases");
        methodRestores = fiteagle.getProperty("http://open-multinet.info/ontology/omn#restores");
        
        propertySparqlQuery = fiteagle.getProperty("http://open-multinet.info/ontology/omn#sparqlQuery");
        
        classAdapter = fiteagle.getResource("http://open-multinet.info/ontology/omn#Adapter");
        classTestbed = fiteagle.getResource("http://open-multinet.info/ontology/omn#Testbed");
        classResource = fiteagle.getResource("http://open-multinet.info/ontology/omn#Resource");
        
        internalMessage = fiteagle.getResource("http://fiteagleinternal#Message");
    }
    
}
