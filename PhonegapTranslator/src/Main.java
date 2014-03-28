import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	private static boolean table = false;
	private static int variableIndex = 0;
	private static Document doc;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();

		File input = new File("tmp/index.html");
		try {
			doc = Jsoup.parse(input, "UTF-8", "");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<Elem> list = main.parseFile();
		if(table) System.out.println(main.generateTableView());

		for (int i = 0; i < list.size(); i++) {
			Elem elem = list.get(i);
			System.out.println(elem.generateJava());
		}
		
		System.out.println("setContentView(layout);\n");
	}

	public ArrayList<Elem> parseFile() {

		ArrayList<Elem> list = new ArrayList<Elem>();

		ArrayList<ArrayList<Elem>> rows = new ArrayList<ArrayList<Elem>>();
		for (Element table : doc.select("table")) {
			this.table = true;
			for (Element row : table.select("tr")) {
				ArrayList<Elem> tablerow = new ArrayList<Elem>();
				Elements tds = row.select("td");
				for (int i = 0; i < tds.size(); i++) {
					for (int k = 0; k < tds.get(i).children().size(); k++) {
						Elem elem = parseElement(tds.get(i).child(k));
						if (elem != null) {
							tablerow.add(elem);
						}
					}
				}
				rows.add(tablerow);
			}
		}

		list.add(new Table(rows));

		return list;
	}

	public Elem parseElement(Element elem) {
		String name = elem.attr("Name");
		if (!elem.attr("onclick").equals("")) {
			String onclick = elem.attr("onclick");
			String value = elem.attr("value");
			return new Button(onclick, value);
		} else if (!name.equals("") && changesValue(name)) {
			return new TextView(name);
		}
		return null;
	}

	public boolean changesValue(String name) {
		Elements scripts = doc.select("script");
		for (Element script : scripts) {
			Pattern p = Pattern.compile(".*?(" + name
					+ ")(\\.)(value)(\\s++)(=)", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);
			Matcher m = p.matcher(script.html());
			while (m.find()) {
				return true;
			}
		}
		return false;
	}

	public String generateTableView() {
		String ret = "TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout."
				+ "LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);\n"
				+ "TableLayout layout = new TableLayout(this);\n"
				+ "layout.setLayoutParams(tableParams);\n";

		return ret;

	}

	public interface Elem {
		public String generateJava();

		public String getName();
	}

	public class Button implements Elem {
		String onclick;
		String value;
		String name;

		public Button(String onclick, String value) {
			this.value = value;
			this.onclick = onclick;
			this.name = "null";
		}

		@Override
		public String generateJava() {
			String ret = "Button button"
					+ variableIndex
					+ " = new Button(this);\n"
					+ "button"
					+ variableIndex
					+ ".setText(\""
					+ this.value
					+ "\");\n"
					+ "button"
					+ variableIndex
					+ ".setOnClickListener(new View.OnClickListener() {\n"
					+ "	@Override\n"
					+ "	public void onClick(View v) {\n"
					+ "       ac.loadUrl(\"javascript:"
					+ this.onclick
					+ ";\");\n"
					+ "       ac.loadUrl(\"javascript:window.javascriptreceiver(\\\"echome\\\", function(echoValue) {alert(echoValue == \\\"echome\\\");});\");\n"
					+ "	}\n" + "});\n";
			this.name = "button" + variableIndex;
			variableIndex++;
			return ret;
		}

		@Override
		public String getName() {
			return this.name;
		}
	}

	public class Table implements Elem {

		ArrayList<ArrayList<Elem>> elements;
		String name;

		public Table(ArrayList<ArrayList<Elem>> elements) {
			this.elements = elements;
			this.name = "null";
		}

		@Override
		public String generateJava() {
			String ret = "";
			for (int i = 0; i < this.elements.size(); i++) {
				int num = variableIndex;
				ret += "TableRow tableRow" + (variableIndex)
						+ " = new TableRow(this);\n" + "tableRow"
						+ (variableIndex) + ".setLayoutParams(tableParams);\n";
				variableIndex++;
				ArrayList<Elem> row = this.elements.get(i);
				String name;
				for (int j = 0; j < row.size(); j++) {
					ret += row.get(j).generateJava();
					name = row.get(j).getName();
					ret += "tableRow" + num + ".addView(" + name + ");\n";
				}
				ret += "layout.addView(tableRow" + num + ");\n";
			}

			return ret;
		}

		@Override
		public String getName() {
			return this.name;
		}

	}

	class TextView implements Elem {
		String id;
		String name;

		public TextView(String id) {
			this.name = "null";
			this.id = id;
		}

		@Override
		public String generateJava() {
			String ret = "text" + variableIndex + " = new TextView(this);\n"
					+ "text" + variableIndex + ".setWidth(100);\n";
			this.name = "text" + variableIndex;

			variableIndex++;
			return ret;
		}

		@Override
		public String getName() {
			return name;
		}

	}
}
