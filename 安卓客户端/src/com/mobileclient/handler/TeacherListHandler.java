package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Teacher;
public class TeacherListHandler extends DefaultHandler {
	private List<Teacher> teacherList = null;
	private Teacher teacher;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (teacher != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	teacher.setId(new Integer(valueString).intValue());
            else if ("name".equals(tempString)) 
            	teacher.setName(valueString); 
            else if ("position".equals(tempString)) 
            	teacher.setPosition(valueString); 
            else if ("password".equals(tempString)) 
            	teacher.setPassword(valueString); 
            else if ("introduction".equals(tempString)) 
            	teacher.setIntroduction(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Teacher".equals(localName)&&teacher!=null){
			teacherList.add(teacher);
			teacher = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		teacherList = new ArrayList<Teacher>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Teacher".equals(localName)) {
            teacher = new Teacher(); 
        }
        tempString = localName; 
	}

	public List<Teacher> getTeacherList() {
		return this.teacherList;
	}
}
