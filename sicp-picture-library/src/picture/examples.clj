(ns picture.examples
  (:import [java.awt Color])
  (:require [clojure.java.io :as io]
            [picture.swing :as swing]))

(defn roger [destRect]
  (swing/draw-image (io/resource "roger.gif") destRect))

(def wave
  (swing/do-renderers
      (swing/draw-line {:x 0 :y 100} {:x 150 :y 350})
      (swing/draw-line {:x 150 :y 350} {:x 300 :y 300})
      (swing/draw-line {:x 300 :y 300} {:x 350 :y 300})
      (swing/draw-line {:x 350 :y 300} {:x 325 :y 150})
      (swing/draw-line {:x 325 :y 150} {:x 350 :y 0})
      ))
