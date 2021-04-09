(ns software-assignment.core-test
  (:require [clojure.test :refer :all]
            [software-assignment.core :refer :all]))

(deftest get-delimiter-test
  (testing "Function should return a string literal of the detected delimiter in a csv file.")
  (is (= "\\|"
         (get-delimiter "Robert|Owens|Robert@email.com|Red|1999-09-21"))))