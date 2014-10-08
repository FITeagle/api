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
        
        propertyFiteagleCreate = fiteagle.getResource("http://fiteagle.org/ontology#Create");
        propertyFiteagleInform = fiteagle.getResource("http://fiteagle.org/ontology#Inform");
        propertyFiteagleConfigure = fiteagle.getResource("http://fiteagle.org/ontology#Configure");
        propertyFiteagleDiscover = fiteagle.getResource("http://fiteagle.org/ontology#Discover");
        propertyFiteagleRelease = fiteagle.getResource("http://fiteagle.org/ontology#Release");
        propertyFiteagleRequest = fiteagle.getResource("http://fiteagle.org/ontology#Request");
        
        propertyFiteagleImplementedBy = fiteagle.getProperty("http://fiteagle.org/ontology#implementedBy");
        propertyFiteagleImplements = fiteagle.getProperty("http://fiteagle.org/ontology#implements");
        propertyFiteagleContainsAdapter = fiteagle.getProperty("http://fiteagle.org/ontology#containsAdapter");
        
        methodReleases = fiteagle.getProperty("http://fiteagle.org/ontology#releases");
        methodRestores = fiteagle.getProperty("http://fiteagle.org/ontology#restores");
        
        propertySparqlQuery = fiteagle.getProperty("http://fiteagle.org/ontology#sparqlQuery");
        propertyJsonResult = fiteagle.getProperty("http://fiteagle.org/ontology#jsonResult");
     
        
        classAdapter = fiteagle.getResource("http://fiteagle.org/ontology#Adapter");
        classTestbed = fiteagle.getResource("http://fiteagle.org/ontology#Testbed");
        classResource = fiteagle.getResource("http://fiteagle.org/ontology#Resource");
        
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

        // read the RDF/XML file
        fiteagle.read(in2, null, serialization);

        return fiteagle;
    }
   
}
