package restservices.publish;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.json.JSONWriter;

import restservices.RestServices;
import restservices.util.DataWriter;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;

public class RestServiceRequest {
	public static enum ContentType { JSON, XML, HTML }

	Request request;
	Response response;
	private ContentType contentType = ContentType.JSON;
	private IContext context;
	protected DataWriter datawriter;

	public RestServiceRequest(Request request, Response response) {
		this.request = request;
		this.response = response;
		this.context = Core.createSystemContext(); //TODO: should be based on user credentials if access was required?
		
		this.contentType = determineContentType(request);
		setResponseContentType(response, contentType);

		try {
			this.datawriter = new DataWriter(response.getOutputStream(), contentType == ContentType.HTML ? DataWriter.HTML : contentType == ContentType.XML ? DataWriter.XML : DataWriter.JSON);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static ContentType determineContentType(Request request) {
		if (request.getParameter(RestServices.CONTENTTYPE_PARAM) != null)
			return ContentType.valueOf(request.getParameter(RestServices.CONTENTTYPE_PARAM).toUpperCase());
		String ct = request.getHeader(RestServices.ACCEPT_HEADER);
		if (ct != null) {
			if (ct.contains("text/json"))
				return ContentType.JSON;
			if (ct.contains("html"))
				return ContentType.HTML;
			if (ct.contains("xml")) 
				return ContentType.XML;
		}
		return ContentType.JSON; //by default
	}
	
	public static void setResponseContentType(Response response, ContentType contentType) {
		response.setContentType("text/" + contentType.toString().toLowerCase()+ "; charset=UTF-8");
	}
	
	public ContentType getContentType() {
		return this.contentType;
	}
	
	public RestServiceRequest write(String data) {
		try {
			this.response.getOutputStream().write(data.getBytes(RestServices.UTF8));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	public IContext getContext() {
		return this.context;
	}

	public void close() {
		try {
			this.response.getOutputStream().close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void startHTMLDoc() {
		this.write("<!DOCTYPE HTML><html><head><style>" + RestServices.STYLESHEET + "</style><head><body>");		
	}


	public void endHTMLDoc() {
		this.write("<p><center><small>View as: <a href='?contenttype=xml'>XML</a> <a href='?contenttype=json'>JSON</a></small></center></p>");
		this.write("<hr /><p><center><small>Generated by the Mendix RestServices module</small></center></body></html>");
	}

	public void startXMLDoc() {
		this.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	}

	public void setStatus(int status) {
		response.setStatus(status);
	}
}