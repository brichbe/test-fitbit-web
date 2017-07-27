package com.fitweb.client;

import com.fitweb.shared.FitbitService;
import com.fitweb.shared.FitbitServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FitbitTestWebApp implements EntryPoint
{
  private final FitbitServiceAsync fitbitService = GWT.create(FitbitService.class);
  private String fitbitAccessToken;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad()
  {
    final String urlHash = Window.Location.getHash();
    if (urlHash == null || urlHash.isEmpty())
    {
      final String currentUrl = Window.Location.getHref();
      fitbitService.getAppClientId(new AsyncCallback<String>()
      {
        @Override
        public void onSuccess(String result)
        {
          Window.Location.assign("https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=" + result
              + "&redirect_uri=" + URL.encode(currentUrl)
              + "&scope=activity%20nutrition%20heartrate%20location%20profile%20settings%20sleep%20social%20weight");
        }

        @Override
        public void onFailure(Throwable caught)
        {
          Window.alert("Failed to get app client id: " + caught.getMessage());
        }
      });
      return;
    }

    GWT.log("Got URL Hash: " + urlHash);
    fitbitAccessToken = getFitbitAccessToken(urlHash.substring(1));
    GWT.log("Got access token: " + fitbitAccessToken);

    createUI();

  }

  public static native String getFitbitAccessToken(String urlHash)
  /*-{
		var fragmentQueryParameters = {};
		urlHash.replace(new RegExp("([^?=&]+)(=([^&]*))?", "g"), function($0,
				$1, $2, $3) {
			fragmentQueryParameters[$1] = $3;
		});
		return fragmentQueryParameters.access_token;
  }-*/;

  private void createUI()
  {
    final TabPanel mainPanel = new TabPanel();
    RootPanel.get("mainUiContainer").add(mainPanel);

    mainPanel.add(createUserProfileWidget(), "User Profile");
    mainPanel.add(createTodaysActivityWidget(), "Today's Activity");
    mainPanel.add(createFriendsWidget(), "Friends");
    mainPanel.add(createStepsWidget("-"), "My Steps");
    mainPanel.add(createStepsWidget("3FSBNP"), "Claire's Steps");
    mainPanel.selectTab(0);
  }

  private TextArea createUserProfileWidget()
  {
    final TextArea textArea = new TextArea();
    textArea.setSize("1200px", "500px");

    fitbitService.getUserProfile(fitbitAccessToken, new AsyncCallback<String>()
    {
      @Override
      public void onSuccess(String result)
      {
        textArea.setText(JsonUtils.stringify(JsonUtils.safeEval(result), "    "));
      }

      @Override
      public void onFailure(Throwable caught)
      {
        Window.alert("Failed to get user profile: " + caught.getMessage());
      }
    });

    return textArea;
  }

  private TextArea createTodaysActivityWidget()
  {
    final TextArea textArea = new TextArea();
    textArea.setSize("1200px", "500px");
    fitbitService.getActivity(fitbitAccessToken, System.currentTimeMillis(), new AsyncCallback<String>()
    {
      @Override
      public void onSuccess(String result)
      {
        textArea.setText(JsonUtils.stringify(JsonUtils.safeEval(result), "    "));
      }

      @Override
      public void onFailure(Throwable caught)
      {
        Window.alert("Failed to get today's activity: " + caught.getMessage());
      }
    });

    return textArea;
  }

  private TextArea createFriendsWidget()
  {
    final TextArea textArea = new TextArea();
    textArea.setSize("1200px", "500px");
    fitbitService.getFriends(fitbitAccessToken, new AsyncCallback<String>()
    {
      @Override
      public void onSuccess(String result)
      {
        textArea.setText(JsonUtils.stringify(JsonUtils.safeEval(result), "    "));
      }

      @Override
      public void onFailure(Throwable caught)
      {
        Window.alert("Failed to get friends: " + caught.getMessage());
      }
    });

    return textArea;
  }

  private TextArea createStepsWidget(String userId)
  {
    final TextArea textArea = new TextArea();
    textArea.setSize("1200px", "500px");
    fitbitService.getSteps(fitbitAccessToken, userId, System.currentTimeMillis(), new AsyncCallback<String>()
    {
      @Override
      public void onSuccess(String result)
      {
        textArea.setText(JsonUtils.stringify(JsonUtils.safeEval(result), "    "));
      }

      @Override
      public void onFailure(Throwable caught)
      {
        Window.alert("Failed to get steps: " + caught.getMessage());
      }
    });

    return textArea;
  }
}
