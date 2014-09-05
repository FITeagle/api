package org.fiteagle.api.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.vocabulary.RDF;

public class MessageBusMsgFactory {

    public static Model createMsgCreate(Model messageModel) {
        return getDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleCreate);
    }

    public static Model createMsgDiscover(Model messageModel) {
        return getDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleDiscover);
    }

    public static Model createMsgRequest(Model messageModel) {
        return getDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleRequest);
    }

    public static Model createMsgRelease(Model messageModel) {
        return getDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleRelease);
    }

    public static Model createMsgInform(Model messageModel) {
        return getDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleInform);
    }
    
    public static Model createMsgConfigure(Model messageModel) {
        return getDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleConfigure);
    }

    private static Model getDefaultMessageModel(Model messageModel, Property messageTypeProperty) {
        Model rdfModel = ModelFactory.createDefaultModel();

        rdfModel.add(messageModel);
        rdfModel.setNsPrefixes(messageModel.getNsPrefixMap());

        com.hp.hpl.jena.rdf.model.Resource message = rdfModel.createResource("http://fiteagleinternal#Message");
        message.addProperty(RDF.type, messageTypeProperty);

        setFiteaglePrefixes(rdfModel);

        return rdfModel;
    }

    private static void setFiteaglePrefixes(Model rdfModel) {
        rdfModel.setNsPrefix("", "http://fiteagleinternal#");
        rdfModel.setNsPrefix("fiteagle", "http://fiteagle.org/ontology#");
    }

    public static String serializeModel(Model rdfModel) {

        StringWriter writer = new StringWriter();

        rdfModel.write(writer, IMessageBus.SERIALIZATION_DEFAULT);

        return writer.toString();
    }

    public static Model parseSerializedModel(String modelString) {
        Model rdfModel = ModelFactory.createDefaultModel();

        InputStream is = new ByteArrayInputStream(modelString.getBytes());

        // read the RDF/XML file
        rdfModel.read(is, null, IMessageBus.SERIALIZATION_DEFAULT);

        return rdfModel;
    }

}
