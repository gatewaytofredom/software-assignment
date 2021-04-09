(ns software-assignment.core
  (:gen-class)
  (:require [clojure.string]))

(defn get-delimiter
  [csv-record]
  (if (or (clojure.string/includes? csv-record "|") (clojure.string/includes? csv-record "%7C"))
    "\\|"
    (if (or (clojure.string/includes? csv-record ",") (clojure.string/includes? csv-record "%2C"))
      ","
      (if (or (clojure.string/includes? csv-record " ") (clojure.string/includes? csv-record "+"))
        " "
        (println "Unsported delimiter.")))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
