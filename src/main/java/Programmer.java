import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Programmer {

    /**
     * Имя программиста
     */
    private String Name;

    /**
     * Почта программиста
     */
    private String Email;

    /**
     * Словарь: ключ: название файла; значение: список коммитов, в котором был затронут этот файл
     */
    private Map<String, List<Commit>> Commits = new HashMap<>();

    Programmer(String name, String email) {
        this.Name = name;
        this.Email = email;
    }

    public void setCommits(Commit commit) {
        if (!Commits.containsKey(commit.getFileName())) {
            Commits.put(commit.getFileName(), new ArrayList<>());
        }
        Commits.get(commit.getFileName()).add(commit);
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public Map<String, List<Commit>> getFileCommits() {
        return Commits;
    }

}
