package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.CourseTest;
public class CourseTestListHandler extends DefaultHandler {
	private List<CourseTest> courseTestList = null;
	private CourseTest courseTest;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (courseTest != null) { 
            String valueString = new String(ch, start, length); 
            if ("testId".equals(tempString)) 
            	courseTest.setTestId(new Integer(valueString).intValue());
            else if ("courseObj".equals(tempString)) 
            	courseTest.setCourseObj(valueString); 
            else if ("testName".equals(tempString)) 
            	courseTest.setTestName(valueString); 
            else if ("weekObj".equals(tempString)) 
            	courseTest.setWeekObj(new Integer(valueString).intValue());
            else if ("labTime".equals(tempString)) 
            	courseTest.setLabTime(valueString); 
            else if ("labObj".equals(tempString)) 
            	courseTest.setLabObj(new Integer(valueString).intValue());
            else if ("testMemo".equals(tempString)) 
            	courseTest.setTestMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("CourseTest".equals(localName)&&courseTest!=null){
			courseTestList.add(courseTest);
			courseTest = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		courseTestList = new ArrayList<CourseTest>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("CourseTest".equals(localName)) {
            courseTest = new CourseTest(); 
        }
        tempString = localName; 
	}

	public List<CourseTest> getCourseTestList() {
		return this.courseTestList;
	}
}
