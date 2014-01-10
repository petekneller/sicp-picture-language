; 1 - Nothing but the truth
(assert 
 (= true true))

; 2 - Simple math
(assert
 (= (- 10 (* 2 3)) 4))

; 3 - Intro to strings
(assert
 (= "HELLO WORLD" (.toUpperCase "hello world")))

; 4 - Intro to lists
(assert
 (= (list :a :b :c) '(:a :b :c)))

; 5 - Lists: conj
(assert
 (= '(1 2 3 4) (conj '(2 3 4) 1)))

(assert
 (= '(1 2 3 4) (conj '(3 4) 2 1)))

; 6 - Intro to vectors
(assert
 (= [:a :b :c] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c)))

; 7 - Vectors: conj
(assert
 (= [1 2 3 4] (conj [1 2 3] 4)))

(assert
 (= [1 2 3 4] (conj [1 2] 3 4)))

; 8 - Intro to sets
(assert
 (= #{:a, :b, :c, :d} (set '(:a :a :b :c :c :c :c :d :d))))

(require '[clojure.set])

(assert
 (= #{:a, :b, :c, :d} (clojure.set/union #{:a :b :c} #{:b :c :d})))

; 9 - Sets: conj
(assert
 (= #{1 2 3 4} (conj #{1 4 3} 2)))

; 10 - Intro to maps
(assert
 (= 20 ((hash-map :a 10, :b 20, :c 30) :b)))

(assert
 (= 20 (:b {:a 10, :b 20, :c 30})))

; 11 - Maps: conj
(assert
 (= {:a 1, :b 2, :c 3} (conj {:a 1} [:b 2] [:c 3])))

;12 - Intro to sequences
(assert
 (= 3 (first '(3 2 1))))

(assert
 (= 3 (second [2 3 4])))

(assert
 (= 3 (last (list 1 2 3))))

;13 - Sequences: rest
(assert
 (= [20 30 40] (rest [10 20 30 40])))

(assert
 (= '(20 30 40) (rest [10 20 30 40])))

;14 - Intro to functions
(assert
 (= 8 ((fn add-five [x] (+ x 5)) 3)))
 
(assert
 (= 8 ((fn [x] (+ x 5)) 3)))

(assert
 (= 8 (#(+ % 5) 3)))

(assert
 (= 8 ((partial + 5) 3)))

; 15 - Double down
(assert
 (= ((fn [x] (* 2 x)) 2) 4))

(assert
 (= (#(* 2 %) 2) 4))

(assert
 (= (#(* 2 %1) 2) 4))

(assert
 (= ((partial * 2) 2) 4))
 
;16 - Hello world
(assert
 (= ((fn [s] (str "Hello, " s "!")) "Dave") "Hello, Dave!"))

(assert
 (= (#(str "Hello, " % "!") "Dave") "Hello, Dave!"))

;17 - Sequences: map
(assert
 (= '(6 7 8) (map #(+ % 5) '(1 2 3))))

;18 - Sequences: filter
(assert
 (= (list 6 7) (filter #(> % 5) '(3 4 5 6 7))))

;19 - Last Element
(assert
 (= (last [1 2 3 4 5]) 5))

(assert
 (= ((comp first reverse) '(5 4 3)) 3))

(assert
 (= (#(first (reverse %)) '(5 4 3)) 3))

;20 - Penultimate element
(assert
 (= ((comp second reverse) (list 1 2 3 4 5)) 4))

(assert
 (= ((comp first #(drop (- (count %1) 2) %1)) (list 1 2 3 4 5)) 4))

;35 - Local bindings
(assert
 (= 7 (let [x 5] (+ 2 x))))

;36 - Let it be
(assert
 (= 10 (let [x 7 y 3 z 1] (+ x y))))

(assert
 (= 4 (let [x 7 y 3 z 1] (+ y z))))

(assert
 (= 1 (let [x 7 y 3 z 1] z)))
