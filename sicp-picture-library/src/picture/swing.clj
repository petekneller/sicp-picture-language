(ns picture.swing
  (:import [javax.swing SwingUtilities JFrame JPanel BorderFactory]
           [java.awt Dimension Color]
           [javax.imageio ImageIO]
           [java.net URL])
  (:require [clojure.java.io :as io]
            [picture.vector :as vector]
            [picture.frame :as frame]))


(defn map-point-user-to-panel-space [frame point]
  (let [x-scale (/ (frame/frame-width frame) 1000)
        y-scale (/ (frame/frame-height frame) 1000)
        scaled-point (vector/scale-vector point x-scale y-scale)
        translated-point (vector/add-vectors (frame/frame-origin frame) scaled-point)]
    translated-point))


(defn draw-line [from to]
  "from and to are coords specified in 'user' space, which begins at the top-left
  and extends to 1000 in both axes"
  (fn [gfx panel-rect]
    (let [to-panel (partial map-point-user-to-panel-space panel-rect)
          panel-from (to-panel from)
          panel-to (to-panel to)]
      (.drawLine gfx (:x panel-from) (:y panel-from) (:x panel-to) (:y panel-to)))))


(defn draw-polyline [& points]
  "coords specified in 'user' space, which begins at the top-left
  and extends to 1000 in both axes"
  (fn [gfx panel-rect]
    (let [to-panel-points (map #(map-point-user-to-panel-space panel-rect %) points)
          xs (map :x to-panel-points)
          ys (map :y to-panel-points)]
      (.drawPolyline gfx (int-array xs) (int-array ys) (count to-panel-points)))))


(defn draw-image [^URL res destRect]
  "the corners of destRect are coords specified in 'user' space, which begins at the top-left
  and extends to 1000 in both axes"
  (fn [gfx panel-rect]
    (let [image (ImageIO/read res)
          to-panel (partial map-point-user-to-panel-space panel-rect)
          panel-top-left (to-panel (frame/frame-origin destRect))
          panel-bot-right (to-panel (frame/frame-bot-right destRect))]
      (.drawImage gfx
                  image
                  ; dest rect on panel
                  (:x panel-top-left) (:y panel-top-left)
                  (:x panel-bot-right) (:y panel-bot-right)
                  ; src rect in image
                  0 0 (.getWidth image) (.getHeight image)
                  nil))))


(defn do-renderers [& renderers]
  (fn [gfx panel-rect]
    (doall (map #(% gfx panel-rect) renderers))))


(defn new-jpanel [on-paint]
  "on-paint: gfx -> rect -> nil"
  (let [panel-width 500
        panel-height 500
        panel (proxy [JPanel] []
                (getPreferredSize [] (new Dimension panel-width panel-height))
                (paintComponent [gfx]
                  (do (proxy-super paintComponent gfx)
                      (.setColor gfx Color/BLACK)
                      ;(.drawString gfx "this is my custom panel!" 10 20)
                      (on-paint gfx (frame/make-frame vector/origin panel-width panel-height)))))]
    (do
      (.setBorder panel (BorderFactory/createLineBorder Color/black))
      panel)))


(defn swing-main [renderer]
  "render-cmds: seq of render commands, where a render command is Graphics -> nil"
  (SwingUtilities/invokeLater
    #(let [frame (new JFrame)]
      (do
        (.setDefaultCloseOperation frame JFrame/EXIT_ON_CLOSE)
        (.add frame (new-jpanel renderer))
        (.pack frame)
        (.setVisible frame true)
        (println (str "Created GUI on EDT? " (SwingUtilities/isEventDispatchThread)))
        nil))))