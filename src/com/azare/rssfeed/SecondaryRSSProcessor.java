package com.azare.rssfeed;

public class SecondaryRSSProcessor extends ARSSProcessor implements IRSSProcessor {

	/**
	 * Secondary RSS Feed Processor.
	 * @param strUrl
	 * @throws Exception
	 */
	
	public SecondaryRSSProcessor(String strUrl) throws Exception {
		super(strUrl);
	}

	@Override
	public void process() throws Exception {
		// TODO Auto-generated method stub
		getRSSFeed();

		parseRSSFeed();

		output(System.out); //To Console.
		
	}

}
