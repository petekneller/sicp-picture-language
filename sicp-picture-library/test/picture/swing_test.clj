(ns picture.swing-test
  (:use [picture.swing]
        [midje.sweet]))

(facts "map-point-user-to-panel-space-test"
       (let [map-fn (partial map-point-user-to-panel-space 200 200)]
         (fact "origin should map to the origin"
               (map-fn {:x 0 :y 0}) => {:x 0 :y 0})
         (fact "bottom right should map to the bottom right"
               (map-fn {:x 1000 :y 1000}) => {:x 200 :y 200})
         (fact ""
               (map-fn {:x 400 :y 600}) => {:x 80 :y 120})))