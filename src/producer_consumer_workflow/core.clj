(ns producer-consumer-workflow.core
  (:require [producer-consumer-workflow.process-controller :as controller]
            [clojure.core.async :refer :all])
  (:gen-class)
  (:import (java.util Date)))

(defn -main [& args]
  (println "Starting.....")

  (let [start-time (Date.)
        thread-number (or (first args) 10)
        item-number (or (second args) 100)
        input-channel (to-chan (range item-number))
        output-channel (chan)]
    (controller/get-from-input-channel thread-number input-channel output-channel)
    (controller/get-from-output-channel start-time output-channel))

  (println "Exit....."))
