
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        String fileName = "KibTest";
        parser.newParse(fileName + ".txt");
        var allPorgers = parser.writeToFile();
        List<Programmer> result = new ArrayList<>();
        for (var prog: allPorgers) {
            int counter = 0;
            var commits = prog.getFileCommits().values();
            for (var comm: commits) {
                counter += comm.size();
            }
           if (counter > 15_000) {
               result.add(prog);
           }
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(fileName + "_result.json"), result);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        JSONArray array = new JSONArray();
//        JSONObject item = new JSONObject();
//
//        json.put("course", array);
//
//        message = json.toString();
//
//        for (var proger: allPorgers) {
//            item.put("Name", proger.getName());
//            item.put("Email", proger.getEmail());
//            item.put("Commits", proger.getFileCommits());
//            array.put(item);
//        }


    }
}
