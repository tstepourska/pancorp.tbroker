package com.pancorp.tbroker.data;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class JAXBMarshaller {

	public JAXBMarshaller() {
		// TODO Auto-generated constructor stub
	}
	
	public void invoke(String xml) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(ScannerParameters.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(xml);
		ScannerParameters sp = (ScannerParameters) unmarshaller.unmarshal(reader);
	}

	public static void main(String[] args) {
		

	}
	
	static class ScannerParameters {
		
	}

}
