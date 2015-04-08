package org.fiteagle.api.core;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class MessageBusOntologyModel {
    
  public static final Property maxInstances;
  public static final Property endTime;  
  public static final Resource classAdapter;
  public static final Property hasPublicKey;
  public static final Property hasUserName;
  
    static{
        Model fiteagle = OntologyModelUtil.loadModel("ontologies/fiteagle/ontology.ttl", "TURTLE");
        
        maxInstances = fiteagle.getProperty("http://open-multinet.info/ontology/omn#maxInstances");
        endTime = fiteagle.getProperty("http://open-multinet.info/ontology/omn#endTime");
        classAdapter = fiteagle.getResource("http://open-multinet.info/ontology/omn#Adapter");
        hasPublicKey = fiteagle.getProperty("http://open-multinet.info/ontology/omn#hasPublicKey");
        hasUserName = fiteagle.getProperty("http://open-multinet.info/ontology/omn#hasUserName");
    }
    
}
