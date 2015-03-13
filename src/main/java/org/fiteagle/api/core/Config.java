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
  
  private  Path FILE_PATH;
  
  
  private static Logger LOGGER = Logger.getLogger(Config.class.toString());
  
  public Config(){
    this.FILE_PATH = IConfig.PROPERTIES_DIRECTORY.resolve(IConfig.FITEAGLE_FILE_NAME);
    checkFolder();
    setDefaultProperty();
  }
  
  public Config(String file_name){
    file_name = file_name.concat(IConfig.EXTENSION);
    this.FILE_PATH = IConfig.PROPERTIES_DIRECTORY.resolve(file_name);
    checkFolder();
  }
  
  public void creatPropertiesFile(){
    deletePropertiesFile();
    setDefaultProperty();
  }
  
  private void checkFolder(){
    
    if(Files.notExists(IConfig.PROPERTIES_DIRECTORY)){
      try {
        Files.createDirectory(IConfig.PROPERTIES_DIRECTORY);
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "The directory can't be created ", e);
        }
      }
  }
  
  public void setDefaultProperty() {
    File file = FILE_PATH.toFile();
    if(!file.exists()){
    Properties property = new Properties();
    property.put(IConfig.KEY_HOSTNAME, IConfig.DEFAULT_HOSTNAME);
    property.put(IConfig.LOCAL_NAMESPACE, IConfig.LOCAL_NAMESPACE_VALUE);
    property.put(IConfig.RESOURCE_NAMESPACE, IConfig.RESOURCE_NAMESPACE_VALUE);
    writeProperties(property);
    } else{
      Properties property = readProperties();
      if(!property.containsKey(IConfig.KEY_HOSTNAME)){
        property.put(IConfig.KEY_HOSTNAME, IConfig.DEFAULT_HOSTNAME);
      }
      if(!property.containsKey(IConfig.RESOURCE_NAMESPACE)){
        property.put(IConfig.RESOURCE_NAMESPACE, IConfig.RESOURCE_NAMESPACE_VALUE);
      }
      if(!property.containsKey(IConfig.LOCAL_NAMESPACE)){
        property.put(IConfig.LOCAL_NAMESPACE, IConfig.LOCAL_NAMESPACE_VALUE);
      }
      writeProperties(property);
    }
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
        LOGGER.log(Level.SEVERE, "The file can't be deleted", e);
      }
    }
  }
  
  private Properties readProperties() {
    InputStream inputStream = FileManager.get().open(FILE_PATH.toString());
    if (inputStream == null) {
      throw new IllegalArgumentException("Properties File: " + FILE_PATH.toString() + " is NOT found ");
    }
    Properties property = new Properties();
    try {
      property.load(inputStream);
      inputStream.close();
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "Properties file " + FILE_PATH.toString() + " can't be opened ", e);
    }
    return property;
  }
  
  private void writeProperties(Properties property) {
    try {
      File file = FILE_PATH.toFile();
      FileOutputStream fileOut = new FileOutputStream(file);
      property.store(fileOut, "");
      fileOut.close();
    } catch (FileNotFoundException e) {
      LOGGER.log(Level.SEVERE, "Properties file " + FILE_PATH.toString() + " is not found ", e);
      e.printStackTrace();
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "properties couldn't be stored in " + FILE_PATH.toString(), e);
    }
  }
}
