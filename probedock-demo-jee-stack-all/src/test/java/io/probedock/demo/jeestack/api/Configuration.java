/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.probedock.demo.jeestack.api;

import io.probedock.api.test.client.IApiTestClientConfiguration;
import java.util.ResourceBundle;

/**
 *
 * @author Laurent Prevost <laurent.prevost@lotaris.com>
 */
public class Configuration implements IApiTestClientConfiguration {
	private static final ResourceBundle CONFIG = ResourceBundle.getBundle("config");
	
	private static final String PROXY_ENABLED = "proxy.enabled";
	private static final String PROXY_HOST = "proxy.host";
	private static final String PROXY_PORT = "proxy.port";
	private static final String PROXY_EXCEPTIONS = "proxy.exceptions";
	
	private static final Configuration instance = new Configuration();

	private Configuration() {}

	public static Configuration getInstance() {
		return instance;
	}
	
	public String getBaseUrl() {
		return null;
	}

	@Override
	public boolean isProxyEnabled() {
		return Boolean.parseBoolean(CONFIG.getString(PROXY_ENABLED));
	}

	@Override
	public String getProxyHost() {
		return CONFIG.getString(PROXY_HOST);
	}

	@Override
	public int getProxyPort() {
		return Integer.parseInt(CONFIG.getString(PROXY_PORT));
	}

	@Override
	public String[] getProxyExceptions() {
		return CONFIG.getString(PROXY_EXCEPTIONS).split(",");
	}
}
