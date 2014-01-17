(ns picture.app
  (:gen-class)
  (:import [javax.swing SwingUtilities JFrame JPanel BorderFactory]
           [java.awt Dimension Color]
           [javax.imageio ImageIO])
  (:require [clojure.java.io :as io]))

(defn newPanel []
  (let [panel (proxy [JPanel] []
                (getPreferredSize [] (new Dimension 500 500))
                (paintComponent [gfx]
                  (do (proxy-super paintComponent gfx)
                      ;(.drawString gfx "this is my custom panel!" 10 20)
                      (.setColor gfx Color/BLACK)
                      (.drawLine gfx 10 10 100 30)
                      (.drawImage gfx
                                  (ImageIO/read (io/resource "roger.gif"))
                                  50 50 400 400 ; dest rect on panel
                                  0 0 180 180 ; src rect in image
                                  nil))))]
    (do
      (.setBorder panel (BorderFactory/createLineBorder Color/black))
      panel)))

(defn run-main []
  (SwingUtilities/invokeLater
    #(let [frame (new JFrame)]
      (do
        (.setDefaultCloseOperation frame JFrame/EXIT_ON_CLOSE)
        (.add frame (newPanel))
        (.pack frame)
        (.setVisible frame true)
        (println (str "Created GUI on EDT? " (SwingUtilities/isEventDispatchThread)))
        nil))))

(defn -main [& args] (run-main))
