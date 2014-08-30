package org.fiteagle.api.core;

import java.io.StringWriter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class MessageBusMsgFactory {
    
    public static Model createMsgRelease(Model messageModel){
        Model rdfModel = ModelFactory.createDefaultModel();
        
        rdfModel.add(messageModel);
        rdfModel.setNsPrefixes(messageModel.getNsPrefixMap());

        com.hp.hpl.jena.rdf.model.Resource message = rdfModel.createResource("http://fiteagleinternal#Message");
        message.addProperty(RDF.type, MessageBusOntologyModel.propertyFiteagleRelease);

        setFiteaglePrefixes(rdfModel);
        
        return rdfModel;
    }
    
    public static Model createMsgInform(Model messageModel){
        Model rdfModel = ModelFactory.createDefaultModel();

        rdfModel.add(messageModel);
        rdfModel.setNsPrefixes(messageModel.getNsPrefixMap());

        com.hp.hpl.jena.rdf.model.Resource message = rdfModel.createResource("http://fiteagleinternal#Message");
        message.addProperty(RDF.type, MessageBusOntologyModel.propertyFiteagleInform);

        setFiteaglePrefixes(rdfModel);
        
        return rdfModel;
    }
    
    private static void setFiteaglePrefixes(Model rdfModel){
        rdfModel.setNsPrefix("", "http://fiteagleinternal#");
        rdfModel.setNsPrefix("fiteagle", "http://fiteagle.org/ontology#");        
    }
    
    public static String serializeModel(Model rdfModel){
        
        StringWriter writer = new StringWriter();

        rdfModel.write(writer, IMessageBus.SERIALIZATION_DEFAULT);
        
        return writer.toString();        
    }
    

}
