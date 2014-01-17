(ns picture.swing-test
  (:require [clojure.test :as t])
  (:use [picture.swing]))

(t/deftest map-point-user-to-panel-space-test
  (let [panel-width 200
        panel-height 200]
    (t/is (= {:x 0 :y 0} (map-point-user-to-panel-space {:x 0 :y 0} panel-width panel-height))
          "origin should map to the origin")))
