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
    private LibraryItem findItem(String itemId) throws ItemNotFoundException {
        LibraryItem item = items.get(itemId);
        if (item == null) {
            throw new ItemNotFoundException(itemId);
        }
        return item;
    }
    private Member findMember(String memberId) throws MemberNotFoundException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new MemberNotFoundException(memberId);
        }
        return member;
    }
     public int borrowItem(String memberId, String itemId) throws LibraryException {
        LibraryItem item = findItem(itemId);
        Member member = findMember(memberId);
        if (!(item instanceof Borrowable)) {
            throw ItemNotAvailableException.notBorrowable(itemId);
        }
        if (loans.containsKey(itemId)) {
            throw ItemNotAvailableException.onLoan(itemId);
        }
        if (countLoans(memberId) >= member.getMaxItems()) {
            throw new BorrowLimitExceededException(memberId, member.getMaxItems());
        }
        Loan loan = new Loan(member, item, today);
        loans.put(itemId, loan);
        return loan.getDueDay();
    }
    public double returnItem(String itemId) throws LibraryException {
        findItem(itemId);   
        Loan loan = loans.get(itemId);
        if (loan == null) {
            throw new ItemNotOnLoanException(itemId);
        }
        double fine = loan.calculateFine(today);
        loans.remove(itemId);
        return fine;
    }
}