public class ReservationNotAllowedException extends LibraryException {
    private ReservationNotAllowedException(String message) {
        super(message);
    }

    public static ReservationNotAllowedException available(String itemId) {
        return new ReservationNotAllowedException(itemId + " is available");
    }

    public static ReservationNotAllowedException alreadyHolds(String memberId, String itemId) {
        return new ReservationNotAllowedException(memberId + " already has " + itemId);
    }

    public static ReservationNotAllowedException alreadyReserved(String memberId, String itemId) {
        return new ReservationNotAllowedException(memberId + " already reserved " + itemId);
    }
}