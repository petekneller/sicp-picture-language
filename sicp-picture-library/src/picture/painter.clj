(ns picture.painter
  (:require [picture.vector :as vector]
            [picture.frame :as frame]))


(defn map-point-user-to-frame-space [frame point]
  (let [x-scale (/ (frame/frame-width frame) 1000)
        y-scale (/ (frame/frame-height frame) 1000)
        scaled-point (vector/scale-vector point x-scale y-scale)
        translated-point (vector/add-vectors (frame/frame-origin frame) scaled-point)]
    translated-point))


(defn do-painters [& renderers]
  (fn [gfx dest-frame]
    (doall (map #(% gfx dest-frame) renderers))))

(defn flip-vert [painter]
  (fn [gfx dest-frame]
    (painter gfx (frame/invert-frame dest-frame))))

(defn beside [painter1 painter2]
  (fn [gfx dest-frame]
    (let [left-frame (frame/scale-frame dest-frame 0.5 1)
          right-translation {:x (* 0.5 (frame/frame-width dest-frame)) :y 0}
          right-frame (frame/translate-frame left-frame right-translation)]
      (do 
        (painter1 gfx left-frame)
        (painter2 gfx right-frame)))))

(defn below [painter1 painter2]
  (fn [gfx dest-frame]
    (let [upper-frame (frame/scale-frame dest-frame 1 0.5)
          lower-translation {:x 0 :y (* 0.5 (frame/frame-height dest-frame))}
          lower-frame (frame/translate-frame upper-frame lower-translation)]
      (do
        (painter1 gfx upper-frame)
        (painter2 gfx lower-frame)))))

(defn flipped-pairs [painter]
  (let [painter2 (beside painter (flip-vert painter))]
    (below painter2 painter2)))

(defn right-split [painter n]
  (if (= 0 n)
    painter
    (let [smaller (right-split painter (- n 1))]
      (beside painter (below smaller smaller)))))