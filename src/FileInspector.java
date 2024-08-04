import javax.swing.*; // for JFileChooser
import java.io.*; // for File, FileNotFoundException, IOException
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String record = "";
        ArrayList<String> lines = new ArrayList<>();

        String fileName = "";
        int numberOfLines = 0;
        int numberOfWords = 0;
        int numberOfCharacters = 0;

        try {
            File workingDirectory = new File(System.getProperty("user.dir"));

            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                fileName = selectedFile.getName();
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                while(reader.ready()) {
                    record = reader.readLine();
                    String[] words;
                    words = record.split(" ");
                    numberOfWords += words.length;
                    numberOfCharacters += record.length();
                    numberOfLines ++;
                    // echo to screen
                    System.out.printf("\nLine %4d %-60s ", numberOfLines, record);
                }
                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("\n\nData file read!");
                System.out.println("\nFile Summary:");
                System.out.println("File Name: " + fileName);
                System.out.println("Number of lines in the file: " + numberOfLines);
                System.out.println("Number of words in the file: " + numberOfWords);
                System.out.println("Number of characters in the file: " + numberOfCharacters);
            }
            else {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}