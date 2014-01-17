(ns picture.swing
  (:import [javax.swing SwingUtilities JFrame JPanel BorderFactory]
           [java.awt Dimension Color]
           [javax.imageio ImageIO]
           [java.net URL])
  (:require [clojure.java.io :as io]))


(defn draw-line [gfx from to]
  (.drawLine gfx (:x from) (:y from) (:x to) (:y to)))

(defn draw-image [gfx ^URL res srcRect destRect]
  (.drawImage gfx
              (ImageIO/read res)
              ; dest rect on panel
              (:x (:top-left destRect)) (:y (:top-left destRect))
              (:x (:bot-right destRect)) (:y (:bot-right destRect))
              ; src rect in image
              (:x (:top-left srcRect)) (:y (:top-left srcRect))
              (:x (:bot-right srcRect)) (:y (:bot-right srcRect))
              nil))


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