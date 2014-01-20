(ns picture.frame-test
  (:use [midje.sweet]
        [picture.frame]))


(facts "frames can have their x- and y-axis extracted"
       (let [f (make-frame {:x 10 :y 10} 100 100)]
         (fact "the x-axis has a y-coord of 0"
               (:y (frame-x-axis f)) => 0)
         (fact "the y-axis has a x-coord of 0"
               (:x (frame-y-axis f)) => 0)
         (fact "the non-zero projections reflect the extent of the frame"
               [(:x (frame-x-axis f)) (:y (frame-y-axis f))] => [100 100])))