package com.fitweb.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FitbitServiceAsync
{
  void getAppClientId(AsyncCallback<String> callback);

  void getUserProfile(String accessToken, AsyncCallback<String> callback);

  void getActivity(String accessToken, long date, AsyncCallback<String> callback);

  void getFriends(String accessToken, AsyncCallback<String> callback);

  void getSteps(String accessToken, String userId, long date, AsyncCallback<String> callback);
  
  
}
