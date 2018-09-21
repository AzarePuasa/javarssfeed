package com.azare.rssfeed;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Interface for all RSS Processors.
 * @author azare
 *
 */

public interface IRSSProcessor {
	
	Path PROCESSOR_DIR = Paths.get("/Users/azare/rssfeedtest");
	
	public void getRSSFeed() throws Exception;
	public void parseRSSFeed() throws Exception;
	public void process() throws Exception;
}
