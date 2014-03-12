package org.fiteagle.api.usermanagement;

public interface PolicyEnforcementPoint {
  
  public abstract boolean isRequestAuthorized(String subjectUsername, String resourceUsername, String action, String role, Boolean isAuthenticated, Boolean requiresAdminRights);
  
}