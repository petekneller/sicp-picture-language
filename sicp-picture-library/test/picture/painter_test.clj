(ns picture.painter_test
  (:use [picture.painter]
        [picture.vector]
        [picture.frame]
        [midje.sweet]))


(facts "map-point-user-to-panel-space can scale its input"
       (let [map-fn (partial map-point-user-to-panel-space (make-frame origin 200 200))]
         (fact "origin should map to the origin"
               (map-fn origin) => origin)
         (fact "bottom right should map to the bottom right"
               (map-fn {:x 1000 :y 1000}) => {:x 200 :y 200})
         (fact "something near the middle should map to the correct place"
               (map-fn {:x 400 :y 600}) => {:x 80 :y 120})))

(facts "map-point-user-to-panel-space can translate its input"
       (let [map-fn (partial map-point-user-to-panel-space (make-frame {:x 50 :y 100} 1000 1000))]
         (fact "origin should map to (50,100)"
               (map-fn origin) => {:x 50 :y 100})
         (fact "(1000,1000) should map to (1050,1100)"
               (map-fn {:x 1000 :y 1000}) => {:x 1050 :y 1100})))

(facts "map-point-user-to-panel-space can translate and scale its input"
       (let [map-fn (partial map-point-user-to-panel-space (make-frame {:x 10 :y 15} 500 500))]
         (fact "origin should map to (10, 15)"
               (map-fn origin) => {:x 10 :y 15})
         (fact "(1000,1000) should map to (510,515)"
               (map-fn {:x 1000 :y 1000}) => {:x 510 :y 515})))