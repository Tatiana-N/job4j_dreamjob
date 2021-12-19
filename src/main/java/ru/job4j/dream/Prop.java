package ru.job4j.dream;

import java.io.IOException;
import java.util.Properties;

public class Prop {
	private static final Properties properties = new Properties();
	
	static {
		try {
			properties.load(Prop.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			throw new IllegalArgumentException("Wrong with properties file");
		}
	}
	
	public static String getDataFromProperties(String string) {
		return properties.getProperty(string);
	}
}
