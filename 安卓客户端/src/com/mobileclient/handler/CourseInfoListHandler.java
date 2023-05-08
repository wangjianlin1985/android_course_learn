package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.CourseInfo;
public class CourseInfoListHandler extends DefaultHandler {
	private List<CourseInfo> courseInfoList = null;
	private CourseInfo courseInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (courseInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	courseInfo.setId(new Integer(valueString).intValue());
            else if ("jianjie".equals(tempString)) 
            	courseInfo.setJianjie(valueString); 
            else if ("dagan".equals(tempString)) 
            	courseInfo.setDagan(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("CourseInfo".equals(localName)&&courseInfo!=null){
			courseInfoList.add(courseInfo);
			courseInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		courseInfoList = new ArrayList<CourseInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("CourseInfo".equals(localName)) {
            courseInfo = new CourseInfo(); 
        }
        tempString = localName; 
	}

	public List<CourseInfo> getCourseInfoList() {
		return this.courseInfoList;
	}
}
