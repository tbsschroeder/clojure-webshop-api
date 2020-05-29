(ns webshop-api.web.basket
  (:require
   [ring.util.http-response :refer [ok not-found]]
   [schema.core :as s]
   [ring.util.http-status :as status]
   [webshop-api.db.handler :as db]
   [compojure.api.sweet :refer [resource]]))

(s/defschema Basket
  {:id      s/Int
   :article s/Int
   :count   s/Int})

(def basket-routes
  (resource
   {:tags   ["Basket"]
    :get    {:summary     "Get basket"
             :description "Get your current basket"
             :responses   {status/ok {:schema [Basket]}}
             :handler     (fn [_] (ok))}
    :put    {:summary     "Updates a basket"
             :description "Updates a basket by an article id and it's count"
             :responses   {status/ok {:schema {:message s/Str}}}
             :parameters  {:query-params {:id    s/Int
                                          :count s/Int}}
             :handler     (fn [request]
                            (let [id (-> request :query-params :id)
                                  count (-> request :query-params :count)]
                              (if (db/get-article-from-basket id)
                                (ok {:message (str "Updated " id " with count " count)})
                                (not-found))))}
    :delete {:summary     "Delete article from basket"
             :description "Deletes an article from the basket"
             :responses   {status/ok {:schema {:message s/Str}}}
             :parameters  {:query-params {:id s/Int}}
             :handler     (fn [request]
                            (let [id (-> request :query-params :id)]
                              (if (db/delete-article-from-basket id)
                                (ok {:message (str "Deleted article " id)})
                                (not-found))))}}))

