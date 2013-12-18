package com.recherchetaff.utils;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class Network {

	private static Network network;

	private HttpClient httpClient;

	public static Network getInstance() {
		if (network == null) {
			network = new Network();
		}
		return network;
	}

	private Network() {
		httpClient = createHttpClient();
	}

	private HttpClient createHttpClient() {
		HttpParams lParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(lParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(lParams, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(lParams, true);

		SchemeRegistry lSchReg = new SchemeRegistry();
		lSchReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

		ClientConnectionManager lConMgr = new ThreadSafeClientConnManager(lParams, lSchReg);

		return new DefaultHttpClient(lConMgr, lParams);
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}
}
