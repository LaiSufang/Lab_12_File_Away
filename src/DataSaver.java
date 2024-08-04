import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {
    public  static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        String firstName = "";
        String lastName = "";
        int idNumber = 0;
        String formattedIdNumber = "";
        String email = "";
        int birthYear = 0;
        String fileName = "";
        boolean isFileAlreadyExist = false;

        do {
            firstName = SafeInput.getNonZeroLenString(scanner,"Please enter the first name: ");
            lastName = SafeInput.getNonZeroLenString(scanner,"Please enter the last name: ");
            idNumber = SafeInput.getRangedInt(scanner, "Enter the ID number [1-999999]: ", 1, 999999);
            formattedIdNumber = String.format("%06d", idNumber);
            email = SafeInput.getRegExString(scanner, "Please enter the email: ", "^[A-Za-z0-9+_.-]+@(.+)$");
            birthYear = SafeInput.getRangedInt(scanner, "Enter the year of birth [1950-2024]: ", 1950, 2024);

            String record = "";
            record = firstName + ", " + lastName + ", " + formattedIdNumber + ", " + email + ", " + birthYear;
            records.add(record);
        } while (SafeInput.getYNConfirm(scanner, "Do you want to enter another record[Y/N]?"));

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file;
        do {
            fileName = SafeInput.getNonZeroLenString(scanner,"Please enter the file name: ").trim() + ".csv";
            file = Paths.get(workingDirectory.getPath(), "src", fileName);
            isFileAlreadyExist = Files.exists(file);
            if (isFileAlreadyExist) {
                System.out.println("File already exists. Please enter a different file name.");
            }
        } while (isFileAlreadyExist);

        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for(String record : records) {
                writer.write(record, 0, record.length());
                writer.newLine();  // adds the new line
            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}