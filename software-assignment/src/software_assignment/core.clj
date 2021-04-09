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


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
