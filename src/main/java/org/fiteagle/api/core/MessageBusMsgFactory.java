package org.fiteagle.api.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

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

        if(messageModel != null){
          rdfModel.add(messageModel);
          rdfModel.setNsPrefixes(messageModel.getNsPrefixMap());
        }

        rdfModel.add(MessageBusOntologyModel.internalMessage, RDF.type, messageTypeProperty);

        return rdfModel;
    }

    public static String serializeModel(Model rdfModel) {
        StringWriter writer = new StringWriter();
        rdfModel.write(writer, IMessageBus.SERIALIZATION_DEFAULT);
        return writer.toString();
    }
    
  public static String serializeModel(Model rdfModel, String serialization) {
    StringWriter writer = new StringWriter();
    rdfModel.write(writer, serialization);
    return writer.toString();
  }

    public static Model parseSerializedModel(String modelString) throws RiotException {
        Model rdfModel = ModelFactory.createDefaultModel();

        InputStream is = new ByteArrayInputStream(modelString.getBytes(Charset.defaultCharset()));
        try{
            rdfModel.read(is, null, IMessageBus.SERIALIZATION_DEFAULT);
        } catch (RiotException e){
            System.err.println("Error parsing serialized model: " + modelString);
        }

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
    
    public static String getTTLResultModelFromSerializedModel(String serializedModel){
      Model resultModel = MessageBusMsgFactory.parseSerializedModel(serializedModel);
      Resource message = resultModel.getResource(MessageBusOntologyModel.internalMessage.getURI());
      String result = message.getProperty(MessageBusOntologyModel.propertyResultModelTTL).getString();
      return result;
    }
    
    public static String createSerializedSPARQLQueryModel(String query){
      Model requestModel = ModelFactory.createDefaultModel();
      Resource resource = requestModel.createResource("http://fiteagleinternal#Message");
      resource.addProperty(RDF.type, MessageBusOntologyModel.propertyFiteagleRequest);
      resource.addProperty(MessageBusOntologyModel.propertySparqlQuery, query);
      requestModel = MessageBusMsgFactory.createMsgRequest(requestModel);

      return MessageBusMsgFactory.serializeModel(requestModel); 
    }
    
    public static void setCommonPrefixes(Model model){
      model.removeNsPrefix("j.0");
      model.removeNsPrefix("j.1");
      model.removeNsPrefix("j.2");
      model.removeNsPrefix("j.3");
      model.removeNsPrefix("j.4");
      model.removeNsPrefix("j.5");
      
      model.setNsPrefix("wgs", "http://www.w3.org/2003/01/geo/wgs84_pos#");
      model.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
      model.setNsPrefix("omn", "http://open-multinet.info/ontology/omn#");
      model.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#");
      model.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
      model.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
      model.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
    }

}
