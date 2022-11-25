
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static String fileName = "KibTest";

    public static void main(String[] args) {
        Parser parser = new Parser();

        var allPorgers = parser.parse(fileName + ".txt").getProgrammers();
        List<Programmer> result = new ArrayList<>();
        for (var prog: allPorgers) {
            int commCounter = 0;
            var commits = prog.getFileCommits().values();
            for (var comm: commits) {
                commCounter += comm.size();
            }
           if (commCounter > 15_000) {
               result.add(prog);
           }
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("RESULT.json"), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
