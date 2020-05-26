package ControlPanel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.xml.sax.SAXParseException;

import java.io.FileNotFoundException;


/**
 * JUnit5 tests for the XMLHandler class
 * @author Connor McHugh - n10522662
 */
class TestXMLHandler {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
	/*
	 * Confirm that the program throws an exception when the file provided doesn't exist.
	 */
    public void readerNonExistentFile() {
		assertThrows(FileNotFoundException.class, () -> {
			XMLHandler.xmlReader(true,"invalid file name", "message", "null");
		});
    }

	@Test
	/*
	 * Confirm that the program throws an exception when the file provided is invalid.
	 */
	public void readerInvalidFile() {
		assertThrows(SAXParseException.class, () -> {
			XMLHandler.xmlReader(true,"./src/ControlPanel/unit_test_data/test.txt", "message", "null");
		});
	}

    @Test
	/*
	 * Confirm that when an invalid tag is passed, the method returns null.
	 */
    void readerInvalidTag() throws Exception {
    	String tester = XMLHandler.xmlReader(true,"./src/ControlPanel/unit_test_data/test.xml", "invalid tag", "null");
		assertNull(tester);
	}

	@Test
	/*
	 * Confirm that when an invalid attribute is passed, the method returns null.
	 */
	void readerInvalidAttribute() throws Exception {
		String tester = XMLHandler.xmlReader(true,"./src/ControlPanel/unit_test_data/test.xml", "invalid tag", "null");
		assertNull(tester);
	}
}
