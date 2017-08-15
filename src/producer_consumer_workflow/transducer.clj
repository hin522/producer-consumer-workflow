(ns producer-consumer-workflow.transducer
  (:require [producer-consumer-workflow.process-controller :as controller]
            [clojure.core.async :as async])
  (:gen-class)
  (:import (java.util Date)))

(def number-transducer
  (comp
    (filter even?)
    (map (partial + 1000))
    (take 10)))

(defn start [& args]
  (let [start-time (Date.)
        thread-number (or (first args) 10)
        item-number (or (second args) 100)
        input-channel (async/to-chan (range item-number))
        output-channel (async/chan 10 number-transducer)]
    (controller/get-from-input-channel thread-number input-channel output-channel)
    (controller/get-from-output-channel start-time output-channel)))
