import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextBuddy {

	/*
	 * ==============NOTE TO SELF========================================= These
	 * messages shown to the user are defined in one place for convenient
	 * editing and proof reading. Such messages are considered part of the UI
	 * and may be subjected to review by UI experts or technical writers. Note
	 * that Some of the strings below include '%1$s' etc to mark the locations
	 * at which java String.format(...) method can insert values.
	 * ====================================================================
	 */
	
	// The possible command types
	enum CommandType {
		ADD, DISPLAY, DELETE, CLEAR, EXIT
	};
	
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_ADD = "added to %1$s: %2$s\"";
	private static final String MESSAGE_DISPLAY = "%1$s. \"%2$s\"";
	private static final String MESSAGE_DELETE = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_ERROR = "Unrecognised command type";
	
	private static Scanner sc = new Scanner(System.in);
	private static ArrayList<String> arr = new ArrayList<String>();

	public static void main(String[] args) throws IOException {

		String fileName = args[0];
		File file = new File(fileName);
		// if file does not exist, create new file
		if (!file.exists()) {
			newFile(file);
		}
		readFile(fileName);
		System.out.println(String.format(MESSAGE_WELCOME, fileName));

		executeCommand(fileName);
		writeFile(file);
	}

	private static void executeCommand(String fileName) {
		while (sc.hasNextLine()) {
			String userCommand = sc.next().toLowerCase();
			String content = sc.nextLine();

			if (userCommand.equals("add")) {
				addContent(fileName, content);
			} else if (userCommand.equals("display")) {
				displayContent();
			} else if (userCommand.equals("delete")) {
				delContent(fileName, content);
			} else if (userCommand.equals("clear")) {
				clear(fileName);
			} else if (userCommand.equals("exit")) {
				break;
			} else {
				System.out.println(MESSAGE_ERROR);
			}
		}
	}

	private static void newFile(File file) {
		try {
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void readFile(String fileName) throws FileNotFoundException,
			IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		String str = null;
		while ((str = br.readLine()) != null) {
			arr.add(str);
		}
		br.close();
	}

	private static void addContent(String fileName, String input) {
		arr.add(input.trim());
		System.out.println(String.format(MESSAGE_ADD, fileName, input));
	}

	private static void displayContent() {
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(String.format(MESSAGE_DISPLAY, i+1, arr.get(i)));
		}
	}

	private static void delContent(String fileName, String input) {
		int toDel = Integer.valueOf(input.trim());
		System.out.println(String.format(MESSAGE_DELETE, fileName, arr.get(toDel-1)));
		arr.remove(toDel - 1);
	}

	private static void clear(String fileName) {
		arr.clear();
		System.out.println(String.format(MESSAGE_CLEAR, fileName));
	}

	private static void writeFile(File file) throws IOException {
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < arr.size(); i++) {
			br.write(arr.get(i) + "\n");
		}
		br.close();
	}
}