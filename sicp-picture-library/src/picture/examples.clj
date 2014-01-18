(ns picture.examples
  (:import [java.awt Color])
  (:require [clojure.java.io :as io]
            [picture.swing :as swing]))

(defn draw-roger [destRect]
  (swing/draw-image (io/resource "roger.gif") destRect))

(def basic-picture
  (swing/do-renderers
      (swing/draw-line {:x 0 :y 100} {:x 150 :y 350})
      (swing/draw-line {:x 150 :y 350} {:x 300 :y 300})
      (swing/draw-line {:x 300 :y 300} {:x 350 :y 300})
      (draw-roger {:top-left  {:x 50 :y 50}
                 :bot-right {:x 400 :y 400}})))
