package ext;

import play.templates.JavaExtensions;

/**
 * 
 * Some very useful extensions to the {@link String} class.
 * 
 * @author <a href="mailto:alexander.hanschke@coinor.de">Alexander Hanschke</a>
 *
 */
public class StringExtensions extends JavaExtensions {
	
	/**
	 * 
	 * @param source the String to work with
	 * @return an anonymized representation of the {@code source}
	 */
	public static String anonymize(String source) {
		return source.replaceAll(".", "*");
	}

}
