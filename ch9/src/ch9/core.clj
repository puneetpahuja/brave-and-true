(ns ch9.core
  (:require [clojure.string :as s])
  (:gen-class))

(defn search-one [search-engine query-string]
   (slurp (str "https://www."
               search-engine
               ".com/search?q"
               (if (= search-engine 'google) "%3D" "=")
               (s/join "+" (s/split query-string #" ")))))

(defn search [query-string search-engines]
  (let [result (promise)]
    (doseq [search-engine search-engines]
      (future (deliver result (search-one search-engine query-string))))
    @result))

;(search "a b")

