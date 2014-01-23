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

(facts "frames can be scaled"
       (let [before (make-frame {:x 10 :y 100} 100 100)
             after (scale-frame before 2 3)]
         (fact "scaling does not affect the origin of the frame"
               (frame-origin before) => {:x 10 :y 100}
               (frame-origin after) => {:x 10 :y 100})
         (fact "each axis can be scaled independently"
               (frame-width after) => 200
               (frame-height after) => 300)
         (fact "both axis together"
               (let [after2 (scale-frame before 2)]
                 (frame-width after2) => 200
                 (frame-height after2) => 200))))

(fact "frames can be translated"
       (let [before (make-frame origin 20 20)
             after (translate-frame before 2 8)]
         (frame-origin after) => {:x 2 :y 8}))
