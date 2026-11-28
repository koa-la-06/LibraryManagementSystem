import java.util.List;

public class CommandProcessor {
    private LibrarySystem library;
    private InputValidator validator;

    public CommandProcessor() {
        library = new LibrarySystem();
        validator = new InputValidator();
    }
    public void processCommand(String command) throws LibraryException {
        String[] parts = validator.validateAndSplit(command);
        validator.validateCommand(parts);
        switch (parts[0]) {
            case "ADD_BOOK":
                library.addItem(new Book(parts[1], parts[2], parts[3]));
                System.out.println("OK");
                break;
            case "ADD_MAGAZINE":
                library.addItem(new Magazine(parts[1], parts[2], parts[3]));
                System.out.println("OK");
                break;
            case "ADD_REFERENCE":
                library.addItem(new ReferenceBook(parts[1], parts[2], parts[3]));
                System.out.println("OK");
                break;
            case "ADD_MEMBER":
                addMember(parts[1], parts[2], parts[3]);
                System.out.println("OK");
                break;
            case "INVENTORY":
                printInventory();
                break;
            case "BORROW": {
                int dueDay = library.borrowItem(parts[1], parts[2]);
                System.out.println("OK due on day " + dueDay);
                break;
            }
            case "RETURN": {
                ReturnResult result = library.returnItem(parts[1]);
                String line = "OK fine " + (long) result.getFine();
                Loan issued = result.getIssuedLoan();
                if (issued != null) {
                    line += ", issued to " + issued.getMember().getId()+ " due on day " + issued.getDueDay();
                }
                System.out.println(line);
                break;
            }
            case "RESERVE": {
                library.reserveItem(parts[1], parts[2]);
                System.out.println("OK reserved");
                break;
            }
            case "ADVANCE": {
                int days = validator.validateInteger(parts[1]);
                library.advanceDays(days);
                System.out.println("OK today is day " + library.getToday());
                break;
            }
            case "SEARCH": {
                List<LibraryItem> found = library.search(parts[1]);
                if (found.isEmpty()) {
                    System.out.println("NONE");
                } 
                else {
                    for (LibraryItem item : found) {
                        System.out.println(item.getId() + "|" + item.getType() + "|" + item.getTitle());
                    }
                }
                break;
            }
            case "LOANS": {
                List<Loan> memberLoans = library.getMemberLoans(parts[1]);
                if (memberLoans.isEmpty()) {
                    System.out.println("NONE");
                } else {
                    for (Loan loan : memberLoans) {
                        System.out.println(loan.getItem().getId() + "|" + loan.getItem().getTitle()+ "|" + loan.getDueDay());
                    }
                }
                break;
            }
            case "OVERDUE": {
                List<Loan> overdue = library.getOverdueLoans();
                if (overdue.isEmpty()) {
                    System.out.println("NONE");
                } 
                else {
                    int today = library.getToday();
                    for (Loan loan : overdue) {
                        System.out.println(loan.getItem().getId() + "|" + loan.getMember().getId()+ "|" + loan.getDaysLate(today)+ "|" + (long) loan.calculateFine(today));
                    }
                }
                break;
            }
            default:
                throw new InvalidInputException("command not implemented yet: " + parts[0]);
        }
    }
    private void addMember(String id, String name, String type) throws InvalidInputException {
        switch (type) {
            case "STUDENT":
                library.addMember(new Student(id, name));
                break;
            case "STAFF":
                library.addMember(new Staff(id, name));
                break;
            case "FACULTY":
                library.addMember(new Faculty(id, name));
                break;
            default:
                throw new InvalidInputException("unknown member type " + type);
        }
    }
    private void printInventory() {
        List<LibraryItem> inventory = library.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("NONE");
            return;
        }
        for (LibraryItem item : inventory) {
            String status;
            if (!(item instanceof Borrowable)) {
                status = "NOT_BORROWABLE";
            } else {
                Loan loan = library.getLoan(item.getId());
                if (loan == null) {
                    status = "AVAILABLE";
                } else {
                    status = "ON_LOAN|" + loan.getMember().getId();
                }
            }
            System.out.println(item.getId() + "|" + item.getType() + "|" + status);
        }
    }
}