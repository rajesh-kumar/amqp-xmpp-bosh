package org.raj.londonboy.bosh.service;

import org.raj.londonboy.bosh.dao.BoshDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PubSubBoshManager {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(PubSubBoshManager.class);
    
    private BoshDao boshDao;
    
    public PubSubBoshManager(BoshDao boshDao){
    	this.boshDao = boshDao;
    }

	/**
	 * @param args
	 */
	public void broadcastMessage(String xmlMessage) throws Exception {
		
		LOGGER.info("\n========================================================="
                + "\n                                                         "
                + "\n          Publishing Messages to BOSH Service!    "
                + "\n=========================================================" );
		
		this.boshDao.publishMessage("entry", xmlMessage);
		
	}

}