package com.fitweb.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("fitbit")
public interface FitbitService extends RemoteService
{
  String getAppClientId();

  String getUserProfile(String accessToken) throws Exception;
  
  String getActivity(String accessToken, long date) throws Exception;
  
  String getFriends(String accessToken) throws Exception;
  
  String getSteps(String accessToken, String userId, long date) throws Exception;
}
