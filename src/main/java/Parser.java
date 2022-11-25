import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    Map<String, String> progerInfo = new HashMap<>();
    Set<String> allFiles = new HashSet<>();
    Set<Commit> allCommits = new HashSet<>();
    int COUNTER;

    public void newParse(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_16))) {
            String line = reader.readLine();
            line = reader.readLine();
            while (line != null) {
                List<String> commitInfo = new ArrayList<>();
                while (line != null && !(line.matches("commit [\\d\\w]{40}") && line.length() == 47)) {
                    commitInfo.add(line);
                    line = reader.readLine();
                }
                line = reader.readLine();
                parseToObject(commitInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseToObject(List<String> commitInfo) {
        List<String> files = commitInfo.stream()
                .filter(l -> l.contains("|") && !l.toLowerCase().contains("bin"))
                .map(l -> l.split("\\|")[0].trim())
                .collect(Collectors.toList());
        String[] authorInfo = commitInfo.get(0).split(" ");
        if (authorInfo[0].toLowerCase().contains("merge"))
            return;
        System.out.println(commitInfo);
        int amountElems = authorInfo.length;
        String authorName = authorInfo[0];
        String authorEmail = authorInfo[amountElems-1].substring(1, authorInfo[amountElems-1].length() - 1);
        progerInfo.put(authorEmail, authorName);
        String[] dateInfo = commitInfo.get(1).split(" ");
        String date = dateInfo[7] + "." + getFullMonthName(dateInfo[4]);
        Enum<CommitType> type = CommitType.NotFix;
        int commentStartIdx = 2; // where comments start
        String commentLine = commitInfo.get(commentStartIdx);
        while (commentStartIdx < commitInfo.size() && !commentLine.contains("|")) {
            commentLine = commitInfo.get(commentStartIdx);
            if (commitInfo.get(commentStartIdx).toLowerCase().contains("fix"))
                type = CommitType.Fix;
            commentStartIdx++;
        }
        for (var file : files) {
            allFiles.add(file);
            allCommits.add(new Commit(date, file,  authorName, authorEmail, type));
        }
    }

    public List<Programmer> writeToFile() {
        List<Programmer> result = new ArrayList<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("JSON.txt"))) {
            Map<String, List<Commit>> fileCommits = new HashMap<>();
            for (var proger: progerInfo.entrySet()) {
                result.add(new Programmer(proger.getValue(), proger.getKey()));
            }
            for (var commit: allCommits) {
               var r = result.stream().filter(pr -> pr.getEmail().equals(commit.getAuthEmail())).toList().get(0);
               r.setCommits(commit);
//            for (var proger: progerInfo.entrySet()) {
//                int COUNTER = 0;
//                System.out.println("+AUTHOR");
//                for (var file: allFiles) {
//                    List<Commit> commits = new ArrayList<>();
//                    for (var commit: allCommits) {
//
//                        if (commit.getFileName().equals(file) && commit.getAuthEmail().contains(proger.getKey())) {
//                            commits.add(commit);
//                        }
//                    }
//                    fileCommits.put(file, commits);
//                }
//                result.add(new Programmer(proger.getValue(), proger.getKey(), fileCommits));
            }
            writer.write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

        private String getFullMonthName (String monthShortName) {
            switch (monthShortName) {
                case "Jan":
                case "Feb":
                case "Mar":
                    return "01";
                case "Apr":
                case "May":
                case "Jun":
                    return "02";
                case "Jul":
                case "Aug":
                case "Sep":
                    return "03";
                case "Oct":
                case "Nov":
                case "Dec":
                    return "04";
                default:
                    return "year";
            }
        }
    }

