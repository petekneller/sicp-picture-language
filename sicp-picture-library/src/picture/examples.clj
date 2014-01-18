(ns picture.examples
  (:import [java.awt Color])
  (:require [clojure.java.io :as io]
            [picture.swing :as swing]))

(def roger
  (swing/draw-image (io/resource "roger.gif") {:top-left {:x 0 :y 0} :bot-right {:x 1000 :y 1000}}))

(def wave
  (swing/draw-polyline {:x 0 :y 100}
                       {:x 150 :y 350}
                       {:x 300 :y 300}
                       {:x 350 :y 300}
                       {:x 325 :y 150}
                       {:x 350 :y 0}))
