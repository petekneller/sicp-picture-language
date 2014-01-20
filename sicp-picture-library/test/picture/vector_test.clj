(ns picture.vector-test
  (:use [midje.sweet]
        [picture.vector]))

(facts "vectors can be scaled"
       (fact ""
             (scale-vector {:x 10 :y 5} 2) => {:x 20 :y 10})
       (fact "vectors that have no projection in the x-axis still dont after scaling"
             (scale-vector {:x 10 :y 0} 8) => {:x 80 :y 0})
       (fact "vectors that have no projection in the y-axis still dont after scaling"
             (scale-vector {:x 0 :y 2} 10) => {:x 0 :y 20}))

(facts "vectors can be added"
       (fact ""
             (add-vectors {:x 1 :y 2} {:x 15 :y 16}) => {:x 16 :y 18})
       (fact "addition of the origin leaves the other vector unchanged"
             (add-vectors origin {:x 10 :y 15}) => {:x 10 :y 15}))

(facts "vectors can have their components scaled individually"
       (let [v {:x 12 :y 29}]
         (fact "scaling in the x component should not affect the y"
               (scale-vector-x v 2) => {:x 24 :y 29}
               (scale-vector v 2 1) => {:x 24 :y 29})
         (fact "scaling in the y component should not affect the x"
               (scale-vector-y v 5) => {:x 12 :y 145}
               (scale-vector v 1 5) => {:x 12 :y 145})))
