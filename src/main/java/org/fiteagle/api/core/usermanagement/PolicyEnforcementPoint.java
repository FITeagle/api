package org.fiteagle.api.core.usermanagement;

import org.fiteagle.api.core.IMessageBus;

public interface PolicyEnforcementPoint {
  
  public static final String IS_REQUEST_AUTHORIZED = "isRequestAuthorized";

  public static final String TYPE_PARAMETER_SUBJECT_USERNAME = "parameter_subject_username";
  public static final String TYPE_PARAMETER_RESOURCE = "parameter_resource_username";
  public static final String TYPE_PARAMETER_ACTION = "parameter_action";
  
  public static final String TARGET = "policyEnforcementPoint";
  public static final String MESSAGE_FILTER = IMessageBus.METHOD_TARGET + " = '" + TARGET + "'";
  
  public abstract boolean isRequestAuthorized(String subjectUsername, String resource, String action);
  
}