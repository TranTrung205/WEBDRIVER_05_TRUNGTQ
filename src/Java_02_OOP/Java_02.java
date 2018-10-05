package Java_02_OOP;

import org.testng.annotations.Test;

public class Java_02 {
	@Test
	public void java_02() {
		String automation = "Automation 01 Testing 345 Tutorials Online 123456";
		// Đếm số lượng ý tự là "a" trong chuỗi (kết quả: 3)
		int count = 0, numberCount = 0;
		int size = automation.length();
		for (int i = 0; i < size; i++) {
			if (automation.charAt(i) == 'a') {
				count++;
			}
		}
		System.out.println("01. Number of character a: " + count);

		// Kiểm tra chuỗi có chứa từ "Testing" hay không (kết quả: true)

		boolean compare = automation.contains("Testing");
		System.out.println("02. Result: " + compare);

		// Kiểm tra chuỗi có bắt đầu bằng từ "Automation" hay không (kết quả: true)

		boolean firstString = automation.startsWith("Automation");
		System.out.println("03. String starts with word Automation: " + firstString);

		// Kiểm tra chuỗi có kết thúc bằng từ "Online" hay không (kết quả: false)
		boolean endString = automation.endsWith("Online");
		System.out.println("04. String ends with word Online: " + endString);

		// Lấy vị trí của từ "Tutorial" trong chuỗi
		int indexTutorial = automation.indexOf("Tutorial");
		System.out.println("05. Index of word Tutorial: " + indexTutorial);

		// Thay thế từ Online bằng Offline => Automation 01 Testing 345 Tutorials aa
		// Offline 123456 a
		String newString = automation.replace("Online", "Offline");
		System.out.println("06. String after replaces: " + newString);

		// Đếm số ký tự là số có trong chuỗi
		for (int i = 0; i < size; i++) {
			if (Character.isDigit(automation.charAt(i))) {
				numberCount++;
			}
		}
		System.out.println("07. Total of number in String: " + numberCount);
	}
}