(ns webshop-api.db.table
  (:require [clojure.java.jdbc :as jdbc]
            [toucan.db :as db]
            [webshop-api.models.article :refer [Article]]
            [webshop-api.models.basket :refer [Basket]]))

(defn drop-table! [connection should-drop-table]
  (when should-drop-table
    (jdbc/execute! connection "DROP TABLE IF EXISTS basket")
    (jdbc/execute! connection "DROP TABLE IF EXISTS article")))

(defn create-table! [connection is-testing]
  (let [pk (if is-testing "INTEGER" "SERIAL")
        fk (if is-testing "FOREIGN KEY" "")
        article-table (str "CREATE TABLE IF NOT EXISTS article(id " pk " PRIMARY KEY, title TEXT NOT NULL, description TEXT NOT NULL, category TEXT NOT NULL, image TEXT NOT NULL);")
        basket-table (str "CREATE TABLE IF NOT EXISTS basket(id  " pk " PRIMARY KEY, article INTEGER " fk " REFERENCES article(id), count INTEGER DEFAULT 0 NOT NULL);")]
    (jdbc/execute! connection article-table)
    (jdbc/execute! connection basket-table)))

(defn fill-table! []
  (db/insert! Article :title "Bacon"
              :description "Bacon is a type of salt-cured pork. Bacon is prepared from several different cuts of meat, typically from the pork belly or from back cuts, which have less fat than the belly. And it's delicious!"
              :category "Meat"
              :image "img/products/bacon.jpg")
  (db/insert! Article :title "Crinkle Cut Fries"
              :description "Crinkle-cutting is slicing that leaves a corrugated surface. This is done with corrugated knives or mandoline blades. Crinkle-cut potato chips are sometimes called ruffled."
              :category "Potato Products "
              :image "img/products/crinklefries.jpg")
  (db/insert! Article :title "Deep Frying Fat"
              :description "Deep fat frying is a cooking method that can be used to cook food. The process involves submerging a food in extremely hot oil until it reaches a safe minimum internal temperature."
              :category "Oil & Fat"
              :image "img/products/fryingfat.jpg")
  (db/insert! Article :title "Garlic"
              :description "Garlic is a species in the onion genus. Its close relatives include the onion, shallot, leek, chive, and chinese onion. Garlic is native to central asia and ..."
              :category "Vegetables"
              :image "img/products/garlic.jpg")
  (db/insert! Article :title "Ice Cream"
              :description "Ice cream is a sweetened frozen food typically eaten as a snack or dessert. It may be made from dairy milk or cream and is flavored with a sweetener and any spice."
              :category "Sweets"
              :image "img/products/icecream.jpg")
  (db/insert! Article :title "Olive Oil"
              :description "Olive oil is a liquid obtained from olives. The oil is produced by pressing whole olives. It is commonly used in cooking, whether for frying or as a salad dressing."
              :category "Oil & Fat"
              :image "img/products/oliveoil.jpg")
  (db/insert! Article :title "Shrimps"
              :description "Shrimp is shrimp and we do not care whether they are black tiger, white tiger or sea tiger. Just cook or grill them with a lot of garlic and, if you like, with a steak!"
              :category "Fish"
              :image "img/products/shrimps.jpg")
  (db/insert! Article :title "Steakhouse Fries"
              :description "Steakhouse fries offer an intense potato experience. Made from whole potatoes extra wide cut, they are especially potato and go perfectly with hearty meat dishes."
              :category "Potato Products"
              :image "img/products/steakhousefries.jpg")
  (db/insert! Article :title "Sweet Potato Fries"
              :description "Fried sweet potato features in a variety of dishes and cuisines including the popular sweet potato fries, a variation of French fries using sweet potato instead of potato."
              :category "Potato Products"
              :image "img/products/sweetpotatoefries.jpg")
  (db/insert! Article :title "Tortilla Chips"
              :description "A tortilla chip is a snack food made from corn tortillas, which are cut into wedges and then fried—or baked. Corn tortillas are made of corn, vegetable oil, salt and water."
              :category "Snack"
              :image "img/products/tortilla.jpg")
  (db/insert! Basket :article 1
              :count 5))

(defn recreate-table! [connection is-testing should-drop-table]
  (drop-table! connection should-drop-table)
  (create-table! connection is-testing)
  (fill-table!))