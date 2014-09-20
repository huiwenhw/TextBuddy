import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SORT, SEARCH
	};

	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_ADD = "added to %1$s:\"%2$s\"";
	private static final String MESSAGE_DISPLAY = "%1$s. %2$s";
	private static final String MESSAGE_EMPTY = "%1$s is empty";
	private static final String MESSAGE_DELETE = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_ERROR = "Unrecognised command type";
	private static final String MESSAGE_SEARCH_ERROR = "Unrecognised search type%1$s/There is no line containing%1$s";

	private static Scanner sc = new Scanner(System.in);
	private static ArrayList<String> arr = new ArrayList<String>();
	private static String fileName = null;

	public static void main(String[] args) throws IOException {
		File file = Create(args);
		run(file);
	}

	// to check if a file is there. If it's not, create new file
	private static File Create(String[] args) throws FileNotFoundException,
			IOException {
		fileName = args[0];
		File file = new File(fileName);
		if (!file.exists()) {
			newFile(file);
		}
		readFile(fileName);
		System.out.println(String.format(MESSAGE_WELCOME, fileName));
		return file;
	}

	// to create new file
	private static void newFile(File file) {
		try {
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// to run the whole user commands
	private static void run(File file) throws IOException {
		while (true) {
			System.out.println("Command: ");
			String userCommand = sc.nextLine().toLowerCase();
			executeCommand(userCommand, file);
		}
	}

	// to execute commands by the user
	private static void executeCommand(String userCommand, File file)
			throws IOException {
		if (userCommand.trim().equals("")) {
			System.out.println(String.format(MESSAGE_ERROR));
		}
		CommandType commandType = determineCommandType(getFirstWord(userCommand));

		switch (commandType) {
			case ADD:
				System.out.println(addContent(fileName,
						removeFirstWord(userCommand.trim()).trim()));
				writeFile(file);
				break;
			case SORT:
				sortContent(fileName);
				break;
			case DISPLAY:
				displayContent(fileName);
				break;
			case DELETE:
				System.out.println(delContent(fileName, userCommand));
				break;
			case CLEAR:
				System.out.println(clear(fileName));
				break;
			case INVALID:
				System.out.println(String.format(MESSAGE_ERROR));
				break;
			case SEARCH:
				System.out.print(searchContent(removeFirstWord(userCommand
						.trim())));
				break;
			case EXIT:
				writeFile(file);
				System.exit(0);
			default:
				// throw an error if the command is not recognized
				throw new Error("Unrecognized command type");
		}
	}

	private static CommandType determineCommandType(String userCommandFirstWord) {
		CommandType detCommandType;

		switch (userCommandFirstWord) {
			case "null":
				throw new Error("command type string cannot be null!");
			case "add":
				detCommandType = CommandType.ADD;
				break;
			case "sort":
				detCommandType = CommandType.SORT;
				break;
			case "display":
				detCommandType = CommandType.DISPLAY;
				break;
			case "delete":
				detCommandType = CommandType.DELETE;
				break;
			case "clear":
				detCommandType = CommandType.CLEAR;
				break;
			case "search":
				detCommandType = CommandType.SEARCH;
				break;
			case "exit":
				detCommandType = CommandType.EXIT;
				break;
			default:
				detCommandType = CommandType.INVALID;
		}
		return detCommandType;
	}

	// to read in file contents to an ArrayList
	private static void readFile(String fileName) throws FileNotFoundException,
			IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String str = null;
		while ((str = br.readLine()) != null) {
			arr.add(str);
		}
		br.close();
	}

	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}

	private static String removeFirstWord(String userCommand) {
		int i = userCommand.indexOf(' ');
		return userCommand.substring(i);
	}

	private static String addContent(String fileName, String content) {
		arr.add(content.trim());
		return String.format(MESSAGE_ADD, fileName, content);
	}

	public static void sortContent(String fileName) {
		if (arr.isEmpty()) {
			System.out.println(String.format(MESSAGE_EMPTY, fileName));
		} else {
			Collections.sort(arr);
		}
		displayContent(fileName);
	}

	private static void displayContent(String fileName) {
		if (arr.isEmpty()) {
			System.out.println(String.format(MESSAGE_EMPTY, fileName));
		} else {
			for (int i = 0; i < arr.size(); i++) {
				System.out.println(String.format(MESSAGE_DISPLAY, i + 1,
						arr.get(i)));
			}
		}
	}

	private static String delContent(String fileName, String userCommand) {
		if (arr.isEmpty()) {
			return String.format(MESSAGE_ERROR);
		} else {
			int toDel = Integer.parseInt(userCommand.split(" ")[1]);
			String toRem = arr.remove(toDel - 1);
			return String.format(MESSAGE_DELETE, fileName, toRem);
		}
	}

	private static String clear(String fileName) {
		arr.clear();
		return String.format(MESSAGE_CLEAR, fileName);
	}

	private static String searchContent(String toCheck) {
		String[] temp;
		String str = "", checkArr = "";
		for (int i = 0; i < arr.size(); i++) {
			checkArr = arr.get(i).replaceAll("[^a-z]", " ");
			temp = checkArr.split(" ");

			for (int k = 0; k < temp.length; k++) {
				String checkArrWord = temp[k];
				if (toCheck.trim().equals(checkArrWord)) {
					str += String.format(MESSAGE_DISPLAY, i + 1, arr.get(i))
							+ "\n";
					break;
				}
			}
			temp = null;
			checkArr = "";
		}
		if (str.equals("")) {
			return String.format(MESSAGE_SEARCH_ERROR + "\n", toCheck);
		}
		return str;
	}

	private static void writeFile(File file) throws IOException {
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < arr.size(); i++) {
			br.write(arr.get(i) + "\n");
		}
		br.close();
	}
}
