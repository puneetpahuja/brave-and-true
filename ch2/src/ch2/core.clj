(ns ch2.core
  (:gen-class))

(defn infix->prefix-helper
  [infix-list op-list]
  (let [[operand1 operation operand2  & rest-elements] infix-list]
    (if operation
      (if (reduce #(or %1 (= operation %2)) false op-list)
        (infix->prefix-helper (conj rest-elements (list operation operand1 operand2)) op-list)
        (concat (list operand1 operation) (infix->prefix-helper (rest (rest infix-list)) op-list)))
      (if operand1
        (list operand1)
        nil))))

(defn infix->prefix
  [infix-string]
  (let [infix-list (read-string infix-string)
        temp (infix->prefix-helper infix-list '(* /))
        result (infix->prefix-helper temp '(+ -))]
    (first result)))

(defn my-comp [& functions]
  (println (rest functions))
  (if (empty? (rest functions))
    (fn [& args]
      (apply (first functions) args))
    (fn [& args]
      ((first functions) (apply (apply my-comp (rest functions)) args)))))

((my-comp *) 9 4)

;(def prefix (infix->prefix "(1 * 3 * 4 - 5 + 6 - 8 / 5 * 5 + 6 - 9 / 8 + 6)"))
;(def prefix (infix->prefix "(4 / 5 * 6 / 8 * 6 * 81 / 61)"))
;(println prefix)
;(println (eval prefix))


