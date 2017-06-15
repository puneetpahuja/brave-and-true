(ns ch5.core
  (:require [clojure.tools.trace :as trace])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn my-comp [& funcs]
  (if (= (count funcs) 1)
    (fn [& args]
      (apply (first funcs) args))
    (fn [& args]
      ((first funcs) (apply (my-comp (rest funcs)) args)))))

(defn my-comp-using-reverse [& funcs]
  (if (= (count funcs) 1)
    (fn [& args]
      (apply (first funcs) args))
    (fn [& args]
      ((my-comp-using-reverse (butlast funcs)) (apply (last funcs) args)))))

; (trace/trace-ns 'ch5.core)

(defn not-my-comp [& all-fns]
  (reduce
    (fn [acc-fn curr-fn]
      (fn [& args]
        (acc-fn (apply curr-fn args))))
    identity
    all-fns))

((not-my-comp inc *) 6 5)
; ((my-comp-using-reverse inc *) 6 5)


(defn attr [attribute]
  (comp attribute :attributes))


(defn my-assoc-in [m [k & ks] v]
  (if (empty? ks)
    (assoc m k v)
    (assoc m k (my-assoc-in (get m k) ks v))))

(def assoc1 assoc)

(defn my-update-in [m [k & ks] f & args]
  (if (empty? ks)
    (assoc1 m k (apply f (get m k) args))
    (assoc1 m k (apply my-update-in (get m k) ks f args))))



