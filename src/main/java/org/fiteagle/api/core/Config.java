package org.fiteagle.api.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import com.hp.hpl.jena.util.FileManager;

public class Config {
  
  private String fileName;
  private final static String home = System.getProperty("user.home");
  private final static String hostname = "Config.HOSTNAME";
  private final static String localhost = "localhost";
  
  private static Config instance;
  
  public static Config getInstance(){
    return createInstance(home.concat("/.fiteagle/fiteagle.properties"));
  }
  
  public static Config getInstance(String file){
    return createInstance(home.concat("/.fiteagle/").concat(file));
  }
  
  private static Config createInstance(String fileName){
    if(instance == null){
      instance = new Config();
      instance.fileName = fileName;
      return instance;
    } else{
      return instance;
    }
  }
  
  public void setDefaultProperty() {
    Properties property = new Properties();
    property.put(hostname, localhost);
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
  
  public void getAllProperties() {
    Properties property = readProperties();
    Enumeration<Object> enuKeys = property.keys();
    while (enuKeys.hasMoreElements()) {
      String key = (String) enuKeys.nextElement();
      String value = property.getProperty(key);
      System.out.println(key + ":" + value);
    }
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
  
  private Properties readProperties() {
    InputStream inputStream = FileManager.get().open(fileName);
    
    if (inputStream == null) {
      throw new IllegalArgumentException("Properties File: " + fileName + " is NOT found");
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
  
  private void writeProperties(Properties property) {
    try {
      File file = new File(fileName);
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
