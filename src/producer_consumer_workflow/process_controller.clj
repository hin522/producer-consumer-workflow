(ns producer-consumer-workflow.process-controller
  (:require [clojure.core.async :refer :all])
  (:import (java.util Date)))

(defn- process-input-channel [item output-channel]
  (Thread/sleep 300)
  (let [processed-item (+ 200 item)]
    (>!! output-channel processed-item)))

(defn- process-output-channel [item]
  (println "Processed item from output channel ..." item))

(defn get-from-input-channel [thread-number input-channel output-channel]
  (dotimes [_ thread-number]
    (thread
      (loop [item (<!! input-channel)]
        (if item
          (do (process-input-channel item output-channel)
              (recur (<!! input-channel)))
          (close! output-channel))))))                   ;; output channel is closed by the producer

(defn get-from-output-channel [start-time output-channel]
  (go-loop [item (<! output-channel)]
    (if item
      (do (process-output-channel item)
          (recur (<! output-channel)))
      (do (println "Output channel closed")
          (println "Process time" (- (.getTime (Date.)) (.getTime start-time)) "milliseconds")))))
