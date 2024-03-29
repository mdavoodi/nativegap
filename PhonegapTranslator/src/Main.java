import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	private static StringBuffer buff;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();

		File htmlFile = new File("tmp/index_hang.html");
		try {
			doc = Jsoup.parse(htmlFile, "UTF-8", "");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String sourceFile = "";
		String path = "tmp/Hangman.java";

		try {
			sourceFile = readFile(path, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (sourceFile.equals(""))
			return;

		String view = "ac = this;\n";
		String global = "";
		String setters = "";
		ArrayList<Elem> list = main.parseFile();
		if (table)
			view += main.generateTableView();

		for (int i = 0; i < list.size(); i++) {
			Elem elem = list.get(i);
			view += elem.generatePostJava();
			if (!(elem instanceof Table)) {
				view += "layout.addView(" + elem.getName() + ");\n";
			}
			global += elem.generateGlobals();
			setters += elem.generateSetters();
		}

		view += "setContentView(layout);\n";
		buff = new StringBuffer(sourceFile);
		buff.insert(buff.indexOf("{") + 1, "\npublic CordovaActivity ac;");
		buff.insert(buff.indexOf("{") + 1, global);

		int index = buff.indexOf("public void onCreate");
		int insert = 0;
		if (index != -1) {
			int brCount = 0;
			for (int i = index; i < buff.length(); i++) {
				if (buff.charAt(i) == '{')
					brCount++;
				if (buff.charAt(i) == '}') {
					if (brCount > 1)
						brCount--;
					else {
						insert = i;
						break;
					}
				}
			}
		} else {
			System.out.println("onCreate not found. java file not valid");
		}
		buff.insert(insert, view);
		System.out.println(buff);
		
		System.out.println(setters);

		int start = path.lastIndexOf("/") + 1;
		int end = path.lastIndexOf(".");
		end = start < end ? end : path.length();
		String name = path.substring(start, end);

		start = buff.indexOf("package");
		end = buff.indexOf(";", start);
		String pckge = buff.substring(start, end).split(" ")[1];
		String receiver = "package org.apache.cordova.plugin;\n"
				+ "import org.apache.cordova.CordovaPlugin;\n"
				+ "import org.apache.cordova.CallbackContext;\n"
				+ "import org.json.JSONArray;import org.json.JSONException;\n"
				+ "import "
				+ pckge
				+ "."
				+ name
				+ ";\n"
				+ "public class JavascriptReceiver extends CordovaPlugin {\n"
				+ "@Override\n"
				+ "public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {\n";

		String jsReceiver = "window.javascriptreceiver = function(str, callback) {\n";

		for (Elem elem : list) {
			if (elem instanceof Table) {

				ArrayList<Elem> elements = ((Table) elem).getElements();

				for (Elem e : elements) {
					if (e instanceof TextView) {

						TextView v = (TextView) e;
						String statement = "if (action.equals(\""
								+ v.id
								+ "\")) {final String message = args.getString(0);\n"
								+ "final " + name + " view = (" + name
								+ ") this.cordova.getActivity();\n"
								+ "view.runOnUiThread(new Runnable() {\n"
								+ "@Override\n" + "public void run() {\n"
								+ "view." + v.getName()
								+ ".setText(message);\n" + "}\n" + "});\n"
								+ "return true;\n" + "}\n";
						receiver += statement;

						jsReceiver += "var val = $(\"input[name="
								+ v.id
								+ "]\").val();\n"
								+ "cordova.exec(callback, function(err) {\n"
								+ "callback('Nothing to echo.');}, \"JavascriptReceiver\", \""
								+ v.id + "\", [val]);\n";
					}
				}
			}
		}

		receiver += "return false;\n}\n}\n";

		System.out.println(receiver);

		jsReceiver += "};";

		System.out.println(jsReceiver);

	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	public ArrayList<Elem> parseFile() {

		ArrayList<Elem> list = new ArrayList<Elem>();

		Elements elms = doc.select("*");

		for (Element elem : elms) {
			if (elem.tagName().equals("table")) {
				list.addAll(addTable(elem));
			} else {
				Elem ret = parseElement(elem);
				if (ret != null && !list.contains(ret)) {
					list.add(ret);
				}
			}
		}

		return list;
	}

	public Elem parseElement(Element elem) {
		String name = elem.attr("Name");
		if (!elem.attr("onclick").equals("")) {
			String onclick = elem.attr("onclick");
			String value = elem.attr("value");
			return new Button(onclick, value, name);
		} else if (!name.equals("") && changesValue(name)) {
			return new TextView(name);
		} else if (!name.equals("") && elem.attr("type").equals("text")) {
			return new TextView(name);
		} else if (!elem.text().equals("") && hasOwnText(elem)) {
			return new TextLabel(name, elem.text());
		}
		return null;
	}

	public ArrayList<Elem> addTable(Element table) {
		ArrayList<Elem> list = new ArrayList<Elem>();

		ArrayList<ArrayList<Elem>> rows = new ArrayList<ArrayList<Elem>>();

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
		table.remove();

		list.add(new Table(rows, table.nodeName()));

		return list;
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

	public static boolean hasOwnText(Element element) {
		return !element.ownText().isEmpty();
	}

	public abstract class Elem {
		String name;
		String id;

		public abstract String generateGlobals();

		public abstract String generatePostJava();

		public abstract String generateSetters();

		public String getName() {
			return this.name;
		}

		@Override
		public boolean equals(Object obj) {

			if (obj instanceof Table) {
				return obj.equals(this);
			} else if (obj instanceof Elem) {
				return id.equals(((Elem) obj).id);
			}

			return false;
		}
	}

	public class Button extends Elem {
		String onclick;
		String value;

		public Button(String onclick, String value, String id) {
			this.value = value;
			this.onclick = onclick;
			this.name = "null";
			this.id = id;
		}

		@Override
		public String generatePostJava() {
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
		public String generateGlobals() {
			return "";
		}

		@Override
		public String generateSetters() {
			return "";
		}
	}

	public class Table extends Elem {

		ArrayList<ArrayList<Elem>> elements;
		String globals = "";

		public Table(ArrayList<ArrayList<Elem>> elements, String id) {
			this.elements = elements;
			this.name = "null";
			this.id = id;
		}

		public String generatePostJava() {
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
					ret += row.get(j).generatePostJava();
					name = row.get(j).getName();
					ret += "tableRow" + num + ".addView(" + name + ");\n";
					this.globals += row.get(j).generateGlobals();
				}
				ret += "layout.addView(tableRow" + num + ");\n";
			}

			return ret;
		}

		public String generateGlobals() {
			return globals;
		}

		public ArrayList<Elem> getElements() {
			ArrayList<Elem> list = new ArrayList<Elem>();
			for (ArrayList<Elem> row : this.elements) {
				for (Elem elem : row) {
					list.add(elem);
				}

			}

			return list;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Table)
				super.equals(obj);
			if (obj instanceof Elem) {
				for (Elem e : this.getElements()) {
					if (e.equals(obj))
						return true;
				}
			}
			return false;
		}

		@Override
		public String generateSetters() {
			String ret = "";
			ArrayList<Elem> elements = getElements();
			for (int i = 0; i < elements.size(); i++) {
				ret += elements.get(i).generateSetters();
			}

			return ret;
		}

	}

	class TextView extends Elem {

		public TextView(String id) {
			this.name = "null";
			this.id = id;
		}

		@Override
		public String generatePostJava() {
			String ret = "text" + variableIndex + " = new EditText(this);\n"
					+ "text" + variableIndex + ".setWidth(100);\n";
			this.name = "text" + variableIndex;
			variableIndex++;
			return ret;
		}

		@Override
		public String generateGlobals() {
			return "public EditText " + getName() + ";\n";
		}

		@Override
		public String generateSetters() {
			String ret = "String " + name + "val = " + name + ".getText().toString();\n" 
					+ "ac.loadUrl(\"javascript:$('[name=\\\"" + this.id
					+ "\\\"]').val(\\\"\" + " + name + "val + \"\\\");\");\n";
			return ret;
		}

	}

	class TextLabel extends Elem {
		String text;

		public TextLabel(String id, String text) {
			this.name = "null";
			this.id = id;
			this.text = text;
		}

		@Override
		public String generateGlobals() {
			return "";
		}

		@Override
		public String generatePostJava() {

			String ret = "TextView text" + variableIndex
					+ " = new TextView(this);\n" + "text" + variableIndex
					+ ".setWidth(100);\n" + "text" + variableIndex
					+ ".setText(\"" + text + "\");\n";
			this.name = "text" + variableIndex;
			variableIndex++;
			return ret;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof TextLabel) {
				return this.text.equals(((TextLabel) obj).text);
			} else
				return obj.equals(this);
		}

		@Override
		public String generateSetters() {
			return "";
		}

	}
}
