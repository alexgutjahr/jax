import junit.framework.Assert;

import models.Session;

import org.junit.Test;

import play.test.UnitTest;

public class BasicTest extends UnitTest {

    @Test
    public void testCorrectSessionCrawling() {
    	Assert.assertEquals(202, Session.count());
    }

}
