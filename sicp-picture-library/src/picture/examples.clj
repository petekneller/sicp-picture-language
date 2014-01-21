(ns picture.examples
  (:import [java.awt Color])
  (:require [clojure.java.io :as io]
            [picture.vector :as vector]
            [picture.frame :as frame]
            [picture.painter :as painter]
            [picture.swing :as swing]))

(def roger
  (swing/draw-image (io/resource "roger.gif") (frame/make-frame {:x 50 :y 50} 800 800)))

(def wave
  (painter/do-painters
    (swing/draw-polyline {:x 0 :y 100}
                         {:x 150 :y 350}
                         {:x 300 :y 300}
                         {:x 350 :y 300}
                         {:x 325 :y 150}
                         {:x 350 :y 0})
    (swing/draw-polyline {:x 600 :y 0}
                         {:x 625 :y 150}
                         {:x 600 :y 300}
                         {:x 750 :y 300}
                         {:x 1000 :y 650})
    (swing/draw-polyline {:x 1000 :y 800}
                         {:x 625 :y 500}
                         {:x 700 :y 1000})
    (swing/draw-polyline {:x 625 :y 1000}
                         {:x 500 :y 700}
                         {:x 375 :y 1000})
    (swing/draw-polyline {:x 300 :y 1000}
                         {:x 325 :y 500}
                         {:x 300 :y 350}
                         {:x 150 :y 500}
                         {:x 0 :y 250})))

(def flipped-wave (painter/invert wave))

(def wave2
  (fn [gfx dest-frame]
    (let [left wave
          left-rect dest-frame
          right wave
          right-rect dest-frame]
      (do
        (left gfx left-rect)
        (right gfx right-rect)))))
