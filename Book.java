public class Book extends LibraryItem implements Borrowable{
    private String author;
    public Book(String id, String title, String author){
        super(id,title);
        this.author=author;
    }
    public String getAuthor(){
        return this.author;
    }
    @Override 
    public double getFinePerDay(){
        return 2;
    }
    @Override 
    public String getType(){
        return "BOOK";
    }
}
