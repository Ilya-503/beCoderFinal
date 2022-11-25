import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Programmer {

    private String Name, Email;
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

//    @Override
//    public String toString() {
//        return new StringBuilder()
//                .append("Name:").append(name).append(",")
//                .append("Email:").append(email).append(",")
//                .append(fileCommits)
//                .append("}")
//                .toString();
//    }
}

/**
 * [JsonPropertyName("Name")]
 *         public string Name { get; set; }
 *
 *         [JsonPropertyName("Email")]
 *         public string Email { get; set; }
 *
 *         [JsonPropertyName("Commits")]
 *         public Dictionary<string, List<Commit>> Commits { get; set; } = new Dictionary<string, List<Commit>>();
 *
 *
 */