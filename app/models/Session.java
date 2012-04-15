package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;

import net.sf.oval.constraint.Size;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * 
 * Entity zur Repr&auml;sentation einer JAX Session.
 * 
 * @author <a href="mailto:alexander.hanschke@coinor.de">Alexander Hanschke</a>
 *
 */
@Entity
public class Session extends Model {

	@Size(max = 50)
	@Required(message = "session.name.required")
	public String name;
	
	@Required(message = "session.speaker.required")
	public String speaker;
	
	public String company;

	@Required(message = "session.startsAt.required")
	public Date startsAt;
	
	@Required(message = "session.endsAt.required")
	public Date endsAt;
	
	@Lob
	@Size(max = 400)
	@Required(message = "session.description.required")
	public String description;
	
	public Date added = new Date();
	
	public static List<Session> upcoming() {
		return find("startsAt > ? order by startsAt asc", new Date()).fetch(10);
	}
	
	@Override
	public String toString() {
		return name;
	}

}
