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

(def records (atom []))

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

(defn json-to-2d-vector
  [post-request]
  (let [json-string (get (json/read-str (slurp (post-request :body))) "data")]
    (swap! records conj (clojure.string/split json-string (re-pattern (get-delimiter json-string))))
    {:status 201
     :headers {"Content-Type" "application/json"}
     :body (json/write-str "201 success")}))


(defroutes app-routes ;(3)
  (POST "/records" [] json-to-2d-vector)
  (route/not-found "You Must Be New Here"))

(defn -main
  ([]
   (server/run-server #'app-routes {:port 8080}))
  ([file sort-method ]
   (swap! records conj (csv-to-2d-vector file))
   (server/run-server #'app-routes {:port 8080})))
