package org.fiteagle.api.core.usermanagement;

public interface PolicyEnforcementPoint {
  
  public abstract boolean isRequestAuthorized(String subjectUsername, String resourceUsername, String action, String role, Boolean isAuthenticated, Boolean requiresAdminRights, Boolean requiresTBOwnerRights);
  
}