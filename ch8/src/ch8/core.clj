(ns ch8.core
  (:gen-class))

(defmacro when-valid [record validations & then]
  `(let [errors# (validate ~record ~validations)]
     (when (empty? errors#)
       ~@then)))

(defmacro my-or
  ([] nil)
  ([x] x)
  ([x & next]
   `(let [or# ~x]
      (if or# or# (my-or ~@next)))))

(defmacro my-defattrs [& attrs]
  (let [attr-pairs (map #(into [] %) (partition 2 attrs))]
    `(doseq [pair# ~attr-pairs]
       (def (first pair#) (comp (second pair#) :attributes)))))

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(defmacro defattrs
  ([] nil)
  ([fn-name attr]
   `(def ~fn-name (comp ~attr :attributes)))

  ([fn-name attr & rest]
    `(do
       (defattrs ~fn-name ~attr)
       (defattrs ~@rest))))

(macroexpand `(defattrs c-int :intelligence
                c-str :strength
                c-dex :dexterity))

(defattrs c-int :intelligence
  c-str :strength
  c-dex :dexterity)

(c-str character)


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
