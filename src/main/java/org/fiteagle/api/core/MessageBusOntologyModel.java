package org.fiteagle.api.core;

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class MessageBusOntologyModel {
    
    static{
        Model fiteagle = loadModel("ontologies/fiteagle/ontology.ttl", "TURTLE");
        
        propertyFiteagleCreate = fiteagle.getResource("http://open-multinet.info/ontology/omn#Create");
        propertyFiteagleInform = fiteagle.getResource("http://open-multinet.info/ontology/omn#Inform");
        propertyFiteagleConfigure = fiteagle.getResource("http://open-multinet.info/ontology/omn#Configure");
        propertyFiteagleDiscover = fiteagle.getResource("http://open-multinet.info/ontology/omn#Discover");
        propertyFiteagleRelease = fiteagle.getResource("http://open-multinet.info/ontology/omn#Release");
        propertyFiteagleRequest = fiteagle.getResource("http://open-multinet.info/ontology/omn#Request");
        
        propertyFiteagleImplementedBy = fiteagle.getProperty("http://open-multinet.info/ontology/omn#implementedBy");
        propertyFiteagleImplements = fiteagle.getProperty("http://open-multinet.info/ontology/omn#implements");
        propertyFiteagleContainsAdapter = fiteagle.getProperty("http://open-multinet.info/ontology/omn#containsAdapter");
        
        methodReleases = fiteagle.getProperty("http://open-multinet.info/ontology/omn#releases");
        methodRestores = fiteagle.getProperty("http://open-multinet.info/ontology/omn#restores");
        
        propertySparqlQuery = fiteagle.getProperty("http://open-multinet.info/ontology/omn#sparqlQuery");
        propertyJsonResult = fiteagle.getProperty("http://open-multinet.info/ontology/omn#jsonResult");
        propertyResultModelTTL = fiteagle.getProperty("http://open-multinet.info/ontology/omn#resultModelTTl");
        
        classAdapter = fiteagle.getResource("http://open-multinet.info/ontology/omn#Adapter");
        classTestbed = fiteagle.getResource("http://open-multinet.info/ontology/omn#Testbed");
        classResource = fiteagle.getResource("http://open-multinet.info/ontology/omn#Resource");
        
        internalMessage = fiteagle.getResource("http://fiteagleinternal#Message");
    }
    
    public static final Resource propertyFiteagleCreate;
    public static final Resource propertyFiteagleInform;
    public static final Resource propertyFiteagleConfigure;
    public static final Resource propertyFiteagleDiscover;
    public static final Resource propertyFiteagleRelease;
    public static final Resource propertyFiteagleRequest;
    
    public static final Property propertyFiteagleImplementedBy;
    public static final Property propertyFiteagleImplements;
    public static final Property propertyFiteagleContainsAdapter;
    public static final Property propertySparqlQuery;
    public static final Property propertyJsonResult;
    public static final Property propertyResultModelTTL;
    
    public static final Property methodReleases;
    public static final Property methodRestores;
    
    public static final Resource classAdapter;
    public static final Resource classTestbed;
    public static final Resource classResource;
    
    public static final Resource internalMessage;
        
    
    private static Model loadModel(String filename, String serialization) {
        Model fiteagle = ModelFactory.createDefaultModel();

        InputStream in2 = FileManager.get().open(filename);
        if (in2 == null) {
            throw new IllegalArgumentException("Ontology File: " + filename + " not found");
        }

        fiteagle.read(in2, null, serialization);

        return fiteagle;
    }
   
}
