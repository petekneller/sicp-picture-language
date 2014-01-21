(ns picture.frame
  (:require [picture.vector :as vector]))


(defn make-frame [frame-origin width height]
  {:origin frame-origin :width width :height height})

(defn frame-origin [frame] (:origin frame))

(defn frame-width [frame] (:width frame))

(defn frame-height [frame] (:height frame))

(defn frame-x-axis [frame]
  {:x (:width frame) :y 0})

(defn frame-y-axis [frame]
  {:x 0 :y (:height frame)})

(def frame-top-left frame-origin)

(defn frame-bot-right [frame]
  (let [origin (:origin frame)
        x-axis {:x (:width frame) :y 0}
        y-axis {:x 0 :y (:height frame)}]
    (-> origin
        (vector/add-vectors x-axis)
        (vector/add-vectors y-axis))))

(def frame-bound frame-bot-right)

(defn invert-frame [frame]
  (let [new-origin (vector/add-vectors (:origin frame) (frame-y-axis frame))
        inverted-height (* -1 (:height frame))]
    (make-frame new-origin (:width frame) inverted-height)))


