(ns webshop-api.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [toucan.db :as db]
            [toucan.models :as models]
            [compojure.api.sweet :refer [api routes context]]
            [ring.util.http-response :as response]
            [compojure.api.exception :as ex]
            [webshop-api.db.handler :refer [create-table!]]
            [webshop-api.web.article :refer [article-entity-route]]
            [webshop-api.web.basket :refer [basket-routes]]
            [webshop-api.web.resource :refer [resource-routes]])
  (:gen-class)
  (:import (java.sql SQLException)))

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
   :options {:ui {:validatorUrl nil}}
   :data    {:info     {:version     "1.0.0"
                        :title       "Webshop CRUD API"
                        :description "With Compojure and Toucan"}
             :tags     [{:name        "api"
                         :description "Webshop CRUD API"}]
             :consumes ["application/json"]
             :produces ["application/json"]}})

(defn custom-handler [f type]
  (fn [^Exception e data request]
    (f {:message (.getMessage e)
        :type type})))

(def app (api {:swagger swagger-config}
              (context "/" [] :tags ["Articles"] article-entity-route)
              (context "/basket" [] :tags ["Basket"] basket-routes)
              (context "/resource" [] :tags ["Resources"] resource-routes)
              :exceptions {:handlers
                           {;; catches ex-infos with {:type ::calm}
                            ::calm                  (custom-handler response/enhance-your-calm :calm)

                            ;; catches all SQLExceptions (and it's subclasses)
                            SQLException            (custom-handler response/internal-server-error :sql)

                            ;; log all request validation errors
                            ::ex/request-validation (ex/with-logging ex/request-parsing-handler :info)

                            ;; everything else
                            ::ex/default            (custom-handler response/internal-server-error :unknown)}}))

(defn -main
  [& args]
  (db/set-default-db-connection! db-connection-map)
  (create-table! (db/connection) false)
  (models/set-root-namespace! 'webshop-api.models)
  (run-jetty app {:port 8000}))
