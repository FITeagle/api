package org.fiteagle.api.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.ws.rs.core.Response;

import org.apache.jena.riot.RiotException;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.vocabulary.RDF;

public class MessageUtil {
  
  private static Logger LOGGER = Logger.getLogger(MessageUtil.class.toString());
  
  public static Message createRDFMessage(Model rdfModel, String methodType, String serialization, String correlationID, JMSContext context) {
    final Message message = context.createMessage();
    try {
      message.setStringProperty(IMessageBus.METHOD_TYPE, methodType);
      message.setStringProperty(IMessageBus.SERIALIZATION, serialization);
      message.setStringProperty(IMessageBus.RDF, serializeModel(rdfModel, serialization));
      if(correlationID != null){
        message.setJMSCorrelationID(correlationID);
      }
      else{
        message.setJMSCorrelationID(UUID.randomUUID().toString());
      }
    } catch (JMSException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    return message;
  }
  
  public static Message createRDFMessage(String rdfModel, String methodType, String serialization, String correlationID, JMSContext context) {
    final Message message = context.createMessage();
    try {
      message.setStringProperty(IMessageBus.METHOD_TYPE, methodType);
      message.setStringProperty(IMessageBus.SERIALIZATION, serialization);
      message.setStringProperty(IMessageBus.RDF, rdfModel);
      if(correlationID != null){
        message.setJMSCorrelationID(correlationID);
      }
      else{
        message.setJMSCorrelationID(UUID.randomUUID().toString());
      }
    } catch (JMSException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    return message;
  }
  
  public static Message createErrorMessage(String errorMessage, String correlationID, JMSContext context) {
    final Message message = context.createMessage();
    try {
      message.setStringProperty(IMessageBus.METHOD_TYPE, IMessageBus.TYPE_INFORM);
      message.setStringProperty(IMessageBus.TYPE_ERROR, errorMessage);
      if(correlationID != null){
        message.setJMSCorrelationID(correlationID);
      }
      else{
        message.setJMSCorrelationID(UUID.randomUUID().toString());
      }
    } catch (JMSException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    return message;
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
    return parseSerializedModel(modelString, IMessageBus.SERIALIZATION_DEFAULT);
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
  
  public static String getRDFResult(final Message receivedMessage) {
    String result = null;
    if (receivedMessage != null) {
      try {
        result = receivedMessage.getStringProperty(IMessageBus.RDF);
      } catch (JMSException e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
      }
    }
    return result;
  }
  
  public static String getError(final Message receivedMessage) {
    String error = null;
    if (receivedMessage == null) {
      error = Response.Status.REQUEST_TIMEOUT.name();
    } else {
      try {
        error = receivedMessage.getStringProperty(IMessageBus.TYPE_ERROR);
      } catch (JMSException e) {
        LOGGER.log(Level.SEVERE, e.getMessage());
      }
    }
    return error;
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
  
  public static String createSerializedSPARQLQueryModel(String query, String serialization) {
    Model requestModel = ModelFactory.createDefaultModel();
    Resource resource = requestModel.createResource(MessageBusOntologyModel.internalMessage.getURI());
    resource.addProperty(RDF.type, MessageBusOntologyModel.propertyFiteagleRequest);
    resource.addProperty(MessageBusOntologyModel.requestType, IMessageBus.REQUEST_TYPE_SPARQL_QUERY);
    resource.addProperty(MessageBusOntologyModel.propertySparqlQuery, query);

    return MessageUtil.serializeModel(requestModel, serialization);
  }
  
  public static String createSerializedSPARQLQueryRestoresModel(String query, Resource adapter) {
    Model requestModel = ModelFactory.createDefaultModel();
    Resource resource = requestModel.createResource(MessageBusOntologyModel.internalMessage.getURI());
    resource.addProperty(RDF.type, MessageBusOntologyModel.propertyFiteagleRequest);
    resource.addProperty(MessageBusOntologyModel.requestType, IMessageBus.REQUEST_TYPE_SPARQL_QUERY);
    resource.addProperty(MessageBusOntologyModel.propertySparqlQuery, query);
    resource.addProperty(MessageBusOntologyModel.methodRestores, adapter);
    
    return MessageUtil.serializeModel(requestModel);
  }
  
  public static String parseResultSetToJson(ResultSet resultSet) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ResultSetFormatter.outputAsJSON(baos, resultSet);
    String jsonString = "";
    try {
      jsonString = baos.toString(Charset.defaultCharset().toString());
      baos.close();
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage());
    }
    return jsonString;
  }
  
  public static String getSPARQLQueryFromModel(Model model) throws ParsingException {
    String query = null;
    Resource message = model.getResource(MessageBusOntologyModel.internalMessage.getURI());
    Statement st = message.getProperty(MessageBusOntologyModel.propertySparqlQuery);
    if(st != null){
      query =  st.getObject().toString();
    }
    if (query == null || query.isEmpty()) {
      throw new ParsingException("SPARQL Query expected, but no sparql query found!");
    }
    return query;
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
  
 public static class ParsingException extends Exception {
    
    private static final long serialVersionUID = 8213556984621316215L;

    public ParsingException(String message){
      super(message);
    }
  }
  
}
