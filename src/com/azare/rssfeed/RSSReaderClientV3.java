package com.azare.rssfeed;

/**
 * A client to invoke the process of RSS Feed Conversion and display.
 * Accepts command line in the form of RSS Feed URLs for secondary source.
 * @author azare
 *
 */

public class RSSReaderClientV3 {

	public static void main(String[] args) 
	{
		try 
		{		
			RSSFeedManager manager = new RSSFeedManager(args);
			
			manager.processFeeds();

		}catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}
}
