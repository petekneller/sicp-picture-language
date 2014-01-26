(ns picture.app
  (:gen-class)
  (:require [picture.swing :as swing]
            [picture.painter :as painter]
            [picture.examples :as eg]))


(defn -main [& args]
  (swing/swing-main (painter/square-limit eg/roger 5)))
