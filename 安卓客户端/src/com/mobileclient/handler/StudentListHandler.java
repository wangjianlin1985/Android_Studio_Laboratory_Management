package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Student;
public class StudentListHandler extends DefaultHandler {
	private List<Student> studentList = null;
	private Student student;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (student != null) { 
            String valueString = new String(ch, start, length); 
            if ("studentNo".equals(tempString)) 
            	student.setStudentNo(valueString); 
            else if ("password".equals(tempString)) 
            	student.setPassword(valueString); 
            else if ("classObj".equals(tempString)) 
            	student.setClassObj(valueString); 
            else if ("name".equals(tempString)) 
            	student.setName(valueString); 
            else if ("gender".equals(tempString)) 
            	student.setGender(valueString); 
            else if ("birthDate".equals(tempString)) 
            	student.setBirthDate(Timestamp.valueOf(valueString));
            else if ("studentPhoto".equals(tempString)) 
            	student.setStudentPhoto(valueString); 
            else if ("telephone".equals(tempString)) 
            	student.setTelephone(valueString); 
            else if ("email".equals(tempString)) 
            	student.setEmail(valueString); 
            else if ("address".equals(tempString)) 
            	student.setAddress(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Student".equals(localName)&&student!=null){
			studentList.add(student);
			student = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		studentList = new ArrayList<Student>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Student".equals(localName)) {
            student = new Student(); 
        }
        tempString = localName; 
	}

	public List<Student> getStudentList() {
		return this.studentList;
	}
}
