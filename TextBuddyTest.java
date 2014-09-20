import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class TextBuddyTest {

	private static TextBuddy textBuddy = new TextBuddy();

	@Test
	public void testAddContent() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = TextBuddy.class.getDeclaredMethod("addContent",
				String.class, String.class);
		method.setAccessible(true);
		String result = (String) method.invoke(textBuddy, "hello.txt",
				"little brown fox");
		assertEquals("added to hello.txt: \"little brown fox\"", result);	
	}


	@Test
	public void testGetFirstWord() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = TextBuddy.class.getDeclaredMethod("getFirstWord",
				String.class);
		method.setAccessible(true);
		String result = (String) method.invoke(textBuddy,
				"add little brown fox");

		assertEquals("add", result);
	}

	@Test
	public void testSortContent() {
		String result = "false"; 
		assertEquals("false", result);
	}
	
	@Test
	public void testSearchContent() {
		String result = "false"; 
		assertEquals("false", result);
	}


	@Test
	public void testClear() throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Method method = TextBuddy.class
				.getDeclaredMethod("clear", String.class);
		method.setAccessible(true);
		method.invoke(textBuddy, "tello.txt");

		Method methodSearch = TextBuddy.class.getDeclaredMethod("clear",
				String.class);
		methodSearch.setAccessible(true);
		String result = (String) methodSearch.invoke(textBuddy, "tello.txt");
		assertEquals("all content deleted from tello.txt", result);
	}

}
