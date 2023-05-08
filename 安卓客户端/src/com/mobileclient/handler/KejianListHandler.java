package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Kejian;
public class KejianListHandler extends DefaultHandler {
	private List<Kejian> kejianList = null;
	private Kejian kejian;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (kejian != null) { 
            String valueString = new String(ch, start, length); 
            if ("id".equals(tempString)) 
            	kejian.setId(new Integer(valueString).intValue());
            else if ("title".equals(tempString)) 
            	kejian.setTitle(valueString); 
            else if ("path".equals(tempString)) 
            	kejian.setPath(valueString); 
            else if ("addTime".equals(tempString)) 
            	kejian.setAddTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Kejian".equals(localName)&&kejian!=null){
			kejianList.add(kejian);
			kejian = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		kejianList = new ArrayList<Kejian>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Kejian".equals(localName)) {
            kejian = new Kejian(); 
        }
        tempString = localName; 
	}

	public List<Kejian> getKejianList() {
		return this.kejianList;
	}
}
