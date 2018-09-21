package com.azare.rssfeed;

public class RSSReaderClientV2 {

	public static void main(String[] args) 
	{
		try {
			//checks for one input, else throw exception 
			if (args.length != 1)
			{
				throw new Exception("Usage: java RSSReaderClient <Feed URL> "
						+ "e.g. http://www.wdc.govt.nz/Pages/RSS.aspx?feed=PublicNotices");
			}
			
			//RSSFeedProcessor feedProcessor = new RSSFeedProcessor(args[0]);
			PrimaryRSSProcessor UzabaseProcessor = new PrimaryRSSProcessor();
			
			UzabaseProcessor.process();

		}catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}

}
