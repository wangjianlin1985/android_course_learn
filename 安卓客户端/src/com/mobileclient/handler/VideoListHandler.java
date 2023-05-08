package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Video;
public class VideoListHandler extends DefaultHandler {
	private List<Video> videoList = null;
	private Video video;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (video != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	video.setId(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	video.setTitle(valueString); 
            else if ("chapterId".equals(tempString)) 
            	video.setChapterId(new Integer(valueString).intValue());
            else if ("path".equals(tempString)) 
            	video.setPath(valueString); 
            else if ("addTime".equals(tempString)) 
            	video.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Video".equals(localName)&&video!=null){
			videoList.add(video);
			video = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		videoList = new ArrayList<Video>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Video".equals(localName)) {
            video = new Video(); 
        }
        tempString = localName; 
	}

	public List<Video> getVideoList() {
		return this.videoList;
	}
}
