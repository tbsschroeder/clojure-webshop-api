(ns webshop-api.db.handler
  (:require [toucan.db :as db]
            [webshop-api.models.article :refer [Article]]
            [webshop-api.models.basket :refer [Basket]]))

(defn delete! [id]
  (db/simple-delete! Article id))

(defn get-article [id]
  (db/select Article :id id))

(defn get-article-with-count [id]
  (db/select-one-field :count Basket :article id))

(defn article-id-to-basket-id [id]
  (db/select-one-field :id Basket :article id))

(defn basket-id-to-article-id [id]
  (db/select-one-field :article Basket :id id))

(defn increase-article! [article-id]
  (let [basket-id (article-id-to-basket-id article-id)]
    (db/update! Basket basket-id :count (inc (get-article-with-count basket-id)))))

(defn decrease-article! [article-id]
  (let [basket-id (article-id-to-basket-id article-id)]
    (db/update! Basket basket-id :count (let [c (get-article-with-count basket-id)]
                                          (if (> c 1)
                                            (dec c)
                                            0)))))

(defn remove-article! [id]
  (db/update! Article id :count 0))

(defn query-all-articles []
  (db/select Article))

(defn query-all-articles-with-count []
  (db/select Article :count [:> 0]))

(defn has-articles-with-data? []
  (pos? (count (query-all-articles-with-count))))

(defn has-data? []
  (pos? (count (query-all-articles))))

(defn get-article-from-basket [id]
  (db/select Basket :article id))

(defn get-line-from-basket [id]
  (db/select Basket :id id))

(defn delete-article-from-basket [id]
  (db/simple-delete! Basket id))
