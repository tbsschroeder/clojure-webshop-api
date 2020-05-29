(ns webshop-api.web.resource
  (:require
   [ring.util.http-response :refer [ok not-found header]]
   [schema.core :as s]
   [compojure.api.sweet :refer [resource]]
   [clojure.java.io :as io]))

(def resource-routes
  (resource
   {:tags ["Resource"]
    :get  {:summary     "Get image"
           :description "Returns an image"
           :produces    ["image/png"]
           :parameters  {:query-params {:name s/Str}}
           :handler     (fn [request]
                          (let [name (-> request :query-params :name)]
                            (-> (io/resource name)
                                (io/input-stream)
                                (ok)
                                (header "Content-Type"



                                        "image/png"))))}}))


