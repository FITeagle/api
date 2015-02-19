package org.fiteagle.api.core;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class OntologyModelUtil {
  
  public static Model loadModel(String filename, String serialization) {
    Model model = ModelFactory.createDefaultModel();
    
    InputStream in = FileManager.get().open(filename);
    if (in == null) {
      throw new IllegalArgumentException("Ontology File: " + filename + " not found");
    }
    
    model.read(in, null, serialization);
    
    return model;
  }
  
  public static String getLocalNamespace() {
    //@todo: move this to a org.fiteagle.api.core.Config.get(Config.HOSTNAME) or similar
    String hostname = "localhost";
    /*
    try {     
      hostname = "localhost"; //InetAddress.getLocalHost().getCanonicalHostName();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    */
    return "http://".concat(hostname).concat("/");
  }
  
  public static String[] getNamespaceAndLocalname(String uri, Map<String, String> prefixes) throws IllegalArgumentException {
    String[] namespaceAndLocalname = new String[2];
    if(uri.startsWith("http://")){
      if(uri.contains("#")){
        String[] splitted = uri.split("#");
        if(splitted.length != 2){
          throw new IllegalArgumentException("Unsupported URI: "+uri);
        }
        else{
          String namespace = splitted[0]+"#";
          namespaceAndLocalname[0] = namespace;
          namespaceAndLocalname[1] = splitted[1];
        }
      }
      else{
        String namespace = uri.substring(0, uri.lastIndexOf("/")+1);
        String localname = uri.substring(uri.lastIndexOf("/")+1, uri.length());
        if(namespace.isEmpty() || localname.isEmpty()){
          throw new IllegalArgumentException("Unsupported URI: "+uri);
        }
        namespaceAndLocalname[0] = namespace;
        namespaceAndLocalname[1] = localname;
      }
    }
    else if(uri.contains(":")){
      String[] splitted = uri.split(":");
      if(splitted.length != 2){
        throw new IllegalArgumentException("Unsupported URI: "+uri);
      }
      else{
        String prefix = splitted[0];
        String namespace = prefixes.get(prefix);
        if(namespace == null){
          throw new IllegalArgumentException("No namespace found for prefix: "+prefix);
        }
        namespaceAndLocalname[0] = namespace;
        namespaceAndLocalname[1] = splitted[1];
      }
    }
    else{
      throw new IllegalArgumentException("Unsupported instance URI: "+uri);
    }
    return namespaceAndLocalname;
  }
  
}
