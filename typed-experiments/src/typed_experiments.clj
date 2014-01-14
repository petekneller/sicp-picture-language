(ns typed-experiments
  (:import (clojure.lang IPersistentVector IPersistentList))
  (:require [clojure.core.typed :as typed]))

;(def unannotated-inc (fn [x] (+ x 1)))

;(typed/ann annotated-inc [Any -> Any]); can not apply Any to +
;(typed/ann annotated-inc [Number -> Any]); okay, result type covariant?
;(typed/ann annotated-inc [Number -> Long]); result type expected Long, found Number from +
(typed/ann annotated-inc [Number -> Number])
(def annotated-inc (fn [x] (+ x 1)))

(typed/ann ok1 [ -> Number])
(defn ok1 [] (annotated-inc 2))

; not okay - expected Number but found (Value "2")
;(typed/ann not-ok1 [ -> Number])
;(defn not-ok1 [] (annotated-inc "2"))

(typed/ann ok2 [ -> nil]) ; empty place for a no-arg function; println returns nil
(defn ok2 [] (println (annotated-inc 2)))



; get the type of a pre-annotated var by cf'ing the form, eg:
;(typed/cf println) ; (Fn [Any * -> nil])
;(typed/cf map); =>
;  (All [c a b1918 ...]
;       (Fn
;         [(Fn [a b ... b -> c]) (typed/NonEmptySeqable a) (typed/NonEmptySeqable b) ... b -> (typed/NonEmptySeq c)]
;         [(Fn [a b ... b -> c]) (U (Seqable a) nil) (U nil (Seqable b)) ... b -> (typed/Seq c)]))



;(typed/ann my-f [Number -> Number])
;(defn my-f [x]
;  (let [_ (typed/ann-form x Integer)] x)) ;not ok - local binding isn't a subtype of Integer
; so it' useless to try to strengthen type constraints intra-fn


; using a polymorphic fn like map
(typed/ann ok3 [ -> nil])
(defn ok3 []
  (let [_ (map (constantly 5) [1 2 3])] ; constantly works with map
    nil))

(typed/ann ok4 [ -> nil])
(defn ok4 []
  (let [id (typed/inst identity Number) ; must instantiate poly function with concrete types
        _ (map id [1 2 3])]
    nil))

(typed/ann ok5 [ -> nil])
(defn ok5 []
  (let [m (typed/inst map Number Number) ; must instantiate poly function with concrete types
        _ (m identity [1 2 3])]
    nil))

(typed/ann ^:no-check ok6 [ -> nil]) ; or ignore the usage
(defn ok6 []
  (let [_ (map identity [1 2 3])]
    nil))


; using a no-check def
(typed/ann ^:no-check dodgy [Number -> Number])
(defn dodgy [x] (str x)) ;actually returns String

(typed/ann naive [ -> boolean])
(defn naive [] (> (dodgy 2) 3)) ;boom!



;(typed/ann ok4 (typed/Vec Number)); ok
;(typed/ann ok4 (typed/List Number))
;(def ok4 [1 2 3])


