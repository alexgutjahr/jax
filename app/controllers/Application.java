package controllers;

import java.util.List;

import models.Session;
import play.data.validation.Valid;
import play.mvc.Controller;

/**
 * 
 * Primary controller for handling incoming requests.
 * 
 * @author <a href="mailto:alexander.hanschke@coinor.de">Alexander Hanschke</a>
 * 
 */
public class Application extends Controller {

	/*
	 * -> /views/Application/index.html
	 */
    public static void index() {
        List<Session> sessions = Session.all().fetch();
        List<Session> upcoming = Session.upcoming();
        render(sessions, upcoming);
    }
    
    /*
     * -> /views/Application/show.html
     */
    public static void show(Long id) {
    	Session talk = Session.findById(id);
    	notFoundIfNull(talk);
    	render(talk);
    }
    
    /*
     * REDIRECT:
     * -> /views/Application/index.html
     */
    public static void delete(Session session) {
    	Session victim = Session.findById(session.id);
    	notFoundIfNull(victim);
    	victim._delete();
    	index();
    }
    
    /*
     * REDIRECT:
     * -> /views/Application/show.html
     */
    public static void update(@Valid Session session) {
    	if (validation.hasErrors()) {
    		validation.keep();
    		
    		show(session.id);
    	}
    	
    	session.save();
    	show(session.id);
    }

}