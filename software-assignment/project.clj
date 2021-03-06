(defproject software-assignment "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"] 
                 [ultra-csv "0.2.3"] 
                 [http-kit "2.5.3"] 
                 [compojure "1.6.1"] 
                 [ring/ring-codec "1.1.3"] 
                 [org.clojure/data.json "2.0.2"]]
  :main ^:skip-aot software-assignment.core
  :target-path "target/%s"
  :plugins [[lein-cljfmt "0.6.1"]]
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})

