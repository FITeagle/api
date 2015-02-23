package org.fiteagle.api.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.nio.file.Path;
import java.nio.file.Files;

import com.hp.hpl.jena.util.FileManager;

public class Config {
  
  private static Path FILE_PATH;
  
  private static Config instance;
  
  private static Logger LOGGER = Logger.getLogger(Config.class.toString());
  
  public static Config getInstance(){
    FILE_PATH = IConfig.PROPERTIES_DIRECTORY.resolve(IConfig.FITEAGLE_FILE_NAME);
    return createInstance();
  }
  
  public static Config getInstance(String file){
    file = file.concat(IConfig.EXTENSION);
    FILE_PATH = IConfig.PROPERTIES_DIRECTORY.resolve(file);
    return createInstance();
  }
  
  private static Config createInstance(){
    checkFolderAndFile();
    if(instance == null){
      instance = new Config();
      return instance;
    } else{
      return instance;
    }
  }
  
  private static void checkFolderAndFile() {
    if(Files.notExists(IConfig.PROPERTIES_DIRECTORY)){
        try {
          Files.createDirectory(IConfig.PROPERTIES_DIRECTORY);
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }
    File file = FILE_PATH.toFile();
    if(!file.exists()){
      setDefaultProperty();
    }
  }
  
  public static void setDefaultProperty() {
    Properties property = new Properties();
    property.put(IConfig.HOSTNAME, IConfig.DEFAULT_HOST);
    property.put(IConfig.LOCAL_NAMESPACE, "urn:host:".concat(IConfig.DEFAULT_HOST).concat(":"));
    writeProperties(property);
  }
  
  public void setNewProperty(String propertyKey, String propertyValue) {
    Properties property = readProperties();
    property.put(propertyKey, propertyValue);
    writeProperties(property);
  }
  
  public String getProperty(String propertyKey) {
    String propertyValue = null;
    propertyValue = readProperties().getProperty(propertyKey);
    if (propertyValue == null) {
      throw new IllegalArgumentException("there is no value for the property " + propertyKey);
    }
    return propertyValue;
  }
  
  public HashMap<String, String> getAllProperties() {
    Properties property = readProperties();
    Enumeration<Object> enuKeys = property.keys();
    HashMap<String, String> allProperties = new HashMap<>();
    while (enuKeys.hasMoreElements()) {
      String key = (String) enuKeys.nextElement();
      String value = property.getProperty(key);
      allProperties.put(key, value);
      LOGGER.log(Level.INFO, key + ":" + value);
    }
    return allProperties;
  }
  
  public void deleteProperty(String propertyKey) {
    Properties property = readProperties();
    property.remove(propertyKey);
    writeProperties(property);
  }
  
  public void updateProperty(String propertyKey, String propertyValue) {
    Properties property = readProperties();
    property.put(propertyKey, propertyValue);
    writeProperties(property);
  }
  
  public void deletePropertiesFile(){
    if(Files.exists(FILE_PATH)){
      try {
        Files.delete(FILE_PATH);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
  
  private Properties readProperties() {
    InputStream inputStream = FileManager.get().open(FILE_PATH.toString());
    if (inputStream == null) {
      throw new IllegalArgumentException("Properties File: " + FILE_PATH.toString() + " is NOT found");
    }
    Properties property = new Properties();
    try {
      property.load(inputStream);
      inputStream.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return property;
  }
  
  private static void writeProperties(Properties property) {
    try {
      File file = FILE_PATH.toFile();
      FileOutputStream fileOut = new FileOutputStream(file);
      property.store(fileOut, "");
      fileOut.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
