; 1
(assert 
 (= true true))

; 2
(assert
 (= "HELLO WORLD" (.toUpperCase "hello world")))

; 3
(assert
 (= (- 10 (* 2 3)) 4))

; 4
(assert
 (= (list :a :b :c) '(:a :b :c)))

; 5
(assert
 (= '(1 2 3 4) (conj '(2 3 4) 1)))

(assert
 (= '(1 2 3 4) (conj '(3 4) 2 1)))

; 6
(assert
 (= [:a :b :c] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c)))

; 7
(assert
 (= [1 2 3 4] (conj [1 2 3] 4)))

(assert
 (= [1 2 3 4] (conj [1 2] 3 4)))

; 8
(assert
 (= #{:a, :b, :c, :d} (set '(:a :a :b :c :c :c :c :d :d))))

(require '[clojure.set])

(assert
 (= #{:a, :b, :c, :d} (clojure.set/union #{:a :b :c} #{:b :c :d})))

; 9
(assert
 (= #{1 2 3 4} (conj #{1 4 3} 2)))

; 10
(assert
 (= 20 ((hash-map :a 10, :b 20, :c 30) :b)))

(assert
 (= 20 (:b {:a 10, :b 20, :c 30})))

; 11
(assert
 (= {:a 1, :b 2, :c 3} (conj {:a 1} [:b 2] [:c 3])))