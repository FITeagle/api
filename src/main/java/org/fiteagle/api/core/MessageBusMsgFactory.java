package org.fiteagle.api.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.jena.riot.RiotException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
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

    private static Model getDefaultMessageModel(Model messageModel, Resource messageTypeProperty) {
        Model rdfModel = ModelFactory.createDefaultModel();

        rdfModel.add(messageModel);
        rdfModel.setNsPrefixes(messageModel.getNsPrefixMap());

        rdfModel.add(MessageBusOntologyModel.internalMessage, RDF.type, messageTypeProperty);

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

    public static Model parseSerializedModel(String modelString) throws RiotException {
        Model rdfModel = ModelFactory.createDefaultModel();

        InputStream is = new ByteArrayInputStream(modelString.getBytes());

        // read the RDF/XML file
        rdfModel.read(is, null, IMessageBus.SERIALIZATION_DEFAULT);

        return rdfModel;
    }
    
    public static boolean isMessageType(Model messageModel, Resource messageTypeProperty) {
        return messageModel.contains(null, RDF.type, messageTypeProperty);
    }
    
    public static Model getMessageRDFModel(Message jmsMessage) throws JMSException {
        // create an empty model
        Model messageModel = null;

        if (jmsMessage.getStringProperty(IMessageBus.RDF) != null) {

            String inputRDF = jmsMessage.getStringProperty(IMessageBus.RDF);
            
            try {
                messageModel = parseSerializedModel(inputRDF);
            } catch (RiotException e) {
                System.err.println("MDB Listener: Received invalid RDF");
            }
        }

        return messageModel;
    }


}
