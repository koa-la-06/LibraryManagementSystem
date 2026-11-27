public class ItemNotAvailableException extends LibraryException {
    private ItemNotAvailableException(String message) {
        super(message);
    }

    public static ItemNotAvailableException onLoan(String itemId) {
        return new ItemNotAvailableException(itemId + " is on loan");
    }

    public static ItemNotAvailableException notBorrowable(String itemId) {
        return new ItemNotAvailableException(itemId + " cannot be borrowed");
    }
}