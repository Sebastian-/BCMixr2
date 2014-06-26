package ca.ubc.partyallthetime.bcmixr.client;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;

public class MapPopUp extends PopupPanel{
	private FlowPanel mapPanel;

	public MapPopUp(MapWidget map){
		super(true);
		mapPanel = new FlowPanel();
		mapPanel.setSize("458px", "370px");
		mapPanel.add(map);
		setWidget(mapPanel);
		setGlassEnabled(true);
	}
	
	
	

	
}
