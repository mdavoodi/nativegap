import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	private boolean table = false;
	private static int variableIndex = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		ArrayList<Elem> list = main.parseFile();
		for (int i = 0; i < list.size(); i++) {
			Elem elem = list.get(i);
			System.out.println(elem.generateJava(variableIndex++));
		}
	}

	public ArrayList<Elem> parseFile() {
		File input = new File("tmp/index.html");
		Document doc;
		try {
			doc = Jsoup.parse(input, "UTF-8", "");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		ArrayList<Elem> list = new ArrayList<Elem>();

		ArrayList<ArrayList<Elem>> rows = new ArrayList<ArrayList<Elem>>();
		for (Element table : doc.select("table")) {
			ArrayList<Elem> tablerow = new ArrayList<Elem>();
			for (Element row : table.select("tr")) {
				Elements tds = row.select("td");
				for (int i = 0; i < tds.size(); i++) {
					Elem elem = parseElement(tds.get(i));
					if (elem != null) {
						tablerow.add(elem);
					}
				}
			}
			rows.add(tablerow);
		}

		list.add(new Table(rows));

		return list;
	}

	public Elem parseElement(Element elem) {
		if (!elem.attr("onclick").equals("")) {
			String onclick = elem.attr("onclick");
			String value = elem.attr("value");
			return new Button(onclick, value);

		}
		return null;

	}

	public String generateTableView() {
		String ret = "TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout."
				+ "LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);\n"
				+ "TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, "
				+ "TableRow.LayoutParams.WRAP_CONTENT);\n"
				+ "TableLayout layout = new TableLayout(this);\n"
				+ "layout.setLayoutParams(tableParams);\n";

		return ret;

	}

	public interface Elem {
		public String generateJava(int num);
	}

	public class Button implements Elem {
		String onclick;
		String value;

		public Button(String onclick, String value) {
			this.value = value;
			this.onclick = onclick;
		}

		@Override
		public String generateJava(int num) {
			String ret = "Button button"
					+ num
					+ " = new Button(this);\n"
					+ "button"
					+ num
					+ ".setText(\""
					+ this.value
					+ "\");\n"
					+ "RelativeLayout.LayoutParams params"
					+ num
					+ " = new RelativeLayout.LayoutParams(60, 50);\n"
					+ "params"
					+ num
					+ ".leftMargin = 100;\n"
					+ "layout.addView(button"
					+ num
					+ ", params"
					+ num
					+ ");\n"
					+ "button"
					+ num
					+ ".setOnClickListener(new View.OnClickListener() {\n"
					+ "	@Override\n"
					+ "	public void onClick(View v) {\n"
					+ "       ac.loadUrl(\"javascript:"
					+ this.onclick
					+ ";\");\n"
					+ "       ac.loadUrl(\"javascript:window.javascriptreceiver(\\\"echome\\\", function(echoValue) {alert(echoValue == \\\"echome\\\");});\");\n"
					+ "	}\n" + "});\n";
			return ret;
		}
	}

	public class Table implements Elem {

		ArrayList<ArrayList<Elem>> elements;

		public Table(ArrayList<ArrayList<Elem>> elements) {
			this.elements = elements;
		}

		@Override
		public String generateJava(int num) {
			String ret = "";
			for (int i = 0; i < this.elements.size(); i++) {
				ret += "TableRow tableRow" + (num + i) + "= new TableRow(this);\n"
						+ "tableRow" + (num + i) + ".setLayoutParams(tableParams);\n";
				variableIndex= num+i+1;
				ArrayList<Elem> row = this.elements.get(i);
				for (int j = 0; j < row.size(); j++) {
					ret += row.get(j).generateJava(variableIndex++);
				}
			}
			return ret;
		}

	}
}
