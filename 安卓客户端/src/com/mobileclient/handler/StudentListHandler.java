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
            if ("studentNumber".equals(tempString)) 
            	student.setStudentNumber(valueString); 
            else if ("password".equals(tempString)) 
            	student.setPassword(valueString); 
            else if ("name".equals(tempString)) 
            	student.setName(valueString); 
            else if ("sex".equals(tempString)) 
            	student.setSex(valueString); 
            else if ("birthday".equals(tempString)) 
            	student.setBirthday(Timestamp.valueOf(valueString));
            else if ("zzmm".equals(tempString)) 
            	student.setZzmm(valueString); 
            else if ("className".equals(tempString)) 
            	student.setClassName(valueString); 
            else if ("telephone".equals(tempString)) 
            	student.setTelephone(valueString); 
            else if ("photo".equals(tempString)) 
            	student.setPhoto(valueString); 
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
