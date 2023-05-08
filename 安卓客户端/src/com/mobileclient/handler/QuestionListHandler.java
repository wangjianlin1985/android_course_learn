package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Question;
public class QuestionListHandler extends DefaultHandler {
	private List<Question> questionList = null;
	private Question question;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (question != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	question.setId(new Integer(valueString).intValue());
            else if ("teacherId".equals(tempString)) 
            	question.setTeacherId(new Integer(valueString).intValue());
            else if ("questioner".equals(tempString)) 
            	question.setQuestioner(valueString); 
            else if ("content".equals(tempString)) 
            	question.setContent(valueString); 
            else if ("reply".equals(tempString)) 
            	question.setReply(valueString); 
            else if ("addTime".equals(tempString)) 
            	question.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Question".equals(localName)&&question!=null){
			questionList.add(question);
			question = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		questionList = new ArrayList<Question>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Question".equals(localName)) {
            question = new Question(); 
        }
        tempString = localName; 
	}

	public List<Question> getQuestionList() {
		return this.questionList;
	}
}
