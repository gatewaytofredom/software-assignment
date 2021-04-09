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

(defn sort-last-name
  [record-map]
  (sort-by first #(compare %2 %1) record-map))

(defn reverse-date
  [nested-record]
  (let [record-vector (clojure.string/split (get nested-record 4) #"-")]
    (clojure.string/join "-" (vector (get record-vector 2) (get record-vector 1) (get record-vector 0)))))

(defn sort-birth-date
  [record-map]
  (let [sorted-vector (sort-by #(nth % 4) record-map)]
    (map #(vector (get % 0) (get % 1) (get % 2) (get % 3) (reverse-date %)) sorted-vector)))

(defn sort-email
  [record-map]
  (sort-by (juxt #(nth % 2) #(nth % 0)) #(let [a %1 b %2]
                                           (if (and (clojure.string/includes? a "@") (clojure.string/includes? b "@"))
                                             (compare b a)
                                             (compare a b))) record-map))
(defn get-delimiter
  [csv-record]
  (if (or (clojure.string/includes? csv-record "|") (clojure.string/includes? csv-record "%7C"))
    "\\|"
    (if (or (clojure.string/includes? csv-record ",") (clojure.string/includes? csv-record "%2C"))
      ","
      (if (or (clojure.string/includes? csv-record " ") (clojure.string/includes? csv-record "+"))
        " "
        (println "Unsported delimiter.")))))

(defn sort-input
  [data sort-method]
  (if (clojure.string/includes? sort-method "last-name")
    (sort-last-name (first data))
    (if (clojure.string/includes? sort-method "birth-date")
      (sort-birth-date data)
      (if (clojure.string/includes? sort-method "email")
        (sort-email data)
        "invalid sort method"))))

(defn get-records-email [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (sort-input @records "email"))})
(defn get-records-birthdate [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (sort-input @records "birth-date"))})
(defn get-records-name [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (sort-input @records "last-name"))})

(defn csv-to-2d-vector
  "Reads in a file from `file-path` and returns a 2d vector where each element represents one entry in a csv file."
  [file-path]
  (let [csv-string  (clojure.string/split (slurp file-path) #"\n")
        delimiter (get-delimiter (slurp file-path))]
    (mapv #(clojure.string/split % (re-pattern delimiter)) csv-string)))

(defn json-to-2d-vector
  [post-request]
  (let [json-string (get (json/read-str (slurp (post-request :body))) "data")]
    (swap! records conj (clojure.string/split json-string (re-pattern (get-delimiter json-string))))
    {:status 201
     :headers {"Content-Type" "application/json"}
     :body (json/write-str "201 success")}))

(defroutes app-routes
  (POST "/records" [] json-to-2d-vector)
  (GET "/records/email" [] get-records-email)
  (GET "/records/birthdate" [] get-records-birthdate)
  (GET "/records/name" [] get-records-name)
  (route/not-found "You Must Be New Here"))

(defn -main
  ([]
   (server/run-server #'app-routes {:port 8080}))
  ([file sort-method]
   (swap! records conj (csv-to-2d-vector file))
   (doseq [x (sort-input @records sort-method)] (println x))
   (server/run-server #'app-routes {:port 8080})))