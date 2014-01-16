(ns typed-experiments
  (:import (clojure.lang IPersistentVector IPersistentList ISeq IPersistentCollection IPersistentMap Keyword IMapEntry))
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
;(typed/cf println) ; => (Fn [Any * -> nil])
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
(defn naive [] (> (dodgy 2) 3)) ; boom! and yet it type-checks


;(typed/cf [1 2 3] ; => [(HVec [(Value 1) (Value 2) (Value 3)]) {:then tt, :else ff}]
(typed/ann ok7 (IPersistentVector Number)) ; ok
;(typed/ann ok7 (IPersistentVector Integer)) ; ok
;(typed/ann ok7 (typed/Vec Integer)) ; ok - Vec is an alias for IPersistentVector
;(typed/ann ok7 (IPersistentCollection Number)) ; ok - Vectors are Collections
;(typed/ann ok7 (IPersistentList Number)) ; not okay - Vectors are not Lists
;(typed/ann ok7 (ISeq Number)) ; not ok - Vector is not an ISeq? Interesting!
;(typed/ann ok7 (typed/Seqable Number)) ; ok - Vector can have seq called upon it
;(typed/ann ok7 java.lang.Iterable) ; ok - Vector is an Iterable, but interesting that it doesn't take type args
;(typed/ann ok7 (HVec [Number Number])) ; not ok - wrong number of elements
;(typed/ann ok7 (HVec [Integer Number typed/Int])) ; ok - 1,2,3 are subtypes of all of these types...
;(typed/ann ok7 (HVec [Number Number (Value 3)])) ; ok - ...as well as the singleton type (Value x)
;(typed/ann ok7 (HVec [Number Number (Value 4)])) ; not ok - wrong singleton value
(def ok7 [1 2 3])


; use of HVec - needs some more thought
(typed/ann a-generator [ -> (typed/Seqable (typed/Option Number))])
(defn a-generator []
    ; some interesting stuff goes on, and then returns a tuple
    [1 2 3])

(typed/ann consumes-a-generator [ -> nil])
(defn consumes-a-generator []
  (let [some-values (a-generator)
        ; some interesting stuff goes on...
        ; ... then make use of the tuple

        ; imagine we were using the three values in a tightly controlled manner, ie.
        ; we need all three, and need to know they're numbers, so we'll fake that with...
        foo (first some-values)
        bar (second some-values)
        baz (nth some-values 2)
        ]
    (println foo bar baz)))



; map
(typed/ann m (typed/Map Keyword String))
(def m {:a "a" :b "b"})

; failed first experiments - before posting to google groups
;(typed/ann f [(typed/Seq Any) -> String])
;(defn f [[k v ]] (str k v))
;(typed/ann ms (typed/Option (typed/NonEmptySeq (U (IMapEntry Keyword String) (HVec [Keyword String])))))
;(def ms (seq m))
;(typed/ann mp (typed/Seq (HVec [Keyword String])))
;(def mp ((typed/inst seq (HVec [Keyword String])) m))
;(typed/ann my-map (typed/Seq String))
;(def my-map ((typed/inst map String (typed/Seq Any)) f ms))

(typed/ann ambrose-f [(typed/Seqable Any) -> String]) ; this is the trick - a HVec is a Seqable, but not a Seq
(defn ambrose-f [[k v]] (str k v))
(typed/ann ambrose-result [ -> Any])
(defn ambrose-result [] (map ambrose-f m))

; proof
;(typed/ann hvec1 [ -> (typed/Vec Any)]) ; ok of course
;(typed/ann hvec1 [ -> (typed/Seq Any)]) ; not ok
(typed/ann hvec1 [ -> (typed/Seqable Any)]) ; ok
(defn hvec1 [] [1 2 3])

