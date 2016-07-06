(ns lsystems.basic
  (:require [thi.ng.geom.core :as g]
            [thi.ng.math.core :as m]
            [thi.ng.geom.vector :as v] ))

(defn make-agent
  [pos theta speed rot-theta]
  {:pos       pos
   :theta     theta
   :rot-theta rot-theta
   :speed     speed
   :path      []
   :stack     []})

(defmulti interpret (fn [agent sym] sym))

(defmethod interpret :fwd
  [agent _]
  (let [pos  (:pos agent)
        pos' (m/+ pos (g/as-cartesian
                       (v/vec2 (:speed agent)
                               (:theta agent))))]
    (-> agent
        (assoc :pos pos')
        (update :path conj [pos pos' (count (:stack agent))]))))

(defmethod interpret :left
  [agent _] (update agent :theta - (:rot-theta agent)))

(defmethod interpret :right
  [agent _] (update agent :theta + (:rot-theta agent)))

(defmethod interpret :push
  [agent _]
  (-> agent
      (update :stack conj (dissoc agent :stack))
      (assoc :path [])))

(defmethod interpret :pop
  [agent _]
  (let [agent' (peek (:stack agent))]
    (assoc agent'
           :stack (pop (:stack agent))
           :path  (into (:path agent') (:path agent)))))

(defmethod interpret :default
  [agent sym] agent)

(defn interpret-symbols
  [agent syms] (reduce interpret agent syms))

(defn rewrite-symbols
  [rules symbols] (mapcat rules symbols))

(def algae
  {:start [:a]
   :a     [:a :b]
   :b     [:a]})

(def dragon-curve
  {:start [:a]
   :a     [:a :right :b :right]
   :b     [:left :a :left :b]})

(->> [:start]
     (iterate (partial rewrite-symbols dragon-curve))
     (take 10)
     (last)
     (interpret-symbols (make-agent (v/vec2) 0 5 m/HALF_PI)))
