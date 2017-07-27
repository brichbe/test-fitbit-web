package com.fitweb.server;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fitweb.shared.FitbitService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class FitbitServiceImpl extends RemoteServiceServlet implements FitbitService
{
  private static final long serialVersionUID = 8115191250957728332L;
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  private static CloseableHttpClient openAMHttpClient = HttpClients.createSystem();

  public FitbitServiceImpl()
  {
    super();
  }

  @Override
  public String getAppClientId()
  {
    return "228PFT";
  }

  @Override
  public String getUserProfile(String accessToken) throws Exception
  {
    HttpGet action = new HttpGet("https://api.fitbit.com/1/user/-/profile.json");
    action.setHeader("Content-Type", "application/json");
    action.setHeader("Authorization", "Bearer " + accessToken);
    CloseableHttpResponse httpResponse = openAMHttpClient.execute(action);
    String response = EntityUtils.toString(httpResponse.getEntity());
    return response;
  }

  @Override
  public String getActivity(String accessToken, long date) throws Exception
  {

    HttpGet action = new HttpGet("https://api.fitbit.com/1/user/-/activities/date/" + DATE_FORMAT.format(new Date(date))
        + ".json");
    action.setHeader("Content-Type", "application/json");
    action.setHeader("Authorization", "Bearer " + accessToken);
    CloseableHttpResponse httpResponse = openAMHttpClient.execute(action);
    String response = EntityUtils.toString(httpResponse.getEntity());
    return response;
  }

  @Override
  public String getFriends(String accessToken) throws Exception
  {
    HttpGet action = new HttpGet("https://api.fitbit.com/1/user/-/friends.json");
    action.setHeader("Content-Type", "application/json");
    action.setHeader("Authorization", "Bearer " + accessToken);
    CloseableHttpResponse httpResponse = openAMHttpClient.execute(action);
    String response = EntityUtils.toString(httpResponse.getEntity());
    return response;
  }

  @Override
  public String getSteps(String accessToken, String userId, long date) throws Exception
  {
    HttpGet action = new HttpGet("https://api.fitbit.com/1/user/" + userId + "/activities/steps/date/"
        + DATE_FORMAT.format(new Date(date)) + "/7d.json");
    action.setHeader("Content-Type", "application/json");
    action.setHeader("Authorization", "Bearer " + accessToken);
    CloseableHttpResponse httpResponse = openAMHttpClient.execute(action);
    String response = EntityUtils.toString(httpResponse.getEntity());
    return response;
  }
}
