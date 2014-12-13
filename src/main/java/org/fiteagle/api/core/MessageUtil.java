package org.fiteagle.api.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.ws.rs.core.Response;

import org.apache.jena.riot.RiotException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

public class MessageUtil {
  
  private static Logger LOGGER = Logger.getLogger(MessageUtil.class.toString());
  
  public static Model createMsgCreate(Model messageModel) {
    return createDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleCreate);
  }
  
  public static Model createMsgDiscover(Model messageModel) {
    return createDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleDiscover);
  }
  
  public static Model createMsgRequest(Model messageModel) {
    return createDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleRequest);
  }
  
  public static Model createMsgRelease(Model messageModel) {
    return createDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleRelease);
  }
  
  public static Model createMsgInform(Model messageModel) {
    return createDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleInform);
  }
  
  public static Model createMsgConfigure(Model messageModel) {
    return createDefaultMessageModel(messageModel, MessageBusOntologyModel.propertyFiteagleConfigure);
  }

  public static Message createRDFMessage(final Model rdfModel, final String methodType, final String serialization, JMSContext context) {
    final Message message = context.createMessage();
    try {
      message.setStringProperty(IMessageBus.METHOD_TYPE, methodType);
      message.setStringProperty(IMessageBus.SERIALIZATION, serialization);
      message.setStringProperty(IMessageBus.RDF, serializeModel(rdfModel));
      message.setJMSCorrelationID(UUID.randomUUID().toString());
    } catch (JMSException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    return message;
  }
  
  public static Message createRDFMessage(final String rdfModel, final String methodType, final String serialization, JMSContext context) {
    final Message message = context.createMessage();
    try {
      message.setStringProperty(IMessageBus.METHOD_TYPE, methodType);
      message.setStringProperty(IMessageBus.SERIALIZATION, serialization);
      message.setStringProperty(IMessageBus.RDF, rdfModel);
      message.setJMSCorrelationID(UUID.randomUUID().toString());
    } catch (JMSException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    return message;
  }
  
  private static Model createDefaultMessageModel(Model messageModel, Resource messageTypeProperty) {
    Model rdfModel = ModelFactory.createDefaultModel();
    
    if (messageModel != null) {
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
    String serializedModel = writer.toString();
    if (serialization.equals(IMessageBus.SERIALIZATION_RDFXML)) {
      String[] splitted = serializedModel.split("\n");
      for (int i = 0; i < splitted.length; i++) {
        if (splitted[i].replace(" ", "").replace("\t", "").startsWith("xmlns:j.")) {
          serializedModel = serializedModel.replace(splitted[i] + "\n", "");
        }
      }
    }
    return serializedModel;
  }
  
  public static Model parseSerializedModel(String modelString) throws RiotException {
    Model rdfModel = ModelFactory.createDefaultModel();
    
    InputStream is = new ByteArrayInputStream(modelString.getBytes(Charset.defaultCharset()));
    rdfModel.read(is, null, IMessageBus.SERIALIZATION_DEFAULT);
    
    return rdfModel;
  }
  
  public static Model parseSerializedModel(String modelString, String serialization) throws RiotException {
    Model rdfModel = ModelFactory.createDefaultModel();
    
    InputStream is = new ByteArrayInputStream(modelString.getBytes(Charset.defaultCharset()));
    try {
      rdfModel.read(is, null, serialization);
    } catch (RiotException e) {
      LOGGER.log(Level.SEVERE, "Error parsing serialized model: " + modelString);
    }
    
    return rdfModel;
  }
  
  public static boolean isMessageType(Model messageModel, Resource messageTypeProperty) {
    return messageModel.contains(null, RDF.type, messageTypeProperty);
  }
  
  public static Model getRDFResultModel(final Message receivedMessage) {
    String rdfResult = getRDFResult(receivedMessage);
    
    Model resultModel = null;
    try {
      resultModel = parseSerializedModel(rdfResult);
    } catch (RiotException e) {
    }
    return resultModel;
  }
  
  public static String getRDFResult(final Message receivedMessage) {
    String result = null;
    if (receivedMessage == null) {
      result = Response.Status.REQUEST_TIMEOUT.name();
    } else {
      try {
        result = receivedMessage.getStringProperty(IMessageBus.RDF);
        if (result == null) {
          result = receivedMessage.getStringProperty(IMessageBus.TYPE_ERROR);
        }
      } catch (JMSException e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
      }
    }
    return result;
  }
  
  public static Message waitForResult(Message requestMessage, JMSContext context, Topic topic) {
    String filter = "";
    try {
      filter = "JMSCorrelationID='" + requestMessage.getJMSCorrelationID() + "'";
    } catch (JMSException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    final Message rcvMessage = context.createConsumer(topic, filter).receive(IMessageBus.SHORT_TIMEOUT);
    return rcvMessage;
  }
  
  public static String getTTLResultModelFromSerializedModel(String serializedModel) {
    Model resultModel = MessageUtil.parseSerializedModel(serializedModel);
    Resource message = resultModel.getResource(MessageBusOntologyModel.internalMessage.getURI());
    String result = message.getProperty(MessageBusOntologyModel.propertyResultModelTTL).getString();
    return result;
  }
  
  public static String createSerializedSPARQLQueryModel(String query) {
    Model requestModel = ModelFactory.createDefaultModel();
    Resource resource = requestModel.createResource(MessageBusOntologyModel.internalMessage.getURI());
    resource.addProperty(RDF.type, MessageBusOntologyModel.propertyFiteagleRequest);
    resource.addProperty(MessageBusOntologyModel.propertySparqlQuery, query);
    requestModel = MessageUtil.createMsgRequest(requestModel);
    
    return MessageUtil.serializeModel(requestModel);
  }
  
  public static void setCommonPrefixes(Model model) {
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
