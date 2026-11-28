#!/bin/bash
mkdir -p tests
cd tests

# --- Error: ItemNotFoundException ---
cat > err_item_not_found.txt <<'EOF'
ADD_MEMBER|S1|Ananya Rao|STUDENT
BORROW|S1|B9
RETURN|B9
EOF
cat > err_item_not_found_expected.txt <<'EOF'
OK
ERROR item B9 not found
ERROR item B9 not found
EOF

# --- Error: MemberNotFoundException ---
cat > err_member_not_found.txt <<'EOF'
ADD_BOOK|B1|Algorithms|Cormen
BORROW|X9|B1
LOANS|X9
EOF
cat > err_member_not_found_expected.txt <<'EOF'
OK
ERROR member X9 not found
ERROR member X9 not found
EOF

# --- Error: ItemNotAvailableException (already on loan) ---
cat > err_item_on_loan.txt <<'EOF'
ADD_BOOK|B1|Algorithms|Cormen
ADD_MEMBER|S1|Ananya Rao|STUDENT
ADD_MEMBER|T1|Ravi Kumar|STAFF
BORROW|S1|B1
BORROW|T1|B1
BORROW|S1|B1
EOF
cat > err_item_on_loan_expected.txt <<'EOF'
OK
OK
OK
OK due on day 14
ERROR B1 is on loan
ERROR B1 is on loan
EOF

# --- Error: ItemNotAvailableException (not borrowable) ---
cat > err_not_borrowable.txt <<'EOF'
ADD_REFERENCE|R1|Mahabharata|Vyasa
ADD_MEMBER|F1|Meera Iyer|FACULTY
BORROW|F1|R1
INVENTORY
EOF
cat > err_not_borrowable_expected.txt <<'EOF'
OK
OK
ERROR R1 cannot be borrowed
R1|REFERENCE|NOT_BORROWABLE
EOF

# --- Error: ItemNotOnLoanException ---
cat > err_not_on_loan.txt <<'EOF'
ADD_BOOK|B1|Algorithms|Cormen
ADD_REFERENCE|R1|Mahabharata|Vyasa
RETURN|B1
RETURN|R1
EOF
cat > err_not_on_loan_expected.txt <<'EOF'
OK
OK
ERROR B1 is not on loan
ERROR R1 is not on loan
EOF

# --- Error: BorrowLimitExceededException, one member of each type ---
{
  for i in $(seq -w 1 15); do echo "ADD_BOOK|B$i|Title $i|Author"; done
  echo "ADD_MEMBER|S1|Student One|STUDENT"
  echo "ADD_MEMBER|T1|Staff One|STAFF"
  echo "ADD_MEMBER|F1|Faculty One|FACULTY"
  echo "BORROW|S1|B01"; echo "BORROW|S1|B02"; echo "BORROW|S1|B03"; echo "BORROW|S1|B04"
  echo "BORROW|T1|B05"; echo "BORROW|T1|B06"; echo "BORROW|T1|B07"; echo "BORROW|T1|B08"; echo "BORROW|T1|B09"
  echo "BORROW|F1|B10"; echo "BORROW|F1|B11"; echo "BORROW|F1|B12"; echo "BORROW|F1|B13"; echo "BORROW|F1|B14"; echo "BORROW|F1|B15"
  echo "RETURN|B01"
  echo "BORROW|S1|B04"
  echo "LOANS|S1"
  echo "OVERDUE"
} > limit_all_types.txt
{
  for i in $(seq 1 18); do echo "OK"; done
  echo "OK due on day 14"; echo "OK due on day 14"; echo "OK due on day 14"
  echo "ERROR S1 has reached the limit of 3"
  echo "OK due on day 21"; echo "OK due on day 21"; echo "OK due on day 21"; echo "OK due on day 21"
  echo "ERROR T1 has reached the limit of 4"
  for i in 1 2 3 4 5; do echo "OK due on day 30"; done
  echo "ERROR F1 has reached the limit of 5"
  echo "OK fine 0"
  echo "OK due on day 14"
  echo "B02|Title 02|14"; echo "B03|Title 03|14"; echo "B04|Title 04|14"
  echo "NONE"
} > limit_all_types_expected.txt

# --- Extra: fine boundary (due day vs one day late) ---
cat > fine_boundary.txt <<'EOF'
ADD_BOOK|B1|Algorithms|Cormen
ADD_MAGAZINE|M1|Resonance|Oct 2026
ADD_MEMBER|S1|Ananya Rao|STUDENT
BORROW|S1|B1
BORROW|S1|M1
ADVANCE|14
OVERDUE
RETURN|B1
ADVANCE|1
OVERDUE
RETURN|M1
EOF
cat > fine_boundary_expected.txt <<'EOF'
OK
OK
OK
OK due on day 14
OK due on day 14
OK today is day 14
NONE
OK fine 0
OK today is day 15
M1|S1|1|1
OK fine 1
EOF

# --- Extra: empty reports ---
cat > empty_reports.txt <<'EOF'
ADD_MEMBER|S1|Ananya Rao|STUDENT
INVENTORY
SEARCH|x
OVERDUE
LOANS|S1
EOF
cat > empty_reports_expected.txt <<'EOF'
OK
NONE
NONE
NONE
NONE
EOF

echo "Test files created in tests/"