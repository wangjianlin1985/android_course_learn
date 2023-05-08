package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Chapter;
public class ChapterListHandler extends DefaultHandler {
	private List<Chapter> chapterList = null;
	private Chapter chapter;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (chapter != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	chapter.setId(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	chapter.setTitle(valueString); 
            else if ("addTime".equals(tempString)) 
            	chapter.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Chapter".equals(localName)&&chapter!=null){
			chapterList.add(chapter);
			chapter = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		chapterList = new ArrayList<Chapter>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Chapter".equals(localName)) {
            chapter = new Chapter(); 
        }
        tempString = localName; 
	}

	public List<Chapter> getChapterList() {
		return this.chapterList;
	}
}
