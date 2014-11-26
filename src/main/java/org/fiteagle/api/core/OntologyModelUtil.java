package org.fiteagle.api.core;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import java.io.InputStream;

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

}
