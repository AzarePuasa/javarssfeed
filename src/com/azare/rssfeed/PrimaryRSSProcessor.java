package com.azare.rssfeed;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Primary RSS Processor. For Uzabase url.
 * Exclude items with word "NewsPicks" in title and description. 
 * @author azare
 *
 */

public class PrimaryRSSProcessor extends ARSSProcessor implements IRSSProcessor {
	
	public static final String RESULT_FILE_NAME = "UzabaseRSS.txt";
	
	public PrimaryRSSProcessor() throws Exception
	{		
		super("http://tech.uzabase.com/rss", "NewsPicks");
	}
	
	public void process() throws Exception {
		
		System.out.println("Processing Uzabase Processor");
		getRSSFeed();

		parseRSSFeed();

		output(System.out); //To Console.

		// output to file
		File resultFile = new File(PROCESSOR_DIR.resolve(RESULT_FILE_NAME).toAbsolutePath().toString());

		FileOutputStream fOutput = new FileOutputStream(resultFile);

		output(fOutput);
	}
	
	public void output(FileOutputStream output) 
	{
		try(BufferedOutputStream writer = new BufferedOutputStream(output);)
		{
			StringBuilder sbContent = new StringBuilder();
			
			sbContent.append("Feed URL: ").append( getFeedUrl().toString());
			
			if (isFilter())
			{
				sbContent.append("\n").append("Filter Text: ").append(getfilterWord());
			}
			
			sbContent.append("\n").append(getFeedChannel().getFeedChannelInfo());
			
			writer.write(sbContent.toString().getBytes());
			
		}catch (IOException e)
		{
			System.out.print("Error writing to file");
		}
	}

}
