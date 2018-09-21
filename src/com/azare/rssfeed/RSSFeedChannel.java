package com.azare.rssfeed;


/**
 * An entity to store the RSS Feed Information.
 * @author azare
 *
 */

public class RSSFeedChannel {
	
	private String channel_title;
	private String channel_description;
	private String channel_link;
	private RSSFeedItems feed_items;
	
	public RSSFeedChannel(final String title, 
			final String description, final String link)
	{
		this.channel_title = title;
		this.channel_description = description;
		this.channel_link = link;
		feed_items = new RSSFeedItems();
	}
	
	public RSSFeedChannel()
	{
		feed_items = new RSSFeedItems();
	}
	
	public void setChannelTitle(String title)
	{
		this.channel_title = title;
	}
	
	public void setChannelDesc(String description)
	{
		this.channel_description = description;
	}
	
	public void setChannelLink(String link)
	{
		this.channel_link = link;
	}
	
	public void addFeedItem(final String title, final String description,
			final String link, final String pubDate)
	{
		if ( ! this.feed_items.add(title, description, link, pubDate) )
		{
			System.out.println("Error adding Feed Item.");
		}
	}
	
	public String getChannelTitle()
	{
		return channel_title;
	}
	
	public String getChannelDesc()
	{
		return channel_description;
	}
	
	public String getChannelLink()
	{
		return channel_link;
	}
	
	public RSSFeedItems getFeedItems()
	{
		return feed_items;
	}

	public String getFeedChannelInfo()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nRSS Feed Details - ").append("\n")
		.append("\nTitle : ").append(this.channel_title).append("\n")
		.append("Description : ").append(this.channel_description).append("\n")
		.append("Link : ").append(this.channel_link).append("\n");
		
		sb.append("\nRSS Feed Items - ").append("\n")
		.append(feed_items.listAllFeedItems());
		
		return sb.toString();
	}
}
