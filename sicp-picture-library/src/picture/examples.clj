(ns picture.examples
  (:import [java.awt Color])
  (:require [clojure.java.io :as io]
            [picture.swing :as swing]))

(defn draw-roger [destRect]
  (swing/draw-image (io/resource "roger.gif") destRect))

(def basic-picture
  (swing/do-renderers
      (swing/draw-line {:x 10 :y 10} {:x 100 :y 30})
      (draw-roger {:top-left  {:x 50 :y 50}
                 :bot-right {:x 400 :y 400}})))
