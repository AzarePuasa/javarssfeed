package com.azare.rssfeed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Implements default behaviours of a RSS Processor
 * 
 * @author azare
 *
 */

public abstract class ARSSProcessor implements IRSSProcessor{
	
	private URL feedUrl;
	protected RSSFeedChannel feedChannel;
	protected File rssFile;
	private static int FileCount = 0;
	
	private List<String> filterWord;
	
	public ARSSProcessor(String strUrl, String...excludeTitle) throws Exception {
		
		try {
			this.feedUrl = new URL(strUrl);
		} catch (MalformedURLException e) {
			throw new Exception("Invalid URL specified.");
		}
		
		feedChannel = new RSSFeedChannel();
		FileCount++;
		
		String rawInputFile = "RawInput_" + String.valueOf(FileCount) + ".xml"; 
		
		rssFile = new File(
				PROCESSOR_DIR.resolve(rawInputFile).toAbsolutePath().toString());

		if (rssFile.exists()) {
			System.out.println(rssFile.toString() + " exist. Deleting File");
			rssFile.delete();
		}
		
		filterWord = new ArrayList<>();
		
		if (excludeTitle.length > 0)
		{
			for (String exclude : excludeTitle)
			{
				this.filterWord.add(exclude);
			}
		}
	}
	
	public void getRSSFeed() throws Exception {
		InputStream icontentstream = this.feedUrl.openStream();

		try (InputStreamReader instream = new InputStreamReader(icontentstream);
				BufferedReader reader = new BufferedReader(instream);
				OutputStreamWriter outstream = new OutputStreamWriter(new FileOutputStream(rssFile));
				BufferedWriter writer = new BufferedWriter(outstream)) {
			String s = null;
			while ((s = reader.readLine()) != null) {
				writer.write(s);
				writer.newLine();
			}
		} catch (IOException e) {
			throw new Exception("Fail to download content to file.");
		}
	}
	
	public void parseRSSFeed() throws Exception
	{
		try 
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(rssFile);

			doc.getDocumentElement().normalize();

			NodeList channel = doc.getElementsByTagName("channel");

			for (int i = 0; i < channel.getLength(); i++) {
				Node nNode = channel.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					String title = eElement.getElementsByTagName("title").item(0).getTextContent();
					String link = eElement.getElementsByTagName("link").item(0).getTextContent();
					String description = eElement.getElementsByTagName("description").item(0).getTextContent();

					StringBuilder sb = new StringBuilder();

					feedChannel.setChannelTitle(title);
					feedChannel.setChannelDesc(description);
					feedChannel.setChannelLink(link);

					System.out.println(sb.toString());
				}
			}

			NodeList nList = doc.getElementsByTagName("item");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String link = eElement.getElementsByTagName("link").item(0).getTextContent();
					String title = eElement.getElementsByTagName("title").item(0).getTextContent();
					String description = eElement.getElementsByTagName("description").item(0).getTextContent();
					String pubdate = eElement.getElementsByTagName("pubDate").item(0).getTextContent();

					StringBuilder sb = new StringBuilder();

					System.out.println(sb.toString());
					
					boolean excludeItem = false;
					
					//filter items with string in title or body(description)
					if (filterWord.size() > 0)
					{
						for (String filterTitle : filterWord)
						{
							if (title.contains(filterTitle) || description.contains(filterTitle))
							{
								excludeItem = true;
								break;
							}
						}
					}
					
					if (!excludeItem)
					{
						feedChannel.addFeedItem(title, description, link, pubdate);
					}
				}
			}
		}
		catch (IOException | ParserConfigurationException e)
		{
			StringBuilder sb = new StringBuilder();
			
			sb.append("Cause:\n");
			for (StackTraceElement stackTrace : e.getStackTrace() )
			{
				sb.append(stackTrace.toString());
			}
			
			throw new Exception("Fail to Parse RSS Feed" + sb.toString());
		}
	}

	public void output(PrintStream out) throws IOException {
		
		out.println("Feed URL: ");
		
		if (isFilter())
		{
			out.println("Filtering Text: " + getfilterWord());
		}

		out.println(feedChannel.getFeedChannelInfo());
	}
	
	public URL getFeedUrl()
	{
		return this.feedUrl;
	}
	
	public RSSFeedChannel getFeedChannel()
	{
		return this.feedChannel;
	}
	
	public boolean isFilter()
	{
		return filterWord.size() > 0;
	}
	
	public String getfilterWord()
	{
		return filterWord.stream()
		.collect(Collectors.joining(", "));
	}

}
