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
import com.statefarm.codingcomp.bean.Product;
import com.statefarm.codingcomp.utilities.SFFileReader;

@Component
public class AgentParser {
	@Autowired
	private SFFileReader sfFileReader;

	@Cacheable(value = "agents")
	public Agent parseAgent(String fileName) {
		Agent agent = new Agent();
		try {
			Office office1 = new Office();
			Office office2 = new Office();
			Set<String> languages1 = new HashSet<String>();
			Set<String> languages2 = new HashSet<String>();
			List<String> office1Hours = new ArrayList<String>();
			List<String> office2Hours = new ArrayList<String>();
			Address address1 = new Address();
			Address address2 = new Address();
			
			File theHtmlPage = new File(fileName);
			System.out.println(fileName);
			Document theAgent = Jsoup.parse(theHtmlPage, "UTF-8", "");
			//System.out.println(theAgent);
			// Grab mainOffice
//			Elements offices = theAgent.select("#tabGroupOffice");
//			
//			String mainOfficeTitle = offices.select("#tabmainLocation").text();
//			String secondOfficetTitle = offices.select("#tabadditionalLoc_0").text();
//			
//			String mainofficeLanguage1 = offices.select("#languageEnglish_mainLocContent").text();
//			String mainofficeLanguage2 = offices.select("#languageSpanish_mainLocContent_0").text();
//			
//			String secondOfficeLanguage1 = offices.select("#languageEnglish_additionalLocContent_0").text();
//			String secondOfficeLanguage2 = offices.select("#languageSpanish_additionalLocContent_0_0").text();
//			
//			
//			languages1.add(mainofficeLanguage1);
//			languages1.add(mainofficeLanguage2);
//			languages2.add(secondOfficeLanguage1);
//			languages2.add(secondOfficeLanguage2);
//			
//			String mainOfficeLine1 = offices.select("#locStreetContent_mainLocContent").text();
//			
//			Elements mainOfficeCity = offices.select("span[itemprop=addressLocality]:lt(1)");
//			// Diagnostic code
//			Elements mainCity = mainOfficeCity.get(0).select("span[itemprop=addressLocality]:lt(1)");
//			String secondCity = mainOfficeCity.get(1).outerHtml();

			// Parsing Products
			// aria-label=Products Offered/Serviced by this Agent
			Elements productsDivLi = theAgent.select("div[aria-label='Products Offered/Serviced by this Agent'] li");
			Set<Product> products = new HashSet<Product>();
			for(Element li : productsDivLi) {
				//System.out.println(li.text());
				products.add(Product.fromValue(li.text()));
			}
			agent.setProducts(products);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return agent;
	}
}
