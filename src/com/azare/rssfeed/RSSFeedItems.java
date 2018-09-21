package com.azare.rssfeed;

import java.util.ArrayList;
import java.util.List;

/**
 * An entity to store RSS Feed Items.
 * @author azare
 *
 */

public class RSSFeedItems {

	private List<RSSFeedItem> items;
	
	public RSSFeedItems()
	{
		items = new ArrayList<RSSFeedItem>();
	}
	
	public boolean add(final String title, final String description,
				final String link, final String pubDate)
	{
		if( ! items.add(new RSSFeedItem(title, description, link, pubDate)) )
		{
			System.out.println("Fail to add Feed Item.");
			return false;
		}
		
		return true;
	}
	
	public List<RSSFeedItem> getFeedItems()
	{
		return items;
	}
	
	public String listAllFeedItems()
	{
		StringBuilder sb = new StringBuilder();
		for(RSSFeedItem item: this.items)
		{
			sb.append("\nTitle: ").append(item.getTitle())
			.append("\nDescription: ").append(item.getDescription())
			.append("\nLink: ").append(item.getLink())
			.append("\nPub Date: ").append(item.getPubDate());
		}
		
		return sb.toString();
	}
		
	private class RSSFeedItem
	{
		private String item_title;
		private String item_description;
		private String item_link;
		private String item_pubdate;
		
		public RSSFeedItem(final String title, final String description,
				final String link, final String pubDate)
		{
			this.item_title = title;
			this.item_description = description;
			this.item_link = link;
			this.item_pubdate = pubDate;
		}
		
		public String getTitle()
		{
			return item_title;
		}
		
		public String getDescription()
		{
			return item_description;
		}
		
		public String getLink()
		{
			return item_link;
		}
		
		public String getPubDate()
		{
			return item_pubdate;
		}
	}
}
