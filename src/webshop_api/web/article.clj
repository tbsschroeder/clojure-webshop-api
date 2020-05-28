(ns webshop-api.web.article
  (:require [schema.core :as s]
            [webshop-api.models.article :refer [Article]]
            [webshop-api.web.restful :as restful]
            [webshop-api.helper.string-util :as str]))

(defn valid-article-title? [title]
  (str/length-in-range? 5 100 title))

(defn valid-article-description? [description]
  (str/non-blank-with-max-length? 100 description))

(defn valid-article-category? [category]
  (str/non-blank-with-max-length? 100 category))

(defn valid-article-image? [image]
  (str/non-blank-with-max-length? 20 image))

(defn valid-article-count? [count]
  (>= count))

(s/defschema ArticleRequestSchema
  {:title       (s/constrained s/Str valid-article-title?)
   :description (s/constrained s/Str valid-article-description?)
   :category    (s/constrained s/Str valid-article-category?)
   :image       (s/constrained s/Str valid-article-image?)
   :count       (s/constrained s/Int valid-article-count?)})

(def article-entity-route
  (restful/resource {:model      Article
                     :name       "article"
                     :req-schema ArticleRequestSchema}))