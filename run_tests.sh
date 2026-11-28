#!/bin/bash
javac *.java || exit 1
pass=0; fail=0
for f in tests/*.txt; do
  case "$f" in *_expected.txt) continue ;; esac
  name="${f%.txt}"
  java Main < "$f" > /tmp/out.txt
  if diff -q /tmp/out.txt "${name}_expected.txt" > /dev/null; then
    echo "PASS $name"; pass=$((pass+1))
  else
    echo "FAIL $name"; fail=$((fail+1))
  fi
done
echo "$pass passed, $fail failed"