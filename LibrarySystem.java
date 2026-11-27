import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
    public void addItem(LibraryItem item) {
        items.put(item.getId(), item);
    }
    public void addMember(Member member) {
        members.put(member.getId(), member);
    }
    public List<LibraryItem> getInventory() {
        List<LibraryItem> inventory = new ArrayList<>(items.values());
        inventory.sort(Comparator.comparing(LibraryItem::getId));
        return inventory;
    }
    public Loan getLoan(String itemId) {
        return loans.get(itemId);
    }
}