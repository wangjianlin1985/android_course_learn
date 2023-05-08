package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Exercise;
public class ExerciseListHandler extends DefaultHandler {
	private List<Exercise> exerciseList = null;
	private Exercise exercise;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (exercise != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	exercise.setId(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	exercise.setTitle(valueString); 
            else if ("chapterId".equals(tempString)) 
            	exercise.setChapterId(new Integer(valueString).intValue());
            else if ("content".equals(tempString)) 
            	exercise.setContent(valueString); 
            else if ("addTime".equals(tempString)) 
            	exercise.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Exercise".equals(localName)&&exercise!=null){
			exerciseList.add(exercise);
			exercise = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		exerciseList = new ArrayList<Exercise>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Exercise".equals(localName)) {
            exercise = new Exercise(); 
        }
        tempString = localName; 
	}

	public List<Exercise> getExerciseList() {
		return this.exerciseList;
	}
}
