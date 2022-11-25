import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Парсер, который из файла .txt формата, где сохранены все комиты репозитория, преобразует его в JSON файл.
 */
public class Parser {

    Map<String, String> progerInfo = new HashMap<>();
    Set<String> allFiles = new HashSet<>();
    Set<Commit> allCommits = new HashSet<>();

    /**
     * Вытаскиваем из файла по одному комиту и парсим его.
     * @param path путь к файлу
     * @return возвращаем этот же парсер для цепочки вызовов
     */
    public Parser parse(String path) {
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
        return this;
    }

    /**
     * Парсим информацию об одном коммите, преобразуем в Java - объект.
     * @param commitInfo Лист со строками комита
     */
    private void parseToObject(List<String> commitInfo) {
        List<String> files = commitInfo.stream()
                .filter(l -> l.contains("|") && !l.toLowerCase().contains("bin"))
                .map(l -> l.split("\\|")[0].trim())
                .collect(Collectors.toList());
        String[] authorInfo = commitInfo.get(0).split(" ");
        if (authorInfo[0].toLowerCase().contains("merge"))
            return;
        int amountElems = authorInfo.length;
        String authorName = authorInfo[0];
        String authorEmail = authorInfo[amountElems - 1].substring(1, authorInfo[amountElems - 1].length() - 1);
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

    /**
     * @return все программисты репо
     */
    public List<Programmer> getProgrammers() {
        List<Programmer> result = new ArrayList<>();
            for (var programmer: progerInfo.entrySet()) {
                result.add(new Programmer(programmer.getValue(), programmer.getKey()));
            }
            for (var commit: allCommits) {
               var r = result.stream().filter(pr -> pr.getEmail().equals(commit.getAuthEmail())).toList().get(0);
               r.setCommits(commit);
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

