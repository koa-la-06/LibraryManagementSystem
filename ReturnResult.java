
public class ReturnResult {
    private double fine;
    private Loan issuedLoan;   // null if nobody was waiting

    public ReturnResult(double fine, Loan issuedLoan) {
        this.fine = fine;
        this.issuedLoan = issuedLoan;
    }

    public double getFine() {
        return fine;
    }

    public Loan getIssuedLoan() {
        return issuedLoan;
    }
}