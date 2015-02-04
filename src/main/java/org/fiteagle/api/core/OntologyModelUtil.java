package org.fiteagle.api.core;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
    String hostname = null;
    try {
      hostname = InetAddress.getLocalHost().getCanonicalHostName();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return "http://".concat(hostname).concat("/");
  }
  
}
