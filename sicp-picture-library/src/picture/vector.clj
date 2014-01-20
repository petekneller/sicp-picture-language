(ns picture.vector)

(def origin {:x 0 :y 0})

(defn add-vectors [v1 v2]
  {:x (+ (:x v1) (:x v2)) :y (+ (:y v1) (:y v2))})

(defn scale-vector
  ([v scaling-factor]
   (scale-vector v scaling-factor scaling-factor))
  ([v scaling-factor-x scaling-factor-y]
   {:x (* scaling-factor-x (:x v)) :y (* scaling-factor-y (:y v))}))

(defn scale-vector-x [v scaling-factor]
  (scale-vector v scaling-factor 1))

(defn scale-vector-y [v scaling-factor]
  (scale-vector v 1 scaling-factor))
