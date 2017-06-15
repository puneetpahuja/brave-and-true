(ns fwpd.core
  (:require [clojure.string :as string :exclude [replace reverse]]))

(def filename "suspects.csv")
(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(def validations {:name string?
                  :glitter-index #(and (number? %) (<= 0 % 10))})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  [string]
  (map #(string/split % #",")
       (string/split string #"\n")))

(defn mapify
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
    [minimum-glitter records]
    (map :name (filter #(>= (:glitter-index %) minimum-glitter) records)))

(defn validate
  [validations record]
  (every? true?
          (map #((second %) ((first %) record)) validations)))

(defn append
  [{:keys [name glitter-index] :as record}]
  (when (validate validations record) 
    (spit filename (str "\n" name "," glitter-index) :append true)))

(defn map->str
  [input-map]
  (string/join "," (map #(get input-map %) vamp-keys)))

(defn maplist->csv
  [maplist]
  (string/join "\n" (map map->str maplist)))

