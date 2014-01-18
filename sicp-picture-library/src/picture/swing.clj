(ns picture.swing
  (:import [javax.swing SwingUtilities JFrame JPanel BorderFactory]
           [java.awt Dimension Color]
           [javax.imageio ImageIO]
           [java.net URL])
  (:require [clojure.java.io :as io]))


; TODO remove 'point' from the name and use multimethods for both this and the future 'rect' one?
(defn map-point-user-to-panel-space [panel-width panel-height point]
  (let [x-scale (/ panel-width 1000)
        y-scale (/ panel-height 1000)
        x2 (* x-scale (:x point))
        y2 (* y-scale (:y point))]
    {:x x2 :y y2}))


(defn draw-line [from to]
  "from and to are coords specified in 'user' space, which begins at the top-left
  and extends to 1000 in both axes"
  (fn [gfx panel-width panel-height]
    (let [map-fn (partial map-point-user-to-panel-space panel-width panel-height)
          panel-from (map-fn from)
          panel-to (map-fn to)]
      (.drawLine gfx (:x panel-from) (:y panel-from) (:x panel-to) (:y panel-to)))))


(defn draw-image [^URL res destRect]
  "the corners of destRect are coords specified in 'user' space, which begins at the top-left
  and extends to 1000 in both axes"
  (fn [gfx panel-width panel-height]
    (let [image (ImageIO/read res)]
      (.drawImage gfx
                  image
                  ; dest rect on panel
                  (:x (:top-left destRect)) (:y (:top-left destRect))
                  (:x (:bot-right destRect)) (:y (:bot-right destRect))
                  ; src rect in image
                  0 0 (.getWidth image) (.getHeight image)
                  nil))))


(defn followed-by [first-renderer second-renderer]
  "creates a renderer that sequences its two arguments one after the other"
  (fn [gfx panel-width panel-height]
    (do
      (first-renderer gfx panel-width panel-height)
      (second-renderer gfx panel-width panel-height))))


(defn do-renderers [& renderers]
  (fn [gfx panel-width panel-height]
    (doall (map #(% gfx panel-width panel-height) renderers))))


(defn new-jpanel [on-paint]
  " on-paint: gfx -> nil"
  (let [panel-width 500
        panel-height 500
        panel (proxy [JPanel] []
                (getPreferredSize [] (new Dimension panel-width panel-height))
                (paintComponent [gfx]
                  (do (proxy-super paintComponent gfx)
                      (.setColor gfx Color/BLACK)
                      ;(.drawString gfx "this is my custom panel!" 10 20)
                      (on-paint gfx panel-width panel-height))))]
    (do
      (.setBorder panel (BorderFactory/createLineBorder Color/black))
      panel)))


(defn serialise-rendering [render-cmds]
  (fn [gfx]
    (println (map #(do (% gfx) (println %)) render-cmds))))


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