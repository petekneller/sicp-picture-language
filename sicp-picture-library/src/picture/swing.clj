(ns picture.swing
  (:import [javax.swing SwingUtilities JFrame JPanel BorderFactory]
           [java.awt Dimension Color]
           [javax.imageio ImageIO]
           [java.net URL])
  (:require [clojure.java.io :as io]))

; TODO remove 'point' from the name and use multimethods for both this and the future 'rect' one?
; TODO move panel extents to beginning of args so can be partially applied?
(defn map-point-user-to-panel-space [point panel-width panel-height] nil)

(defn draw-line [gfx from to]
  "from and to are coords specified in 'user' space, which begins at the top-left
  and extends to 1000 in both axes"
  (.drawLine gfx (:x from) (:y from) (:x to) (:y to)))

(defn draw-image [gfx ^URL res destRect]
  "the corners of destRect are coords specified in 'user' space, which begins at the top-left
  and extends to 1000 in both axes"
  (let [image (ImageIO/read res)]
    (.drawImage gfx
                image
                ; dest rect on panel
                (:x (:top-left destRect)) (:y (:top-left destRect))
                (:x (:bot-right destRect)) (:y (:bot-right destRect))
                ; src rect in image
                0 0 (.getWidth image) (.getHeight image)
                nil)))


  (defn new-jpanel [on-paint]
    (let [panel (proxy [JPanel] []
                  (getPreferredSize [] (new Dimension 500 500))
                  (paintComponent [gfx]
                    (do (proxy-super paintComponent gfx)
                        (on-paint gfx))))]
      (do
        (.setBorder panel (BorderFactory/createLineBorder Color/black))
        panel)))


  (defn swing-main [on-paint]
    (SwingUtilities/invokeLater
      #(let [frame (new JFrame)]
        (do
          (.setDefaultCloseOperation frame JFrame/EXIT_ON_CLOSE)
          (.add frame (new-jpanel on-paint))
          (.pack frame)
          (.setVisible frame true)
          (println (str "Created GUI on EDT? " (SwingUtilities/isEventDispatchThread)))
          nil))))