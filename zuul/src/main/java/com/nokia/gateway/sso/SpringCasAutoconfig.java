package com.nokia.gateway.sso;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cas")
public class SpringCasAutoconfig {  
    private String serverName;
    private String casServerUrlPrefix;
    private String casServerLoginUrl;
    private String singleSignOut;
    private String loginUserHandle;
    private String characterEncoding;
    private String encoding;
    private String skipUrls;
    
	public String getSkipUrls() {
		return skipUrls;
	}
	public void setSkipUrls(String skipUrls) {
		this.skipUrls = skipUrls;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}
	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}
	public String getCasServerLoginUrl() {
		return casServerLoginUrl;
	}
	public void setCasServerLoginUrl(String casServerLoginUrl) {
		this.casServerLoginUrl = casServerLoginUrl;
	}
	public String getSingleSignOut() {
		return singleSignOut;
	}
	public void setSingleSignOut(String singleSignOut) {
		this.singleSignOut = singleSignOut;
	}
	public String getLoginUserHandle() {
		return loginUserHandle;
	}
	public void setLoginUserHandle(String loginUserHandle) {
		this.loginUserHandle = loginUserHandle;
	}
	public String getCharacterEncoding() {
		return characterEncoding;
	}
	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
    
}  
