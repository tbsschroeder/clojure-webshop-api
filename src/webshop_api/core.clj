(ns webshop-api.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [toucan.db :as db]
            [toucan.models :as models]
            [compojure.api.sweet :refer [api routes]]
            [webshop-api.db.handler :refer [create-table!]]
            [webshop-api.web.article :refer [article-entity-route]])
  (:gen-class))

(def db-connection-map
  {:dbtype      "postgres"
   :dbname      "webshop"
   :user        "clojure"
   :password    "clojure"
   :classname   "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname     (str "//" (or (System/getenv "DB_HOST") "localhost") ":5432/webshop")})

(def swagger-config
  {:ui      "/swagger"
   :spec    "/swagger.json"
   :options {:ui   {:validatorUrl nil}
             :data {:info {:version "1.0.0" :title "Webshop CRUD API"}}}})

(def app (api {:swagger swagger-config} (apply routes article-entity-route)))

(defn -main
  [& args]
  (db/set-default-db-connection! db-connection-map)
  (create-table! db/connection false)
  (models/set-root-namespace! 'webshop-api.models)
  (run-jetty app {:port 8000}))
