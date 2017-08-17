package helpers;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by kinmanz on 17.08.17.
 */
public class SimpleCacheTest {

    @Before
    public void beforeTests() {
        SimpleCache.setCacheData("TestKey", "TestValue");
    }

    @Test
    public void getCacheData() throws Exception {
        assertEquals("TestValue",
                (String) SimpleCache.getCacheData("TestKey"));
    }

    @Test
    public void setCacheData() throws Exception {
        SimpleCache.setCacheData("TestKey2", "TestValue2");
        assertEquals("TestValue2",
                (String) SimpleCache.getCacheData("TestKey2"));
    }

    @Test
    public void containsKey() throws Exception {
        assertTrue(SimpleCache.containsKey("TestKey"));
        assertFalse(SimpleCache.containsKey("TestKeyWrong"));
    }

}
