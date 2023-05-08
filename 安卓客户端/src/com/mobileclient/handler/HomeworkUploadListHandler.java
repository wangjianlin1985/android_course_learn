package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.HomeworkUpload;
public class HomeworkUploadListHandler extends DefaultHandler {
	private List<HomeworkUpload> homeworkUploadList = null;
	private HomeworkUpload homeworkUpload;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (homeworkUpload != null) { 
            String valueString = new String(ch, start, length); 
            if ("uploadId".equals(tempString)) 
            	homeworkUpload.setUploadId(new Integer(valueString).intValue());
            else if ("homeworkTaskObj".equals(tempString)) 
            	homeworkUpload.setHomeworkTaskObj(new Integer(valueString).intValue());
            else if ("studentObj".equals(tempString)) 
            	homeworkUpload.setStudentObj(valueString); 
            else if ("homeworkFile".equals(tempString)) 
            	homeworkUpload.setHomeworkFile(valueString); 
            else if ("uploadTime".equals(tempString)) 
            	homeworkUpload.setUploadTime(valueString); 
            else if ("resultFile".equals(tempString)) 
            	homeworkUpload.setResultFile(valueString); 
            else if ("pigaiTime".equals(tempString)) 
            	homeworkUpload.setPigaiTime(valueString); 
            else if ("pigaiFlag".equals(tempString)) 
            	homeworkUpload.setPigaiFlag(new Integer(valueString).intValue());
            else if ("pingyu".equals(tempString)) 
            	homeworkUpload.setPingyu(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("HomeworkUpload".equals(localName)&&homeworkUpload!=null){
			homeworkUploadList.add(homeworkUpload);
			homeworkUpload = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		homeworkUploadList = new ArrayList<HomeworkUpload>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("HomeworkUpload".equals(localName)) {
            homeworkUpload = new HomeworkUpload(); 
        }
        tempString = localName; 
	}

	public List<HomeworkUpload> getHomeworkUploadList() {
		return this.homeworkUploadList;
	}
}
