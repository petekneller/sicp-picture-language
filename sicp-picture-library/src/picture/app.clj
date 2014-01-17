(ns picture.app
  (:gen-class)
  (:import [javax.swing SwingUtilities JFrame JPanel BorderFactory]
           [java.awt Dimension Color]))

(defn newPanel []
  (let [panel (proxy [JPanel] []
                (getPreferredSize [] (new Dimension 250 200))
                (paintComponent [gfx]
                  (do (proxy-super paintComponent gfx)
                      (.drawString gfx "this is my custom panel!" 10 20))))
        _ (.setBorder panel (BorderFactory/createLineBorder Color/black))]
    panel))

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
