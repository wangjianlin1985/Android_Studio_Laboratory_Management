package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.LatState;
public class LatStateListHandler extends DefaultHandler {
	private List<LatState> latStateList = null;
	private LatState latState;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (latState != null) { 
            String valueString = new String(ch, start, length); 
            if ("stateId".equals(tempString)) 
            	latState.setStateId(valueString); 
            else if ("stateName".equals(tempString)) 
            	latState.setStateName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("LatState".equals(localName)&&latState!=null){
			latStateList.add(latState);
			latState = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		latStateList = new ArrayList<LatState>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("LatState".equals(localName)) {
            latState = new LatState(); 
        }
        tempString = localName; 
	}

	public List<LatState> getLatStateList() {
		return this.latStateList;
	}
}
