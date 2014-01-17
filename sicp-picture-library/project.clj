(defproject sicp-picture-library "1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [midje "1.6.0"]]
  :main ^:skip-aot picture.app
  :plugins [[lein-midje "3.0.0"]])
