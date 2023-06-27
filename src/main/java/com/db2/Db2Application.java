package com.db2;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Db2Application{

	public static void main(String[] args) {		
		deleteFileContent();
		SpringApplication.run(Db2Application.class, args);
	}

	private static void deleteFileContent() {
		String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\details.txt";
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("An error occurred while clearing the file content: " + e.getMessage());
        }
	}


}
        