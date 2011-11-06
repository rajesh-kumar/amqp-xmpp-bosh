package org.raj.londonboy.bosh.connection;

import org.jivesoftware.smack.BOSHConfiguration;
import org.jivesoftware.smack.BOSHConnection;
import org.jivesoftware.smack.SASLAuthentication;

public class EjabberdBoshSource implements BoshSource{
	
	private boolean sslEnable;
	private String ejabberdHostName;
	private int ejabberdPort;
	private String ejabberdDomain;
	
	public EjabberdBoshSource(boolean sslEnable, String ejabberdHostName, int ejabberdPort, String ejabberdDomain){
		this.sslEnable = sslEnable;
		this.ejabberdHostName = ejabberdHostName;
		this.ejabberdPort = ejabberdPort;
		this.ejabberdDomain = ejabberdDomain;
	}

	@Override
	public BOSHConnection getConnection() {
		BOSHConfiguration boshConfiguration = new BOSHConfiguration(this.sslEnable, this.ejabberdHostName, this.ejabberdPort, "/http-bind", this.ejabberdDomain);
		SASLAuthentication.supportSASLMechanism("EXTERNAL", 0);
		return new BOSHConnection(boshConfiguration);
	}

}
