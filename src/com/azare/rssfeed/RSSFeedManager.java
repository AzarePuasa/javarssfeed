package com.azare.rssfeed;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage processing of a Primary and zero to many Secondary RSS Feed Source
 * 
 * @author azare
 *
 */

public class RSSFeedManager 
{
	List<IRSSProcessor> rssProcessors = new ArrayList<IRSSProcessor>();
	
	public RSSFeedManager(String[] rssFeeds) throws Exception
	{
		//Primary RSS.
		try
		{
			PrimaryRSSProcessor uzabaseRSS = new PrimaryRSSProcessor();
			
			rssProcessors.add(uzabaseRSS);
			
		} catch (Exception e)
		{
			throw new Exception("Invalid Primary RSS URL Source");
		}
		
		//accept 1 or more Secondary RSS URL
		for (String rssFeed : rssFeeds)
		{
			try
			{
				SecondaryRSSProcessor secondary = new SecondaryRSSProcessor(rssFeed);
				rssProcessors.add(secondary);
			} catch (Exception e)
			{
				System.out.println("Invalid Secondary RSS URL Source: " + rssFeed);
			}
		}
	}
	
	public void processFeeds() throws Exception
	{
		for ( IRSSProcessor rssProcessor : rssProcessors)
		{
			try
			{
				rssProcessor.process();
			}
			catch (Exception e)
			{
				System.out.println("Fail to process");
			}	
		}
	}

}
