package org.fiteagle.api.core;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import java.io.InputStream;

public class OntologyModels {
    static{
       motorModel = loadModel("ontologies/fiteagle/ontology.ttl", "TURTLE");
    }

    private static Model motorModel;
    public static Model getMotorModel(){
        return motorModel;
    }

    public static Model loadModel(String filename, String serialization) {
        Model fiteagle = ModelFactory.createDefaultModel();

        InputStream in2 = FileManager.get().open(filename);
        if (in2 == null) {
            throw new IllegalArgumentException("Ontology File: " + filename + " not found");
        }

        fiteagle.read(in2, null, serialization);

        return fiteagle;
    }

}
