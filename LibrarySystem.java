import java.util.HashMap;
import java.util.Map;

public class LibrarySystem {
    private Map<String, LibraryItem> items;
    private Map<String, Member> members;
    private Map<String, Loan> loans;   // keyed by item ID
    private int today;

    public LibrarySystem() {
        items = new HashMap<>();
        members = new HashMap<>();
        loans = new HashMap<>();
        today = 0;
    }
}