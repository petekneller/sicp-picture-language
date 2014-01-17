(ns picture.swing-test
  (:require [clojure.test :as t])
  (:use [picture.swing]))

(t/deftest map-point-user-to-panel-space-test
  (let [map-fn (partial map-point-user-to-panel-space 200 200)]
    (t/is (= {:x 0 :y 0} (map-fn {:x 0 :y 0}))
          "origin should map to the origin")
    (t/is (= {:x 200 :y 200} (map-fn {:x 1000 :y 1000}))
          "bottom right should map to the bottom right")
    (t/is (= {:x 80 :y 120} (map-fn {:x 400 :y 600})))))
