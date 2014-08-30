package org.fiteagle.api.core;

import java.io.InputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDFS;

public class MessageBusOntologyModel {
    
    static{
        Model fiteagle = loadModel("ontologies/fiteagle/ontology.ttl", "TURTLE");
        propertyFiteagleCreate = fiteagle.getProperty("http://fiteagle.org/ontology#Create");
        propertyFiteagleInform = fiteagle.getProperty("http://fiteagle.org/ontology#Inform");
        propertyFiteagleConfigure = fiteagle.getProperty("http://fiteagle.org/ontology#Configure");
        propertyFiteagleDiscover = fiteagle.getProperty("http://fiteagle.org/ontology#Discover");
        propertyFiteagleRelease = fiteagle.getProperty("http://fiteagle.org/ontology#Release");
        propertyFiteagleImplementedBy = fiteagle.getProperty("http://fiteagle.org/ontology#implementedBy");
        propertyFiteagleImplements = fiteagle.getProperty("http://fiteagle.org/ontology#implements");  
        
        methodReleases = fiteagle.getProperty("http://fiteagle.org/ontology#releases");  
    
    }
    
    public static final Property propertyFiteagleCreate;
    public static final Property propertyFiteagleInform;
    public static final Property propertyFiteagleConfigure;
    public static final Property propertyFiteagleDiscover;
    public static final Property propertyFiteagleRelease;
    public static final Property propertyFiteagleImplementedBy;
    public static final Property propertyFiteagleImplements;
    
    public static final Property methodReleases;
        
    
    public static Model loadModel(String filename, String serialization) {
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
