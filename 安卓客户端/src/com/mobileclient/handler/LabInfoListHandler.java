package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.LabInfo;
public class LabInfoListHandler extends DefaultHandler {
	private List<LabInfo> labInfoList = null;
	private LabInfo labInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (labInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("labId".equals(tempString)) 
            	labInfo.setLabId(new Integer(valueString).intValue());
            else if ("labName".equals(tempString)) 
            	labInfo.setLabName(valueString); 
            else if ("labPhoto".equals(tempString)) 
            	labInfo.setLabPhoto(valueString); 
            else if ("labArea".equals(tempString)) 
            	labInfo.setLabArea(new Float(valueString).floatValue());
            else if ("personNum".equals(tempString)) 
            	labInfo.setPersonNum(new Integer(valueString).intValue());
            else if ("labAddress".equals(tempString)) 
            	labInfo.setLabAddress(valueString); 
            else if ("labStateObj".equals(tempString)) 
            	labInfo.setLabStateObj(valueString); 
            else if ("labDesc".equals(tempString)) 
            	labInfo.setLabDesc(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("LabInfo".equals(localName)&&labInfo!=null){
			labInfoList.add(labInfo);
			labInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		labInfoList = new ArrayList<LabInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("LabInfo".equals(localName)) {
            labInfo = new LabInfo(); 
        }
        tempString = localName; 
	}

	public List<LabInfo> getLabInfoList() {
		return this.labInfoList;
	}
}
