(ns software-assignment.core
  (:gen-class)
  (:require
   [clojure.string]
   [compojure.core :refer :all]
   [clojure.data.json :as json])
  (:require
   [org.httpkit.server :as server]
   [compojure.route :as route]
   [ring.util.codec]))

(defn get-delimiter
  [csv-record]
  (if (or (clojure.string/includes? csv-record "|") (clojure.string/includes? csv-record "%7C"))
    "\\|"
    (if (or (clojure.string/includes? csv-record ",") (clojure.string/includes? csv-record "%2C"))
      ","
      (if (or (clojure.string/includes? csv-record " ") (clojure.string/includes? csv-record "+"))
        " "
        (println "Unsported delimiter.")))))

(defn csv-to-2d-vector
  "Reads in a file from `file-path` and returns a 2d vector where each element represents one entry in a csv file."
  [file-path]
  (let [csv-string  (clojure.string/split (slurp file-path) #"\r\n")
        delimiter (get-delimiter (slurp file-path))]
    (mapv #(clojure.string/split % (re-pattern delimiter)) csv-string)))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
