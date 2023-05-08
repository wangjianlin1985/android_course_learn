package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.HomeworkTask;
public class HomeworkTaskListHandler extends DefaultHandler {
	private List<HomeworkTask> homeworkTaskList = null;
	private HomeworkTask homeworkTask;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (homeworkTask != null) { 
            String valueString = new String(ch, start, length); 
            if ("homeworkId".equals(tempString)) 
            	homeworkTask.setHomeworkId(new Integer(valueString).intValue());
            else if ("teacherObj".equals(tempString)) 
            	homeworkTask.setTeacherObj(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	homeworkTask.setTitle(valueString); 
            else if ("content".equals(tempString)) 
            	homeworkTask.setContent(valueString); 
            else if ("addTime".equals(tempString)) 
            	homeworkTask.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("HomeworkTask".equals(localName)&&homeworkTask!=null){
			homeworkTaskList.add(homeworkTask);
			homeworkTask = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		homeworkTaskList = new ArrayList<HomeworkTask>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("HomeworkTask".equals(localName)) {
            homeworkTask = new HomeworkTask(); 
        }
        tempString = localName; 
	}

	public List<HomeworkTask> getHomeworkTaskList() {
		return this.homeworkTaskList;
	}
}
