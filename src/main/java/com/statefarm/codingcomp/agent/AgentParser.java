package com.statefarm.codingcomp.agent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.statefarm.codingcomp.bean.Address;
import com.statefarm.codingcomp.bean.Agent;
import com.statefarm.codingcomp.bean.Office;
import com.statefarm.codingcomp.utilities.SFFileReader;

@Component
public class AgentParser {
	@Autowired
	private SFFileReader sfFileReader;

	@Cacheable(value = "agents")
	public Agent parseAgent(String fileName) {
		try {
			Agent agent = new Agent();
			
			Office office1 = new Office();
			Office office2 = new Office();
			Set<String> languages1 = new HashSet<String>();
			Set<String> languages2 = new HashSet<String>();
			List<String> office1Hours = new ArrayList<String>();
			List<String> office2Hours = new ArrayList<String>();
			Address address1 = new Address();
			Address address2 = new Address();
			
			File theHtmlPage = new File(Paths.get("src", "test", "resources", "KevinParks.html").toString());
			Document theAgent = Jsoup.parse(theHtmlPage, "UTF-8", "");
			// Grab mainOffice
			Elements offices = theAgent.select("#tabGroupOffice");
			
			String mainOfficeTitle = offices.select("#tabmainLocation").text();
			String secondOfficetTitle = offices.select("#tabadditionalLoc_0").text();
			
			String mainofficeLanguage1 = offices.select("#languageEnglish_mainLocContent").text();
			String mainofficeLanguage2 = offices.select("#languageSpanish_mainLocContent_0").text();
			languages1.add(mainofficeLanguage1);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
