(ns picture.examples
  (:import [java.awt Color])
  (:require [clojure.java.io :as io]
            [picture.vector :as vector]
            [picture.frame :as frame]
            [picture.painter :as painter]
            [picture.swing :as swing]))

(def roger
  (swing/draw-image (io/resource "roger.gif") (frame/make-frame {:x 0 :y 0} 1000 1000)))

(def wave
  (painter/do-painters
    (swing/draw-polyline {:x 0 :y 150}
                         {:x 150 :y 350}
                         {:x 300 :y 300}
                         {:x 325 :y 300}
                         {:x 300 :y 150}
                         {:x 325 :y 0})
    (swing/draw-polyline {:x 625 :y 0}
                         {:x 650 :y 150}
                         {:x 625 :y 300}
                         {:x 750 :y 300}
                         {:x 1000 :y 700})
    (swing/draw-polyline {:x 1000 :y 850}
                         {:x 625 :y 500}
                         {:x 700 :y 1000})
    (swing/draw-polyline {:x 625 :y 1000}
                         {:x 500 :y 700}
                         {:x 375 :y 1000})
    (swing/draw-polyline {:x 300 :y 1000}
                         {:x 325 :y 500}
                         {:x 300 :y 350}
                         {:x 150 :y 500}
                         {:x 0 :y 300})))


(def wave2 (painter/beside wave (painter/flip-vert wave)))

(def wave4 (painter/flipped-pairs wave))

(def right-split (painter/right-split wave 4))

(def up-split (painter/up-split wave 4))

(def corner-split (painter/corner-split roger 4))