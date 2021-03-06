(ns producer-consumer-workflow.core
  (:require [producer-consumer-workflow.process-controller :as controller]
            [clojure.core.async :as async])
  (:gen-class)
  (:import (java.util Date)))

(defn -main [& args]
  (let [start-time (Date.)
        thread-number (or (first args) 10)
        item-number (or (second args) 100)
        input-channel (async/to-chan (range item-number))
        output-channel (async/chan 10)]
    (controller/get-from-input-channel thread-number input-channel output-channel)
    (controller/get-from-output-channel start-time output-channel)))
