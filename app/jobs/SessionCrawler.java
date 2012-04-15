package jobs;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import models.Session;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * 
 * Startup job to extract the JAX 2012 session information from an HTML site,
 * which are stored in the local database.
 * 
 * @author <a href="mailto:alexander.hanschke@coinor.de">Alexander Hanschke</a>
 *
 */
@OnApplicationStart
public class SessionCrawler extends Job {
	
	private final String REMOTE_SOURCE = "http://jax.de/2012/sessions";
	private final String LOCAL_SOURCE  = "file:/Users/alexhanschke/Desktop/jax-sessions/sessions.html";
	
	@Override
	public void doJob() throws Exception {
		
		Source source = null;
		
		if (online()) {
			source = new Source(new URL(REMOTE_SOURCE));
			Logger.debug("Started crawling %s", REMOTE_SOURCE);
		} else {
			source = new Source(new URL(LOCAL_SOURCE));
			Logger.debug("Started crawling %s", LOCAL_SOURCE);
		}
		
		Session.deleteAll();
		List<Element> elements = source.getAllElementsByClass("item");
		for (Element element : elements) {
			sessionFrom(element).save();
		}
		
		Logger.debug("Added %d sessions to the database!", Session.count());
	}
	
	private boolean online() {
		try {
			return InetAddress.getByName("jax.de").isReachable(5000);
		} catch (Throwable cause) {
			return false;
		}
	}

	private DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	
	private Session sessionFrom(Element element) throws Exception {
		Session session = new Session();
		
//		extract the name of the session
		session.name = element.getAllElements("h3").get(0).getContent().getTextExtractor().toString();	
		
//		extract the session start time and the session end time
		String date = element.getAllElementsByClass("session_date").get(0).getContent().toString();
		String time = element.getAllElementsByClass("session_time").get(0).getContent().toString();
		
		session.startsAt = formatter.parse(String.format("%s %s", date, time.split(" - ")[0]));
		session.endsAt   = formatter.parse(String.format("%s %s", date, time.split(" - ")[1]));
		
//		extract the session description
		session.description = element.getAllElements("p").get(1).getContent().getTextExtractor().toString();
		
//		extract the speaker
		session.speaker = element.getAllElements("a").get(0).getContent().getTextExtractor().toString();
		
//		extract the company
		session.company = element.getAllElementsByClass("firma").get(0).getContent().getTextExtractor().toString();
	
		return session;
	}

}
