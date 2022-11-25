public class Commit {

    private String Date, FileName, authorName, authorEmail;
    Enum<CommitType> Type;

    Commit(String date, String fileName, String authorName, String authorEmail, Enum<CommitType> type) {
        this.Date = date;
        this.FileName = fileName;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.Type = type;
    }

    public String getDate() {
        return Date;
    }

    public String getFileName() {
        return FileName;
    }

    public String getType() {
        return Type.toString();
    }

    public String getAuthEmail() {
        return authorEmail;
    }

    public String getAuthName() {
        return authorName;
    }
}
