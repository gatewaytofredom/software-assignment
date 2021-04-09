(ns software-assignment.core-test
  (:require [clojure.test :refer :all]
            [software-assignment.core :refer :all]))

(def expected-2d-vector
  [["Owens" "Robert" "bob@email" "red" "1999-09-21"] ["Hodlofski" "Nic" "nic@email" "black" "2000-06-09"] 
   ["Chen" "Evans" "evans@email" "green" "2001-01-02"] ["Zim" "Apple" "zim@email" "brown" "1980-01-01"] 
   ["A" "john" "zim@email" "pink" "1000-02-02"]])

(def test-sort-vector
  [["Washington" "George" "Washington@email.com" "Green" "1732-2-22"] 
   ["Adams" "John" "Adams@email.com" "Red" "1735-10-30"]])

(deftest get-delimiter-test
  (testing "Function should return a string literal of the detected delimiter in a csv file.")
  (is (= "\\|"
         (get-delimiter "Robert|Owens|Robert@email.com|Red|1999-09-21"))))

(deftest csv-to-2d-vector-test
  (testing "Function should return a 2d vector where each element represents one entry in a csv file.")
  (is (= expected-2d-vector
         (csv-to-2d-vector "resources/test.csv"))))

(deftest sort-last-name-test
  (testing "Function should return vector containing vectors sorted last name decending.")
  (is (= [["Adams" "John" "Adams@email.com" "Red" "1735-10-30"] ["Washington" "George" "Washington@email.com" "Green" "1732-2-22"]])
      (sort-last-name test-sort-vector)))

(deftest sort-birth-date-test
  (testing "Function should return vector containing vectors sorted birth date ascending.")
  (is (= [["Washington" "George" "Washington@email.com" "Green" "1732-2-22"] ["Adams" "John" "Adams@email.com" "Red" "1735-10-30"]])
      (sort-birth-date test-sort-vector)))