(assert 
 (= true true))

(assert
 (= "HELLO WORLD" (.toUpperCase "hello world")))

(assert
 (= (- 10 (* 2 3)) 4))

(assert
 (= (list :a :b :c) '(:a :b :c)))

(assert
 (= '(1 2 3 4) (conj '(2 3 4) 1)))

(assert
 (= '(1 2 3 4) (conj '(3 4) 2 1)))

(assert
 (= [:a :b :c] (list :a :b :c) (vec '(:a :b :c)) (vector :a :b :c)))

(assert
 (= [1 2 3 4] (conj [1 2 3] 4)))

(assert
 (= [1 2 3 4] (conj [1 2] 3 4)))