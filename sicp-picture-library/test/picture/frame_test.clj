(ns picture.frame-test
  (:use [midje.sweet]
        [picture.vector]
        [picture.frame]))


(facts "frames can have their x- and y-axis extracted"
       (let [f (make-frame {:x 10 :y 10} 100 100)]
         (fact "the x-axis has a y-coord of 0"
               (:y (frame-x-axis f)) => 0)
         (fact "the y-axis has a x-coord of 0"
               (:x (frame-y-axis f)) => 0)
         (fact "the non-zero projections reflect the extent of the frame"
               [(:x (frame-x-axis f)) (:y (frame-y-axis f))] => [100 100])))

(facts "frames can be inverted"
      (let [before (make-frame origin 200 100)
            after (invert-frame before)]
        (fact "the frame origin is the top-left corner"
              (frame-origin before) => {:x 0 :y 0})
        (fact "the bounding corner is the bottom right"
              (frame-bound before) => {:x 200 :y 100})
        (fact "the top-left corner of the inverted frame is now the bottom left"
              (frame-origin after) => {:x 0 :y 100})
        (fact "the bot-right corner of the inverted frame is now the top right"
              (frame-bound after) => {:x 200 :y 0})))
