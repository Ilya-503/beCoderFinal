import java.io.*;

public class Parser {

    public void parse() {
       try (BufferedReader reader = new BufferedReader(new FileReader("PATH"))) {

       } catch (IOException e) {
           e.printStackTrace();
       }
    }

}
