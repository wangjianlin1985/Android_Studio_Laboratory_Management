package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Device;
public class DeviceListHandler extends DefaultHandler {
	private List<Device> deviceList = null;
	private Device device;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (device != null) { 
            String valueString = new String(ch, start, length); 
            if ("deviceId".equals(tempString)) 
            	device.setDeviceId(new Integer(valueString).intValue());
            else if ("deviceName".equals(tempString)) 
            	device.setDeviceName(valueString); 
            else if ("labObj".equals(tempString)) 
            	device.setLabObj(new Integer(valueString).intValue());
            else if ("devicePhoto".equals(tempString)) 
            	device.setDevicePhoto(valueString); 
            else if ("devicePrice".equals(tempString)) 
            	device.setDevicePrice(new Float(valueString).floatValue());
            else if ("deviceCount".equals(tempString)) 
            	device.setDeviceCount(new Integer(valueString).intValue());
            else if ("deviceDesc".equals(tempString)) 
            	device.setDeviceDesc(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Device".equals(localName)&&device!=null){
			deviceList.add(device);
			device = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		deviceList = new ArrayList<Device>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Device".equals(localName)) {
            device = new Device(); 
        }
        tempString = localName; 
	}

	public List<Device> getDeviceList() {
		return this.deviceList;
	}
}
