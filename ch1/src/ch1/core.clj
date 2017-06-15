h(ns ch1.core
  (:gen-class))

(defn ex1
  []
  (println (str "a" 1 '("b" 2)))
  (println (vector 1 "c" 2))
  (println (list 1 "c"))
  (println (hash-map :a 1 :b 2 "c" 3))
  (println (hash-set 9 "y")))

(defn ex2
  [num]
  (+ num 100))

(defn dec-maker
  [diff]
  #(- % diff))

(defn mapset
  [func vec]
  (set (map func vec)))

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ; (ex1)
  ; (ex2 2)
  ; (def dec9 (dec-maker 9))
  ; (println (dec9 10))
  ; (println  (mapset inc [1 1 2 2 1]))
  )


