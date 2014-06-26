package ca.ubc.partyallthetime.bcmixr.server;

import ca.ubc.partyallthetime.bcmixr.shared.Alcohol;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;

public class DAO extends DAOBase
{
    static {
    	ObjectifyService.register(Alcohol.class);
    //	ObjectifyService.register(TimeOfLastParse.class);
    	ObjectifyService.register(AlcoholDisplayNames.class);

    }

    



}