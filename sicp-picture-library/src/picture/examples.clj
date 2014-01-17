(ns picture.examples
  (:import [java.awt Color])
  (:require [clojure.java.io :as io]
            [picture.swing :as swing]))

(defn draw-roger [gfx destRect]
  (swing/draw-image gfx
              (io/resource "roger.gif")
              ; src
              { :top-left {:x 0 :y 0}
                :bot-right {:x 180 :y 180}}
              ; dest
              destRect))

(defn basic-paint [gfx]
  (do
    ;(.drawString gfx "this is my custom panel!" 10 20)
    (.setColor gfx Color/BLACK)
    (swing/draw-line gfx {:x 10 :y 10} {:x 100 :y 30})
    (draw-roger gfx { :top-left {:x 50 :y 50}
                      :bot-right {:x 400 :y 400}})))
