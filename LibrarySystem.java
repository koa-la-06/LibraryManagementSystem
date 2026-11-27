import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibrarySystem {
    private Map<String, LibraryItem> items;
    private Map<String, Member> members;
    private Map<String, Loan> loans;  
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
        public int borrowItem(String memberId, String itemId) {
        // Check order follows the spec's error table
        LibraryItem item = items.get(itemId);
        if (item == null) {
            System.out.println("ERROR item " + itemId + " not found");              
            return -1;
        }
        Member member = members.get(memberId);
        if (member == null) {
            System.out.println("ERROR member " + memberId + " not found");          
            return -1;
        }
        if (!(item instanceof Borrowable)) {
            System.out.println("ERROR " + itemId + " cannot be borrowed");           
            return -1;
        }
        if (loans.containsKey(itemId)) {
            System.out.println("ERROR " + itemId + " is on loan");                   
            return -1;
        }
        if (countLoans(memberId) >= member.getMaxItems()) {
            System.out.println("ERROR " + memberId + " has reached the limit of "+ member.getMaxItems());                                         
            return -1;
        }

        Loan loan = new Loan(member, item, today);
        loans.put(itemId, loan);
        return loan.getDueDay();
    }
    private int countLoans(String memberId) {
        int count = 0;
        for (Loan loan : loans.values()) {
            if (loan.getMember().getId().equals(memberId)) {
                count++;
            }
        }
        return count;
    }
    public void advanceDays(int days) {
        today += days;
    }
    public int getToday() {
        return today;
    }
    public double returnItem(String itemId) {
        if (!items.containsKey(itemId)) {
            System.out.println("ERROR item " + itemId + " not found");                
            return -1;
        }
        Loan loan = loans.get(itemId);
        if (loan == null) {
            System.out.println("ERROR " + itemId + " is not on loan");               
            return -1;
        }
        double fine = loan.calculateFine(today);
        loans.remove(itemId);
        return fine;
    }
}