(defproject webshop-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://github.com/tbsschroeder/clojure-webshop-api"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [metosin/compojure-api "2.0.0-alpha30"]

                 ;; web
                 [prismatic/schema "1.1.9"]
                 [ring/ring-jetty-adapter "1.6.3"]

                 ;; tests
                 [ring/ring-mock "0.3.2"]
                 [midje "1.8.3"]

                 ;; database
                 [toucan "1.15.1"]
                 [org.postgresql/postgresql "42.2.12"]

                 ;; database tests
                 [org.clojure/java.jdbc "0.7.11"]
                 [org.xerial/sqlite-jdbc "3.31.1"]]
  :profiles {:uberjar {:omit-source true
                       :aot :all}
             :dev     {:plugins      [[lein-midje "3.2"]    ; tests
                                      [lein-kibit "0.1.6"]  ; static code analyzer
                                      [lein-ancient "0.6.15"] ; dependency checker
                                      [lein-cljfmt "0.6.0"] ; code formatter
                                      [jonase/eastwood "0.2.9"] ; lint tool
                                      ]}}
  :uberjar-name "server.jar"
  :main ^:skip-aot webshop-api.core
  :target-path "target/%s"
  )