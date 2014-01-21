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

(defn invert [painter]
  (fn [gfx dest-frame]
    (painter gfx (frame/invert-frame dest-frame))))
