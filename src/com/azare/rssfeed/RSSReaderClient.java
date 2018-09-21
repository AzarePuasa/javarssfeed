package com.azare.rssfeed;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Client class for the RSS Feed Reader.
 * Expects an input from console which is the url of the 
 * RSS Feed. 
 * @author azare
 *
 */

public class RSSReaderClient {
	
	public static void main(String[] args)
	{	
		try {
			
			//checks for one input.
			//else throw exception 
			if (args.length != 1)
			{
				throw new Exception("Usage: java RSSReaderClient <Feed URL> "
						+ "e.g. http://www.wdc.govt.nz/Pages/RSS.aspx?feed=PublicNotices");
			}
			
			//Feed Url is valid. 
			//1. get the content.
		    // - use open stream.
			
			//Validate the input is a URL. throw exception otherwise.
			String strUrl = args[0];
			
		    URL url = new URL(strUrl);
   
		    InputStream icontentstream = url.openStream();
		    
		    File outFile = new File(Paths.get("/Users/azare/rssfeedtest")
		    		.resolve("Output.xml").toAbsolutePath().toString());
		    
		    if (outFile.exists())
		    {
		    	System.out.println(outFile.toString() + " exist. Deleting File");
		    	outFile.delete();
		    }
		    
			try (InputStreamReader instream = new InputStreamReader(icontentstream);
					BufferedReader reader = new BufferedReader(instream);
					OutputStreamWriter outstream = new OutputStreamWriter(new FileOutputStream(outFile));
					BufferedWriter writer = new BufferedWriter(outstream)) {
				String s = null;
				while((s = reader.readLine()) != null) {
				    //System.out.println(s);
				    writer.write(s);
				    writer.newLine();
				}
			} catch (IOException e) { 
				System.out.println(e.getMessage());
				
			}
		    
		    
			//2. parse the content.
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(outFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList channel = doc.getElementsByTagName("channel");
			
			RSSFeedChannel feedChannel = new RSSFeedChannel();
			
			for(int i = 0; i < channel.getLength(); i++)
			{
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
					
					feedChannel.addFeedItem(title, description, link, pubdate);
					
				}
			}
			
			//3. display the Feed information.
		    // title:
		    // link:
		    // Description:
		    // (Loop the feed item)
		    // Item (N):
		    // Title:
		    // Description:
		    // Link:
		    // Publication date
		    
		    // Publication Date:
			
			System.out.println(feedChannel.getFeedChannelInfo());
		    
		    
		} catch (MalformedURLException e) {
		    // the URL is not in a valid form
		} catch (IOException e) {
		    // the connection couldn't be established
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}	
	}
}
