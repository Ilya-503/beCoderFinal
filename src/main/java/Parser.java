import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {    // 0 - fix, 1 - NOT fix


    public void newParse(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            while (!line.isEmpty()) {
                line = reader.readLine();
                List<String> commitInfo = new ArrayList<>();
                while (!(line.matches("commit [\\d\\w]{40}") && line.length() == 47)) {
                    commitInfo.add(line);
                    line = reader.readLine();
                }
                parseToObject(commitInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseToObject(List<String> commitInfo) {
        List<String> files = commitInfo.stream()
                .filter( l -> l.contains("|"))
                .map(l -> l.split("\\|")[0].trim())
                .collect(Collectors.toList());
        String[] authorInfo = commitInfo.get(0).split(" ");
        String authorName = authorInfo[1],
                authorEmail = authorInfo[2];
        String[] dateInfo = commitInfo.get(1).split(" ");
        String date = dateInfo[7] + "." + getFullMonthName(dateInfo[4]);
        Enum<CommitType> type = CommitType.NotFix;
        int commentStartIdx = 3; // where comments start
        String commentLine = commitInfo.get(commentStartIdx);
        while (!commentLine.contains("|") && !commentLine.toLowerCase().contains("bin")) {
            if (commitInfo.get(commentStartIdx).toLowerCase().contains("fix"))
                type = CommitType.Fix;
            commentStartIdx++;
            commentLine = commitInfo.get(commentStartIdx);
        }
        for (var file: files) {
            System.out.println(new Commit(date, file, type));
        }
    }

    public void parseTo(String path) {
        int type = 1;
        List<String> changedFiles = new ArrayList<>();
       try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
           String line = reader.readLine();
           if (line.matches("commit [\\d\\w]{40}") && line.length() == 47) {
               String[] authorInfo = reader.readLine().split(" ");
               String authorName = authorInfo[0],
                       authorEmail = authorInfo[1];
               String[] dateInfo = reader.readLine().split(" ");
               String date = dateInfo[7] + "." + dateInfo[4];

               String s = reader.readLine();
               while (!s.equals("|")) {
                   if (s.toLowerCase().contains("fix"))
                       type = 0;
                   if (s.equals("|"))
                       changedFiles.add(s.split("|")[0].trim());
               }
               s = reader.readLine();
               while (s.contains("|")) {
                   changedFiles.add(s.split("|")[0].trim());
               }
               reader.readLine();

           }
       } catch (IOException e) {
           e.printStackTrace();
       }
    }


    private String getFullMonthName(String monthShortName) {
        switch (monthShortName) {
            case "Jan": return "01";
            case "Feb": return "02";
            case "Mar": return "03";
            case "Apr": return "04";
            case "May": return "05";
            case "Jun": return "06";
            case "Jul": return "07";
            case "Aug": return "08";
            case "Sep": return "09";
            case "Oct": return "10";
            case "Nov": return "11";
            case "Dec": return "12";
            default: return "year";
        }
    }
}
