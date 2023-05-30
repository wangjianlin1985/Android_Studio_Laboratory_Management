package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.WeekInfo;
public class WeekInfoListHandler extends DefaultHandler {
	private List<WeekInfo> weekInfoList = null;
	private WeekInfo weekInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (weekInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("weekId".equals(tempString)) 
            	weekInfo.setWeekId(new Integer(valueString).intValue());
            else if ("weekName".equals(tempString)) 
            	weekInfo.setWeekName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("WeekInfo".equals(localName)&&weekInfo!=null){
			weekInfoList.add(weekInfo);
			weekInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		weekInfoList = new ArrayList<WeekInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("WeekInfo".equals(localName)) {
            weekInfo = new WeekInfo(); 
        }
        tempString = localName; 
	}

	public List<WeekInfo> getWeekInfoList() {
		return this.weekInfoList;
	}
}
