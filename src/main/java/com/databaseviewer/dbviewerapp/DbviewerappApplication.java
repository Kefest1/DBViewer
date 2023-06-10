package com.databaseviewer.dbviewerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@SpringBootApplication
@RestController
@RequestMapping("")
public class DbviewerappApplication {

	public static void main(String[] args) {
//		System.out.println(DatabaseConnector.getDatabaseName());
		 SpringApplication.run(DbviewerappApplication.class, args);
	}

	@GetMapping("/test")
	public String test1() {
		return "123";
	}

	@GetMapping("/works")
	public String test2() {
		return "TESTTEST";
	}

	@GetMapping("")
	public String test() {
		StringBuilder stringBuilder = new StringBuilder("<h4>Currently using " + DatabaseConnector.getDatabaseName() + " database.</h4>\n");
		HashSet<String> hashSet = DatabaseConnector.getDatabaseInfo();

		stringBuilder.append("<form id=\"myForm\">");
		stringBuilder.append("<select id=\"tableNameSelect\">");
		hashSet.forEach(option -> stringBuilder.append("<option>").append(option).append("</option>"));
		stringBuilder.append("</select>");
		stringBuilder.append("</form>");

		stringBuilder.append("<button type=\"button\" onclick=\"gotoTable()\">Pokaż zawartość tabeli</button>");

		// stringBuilder.append("<div id=\"displayDiv\"></div>");

		stringBuilder.append("<script>");
		stringBuilder.append("function gotoTable() {");
		stringBuilder.append("	var tableName = document.getElementById('tableNameSelect').value;");
		stringBuilder.append("	window.location.href = '/tables/' + tableName;");
		stringBuilder.append("}");
		stringBuilder.append("</script>");

		return stringBuilder.toString();
	}

	@GetMapping("/tables/{tableName}")
	public String getTableContent(@PathVariable String tableName) {
		StringBuilder stringBuilder = new StringBuilder("<html>");
		String content = DatabaseConnector.getTableContent(tableName);
		if (content == null) {
			stringBuilder.append("<h4>Table \"").append(tableName).append("\" does not exists</h4>");
			stringBuilder.append("</html>");
			return stringBuilder.toString();
		}

		stringBuilder.append(content);

		stringBuilder.append("<br><br>");
		stringBuilder.append("<button type=\"button\" onclick=\"goToRoot()\">goToRoot</button>");

		stringBuilder.append("<script>");
		stringBuilder.append("function goToRoot() {");
		stringBuilder.append("	window.location.href = '/';");
		stringBuilder.append("}");
		stringBuilder.append("</script>");

		stringBuilder.append("<h2>Alter database content</h2>");
		stringBuilder.append("</html>");

		stringBuilder.append("<button type=\"button\" onclick=\"alterDatabase()\">goToRoot</button>");

		stringBuilder.append("<br><br>");
		stringBuilder.append("<input type=\"text\" id=\"myTextField\">");

		stringBuilder.append("<script>");
		stringBuilder.append("function alterDatabase() {");
		stringBuilder.append("	window.location.href = '/';");
		stringBuilder.append("}");
		stringBuilder.append("</script>");

		return stringBuilder.toString();
	}

}
