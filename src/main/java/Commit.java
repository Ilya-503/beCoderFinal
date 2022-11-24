public class Commit {

    private String date, fileName;
    Enum<CommitType> type;

    Commit(String date, String fileName, Enum<CommitType> type) {
        this.date = date;
        this.fileName = fileName;
        this.type = type;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(date).append(" ")
                .append(fileName).append(" ")
                .append(type).toString();
    }

}
