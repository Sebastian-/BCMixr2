package ca.ubc.partyallthetime.bcmixr.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

final class LoginPopupPanel extends PopupPanel {
	
    public LoginPopupPanel() {
        // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
        // If this is set, the panel closes itself automatically when the user
        // clicks outside of it.
        super(true);

        // PopupPanel is a SimplePanel, so you have to set it's widget property to
        // whatever you want its contents to be.
        setWidget(new Label("To create new recipes, please sign in to BC Mixr using your Google account."));
        
        
        
      }

}
