package Importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVImporter {

	private ArrayList<String> list = new ArrayList<String>();

	public CSVImporter(String address) throws FileNotFoundException {
		
		Scanner scanner = new Scanner(new File(address));
		while (scanner.hasNext()) {
			String s = scanner.next();
			System.out.println(s);
			list.add(s);
		}
		
		scanner.close();

	}
	
	public ArrayList<String> getCSVList() {
		return list;
	}
}
