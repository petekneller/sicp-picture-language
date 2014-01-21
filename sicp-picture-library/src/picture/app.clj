(ns picture.app
  (:gen-class)
  (:require [picture.swing :as swing]
            [picture.examples :as eg]))


(defn -main [& args]
  (swing/swing-main eg/flipped-wave))
