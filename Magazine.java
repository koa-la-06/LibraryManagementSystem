public class Magazine extends LibraryItem implements Borrowable{
    private String issue;
    public Magazine(String id, String title, String issue){
        super(id,title);
        this.issue=issue;
    }
    public String getIssue(){
        return this.issue;
    }
    @Override 
    public double getFinePerDay(){
        return 1;
    }
    @Override 
    public String getType(){
        return "MAGAZINE";
    }
}

