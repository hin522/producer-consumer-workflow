(ns producer-consumer-workflow.process-controller
  (:require [clojure.core.async :refer :all])
  (:import (java.util Date)))

(defn watch-process-counter [process-counter output-channel]
  (add-watch process-counter :watcher
             (fn [key atom old-state new-state]
               (if (and (= 1 old-state) (= 0 new-state))
                 (close! output-channel)))))

(defn- process-input-channel [item output-channel]
  (Thread/sleep 300)
  (let [processed-item (+ 200 item)]
    (>!! output-channel processed-item)))

(defn- process-output-channel [item]
  (println "Processed item from output channel ..." item))

(defn- create-concurrent-process [process-counter input-channel output-channel]
  (thread
    (swap! process-counter inc)
    (loop [item (<!! input-channel)]
      (if item
        (do (process-input-channel item output-channel)
            (recur (<!! input-channel)))
        (swap! process-counter dec)))))

(defn get-from-input-channel [thread-number input-channel output-channel]
  (let [process-counter (atom 0)]
    (watch-process-counter process-counter output-channel) ;; this will close the output channel at the end
    (dotimes [_ thread-number]
      (create-concurrent-process process-counter input-channel output-channel))))

(defn get-from-output-channel [start-time output-channel]
  (go-loop [item (<! output-channel)]
    (if item
      (do (process-output-channel item)
          (recur (<! output-channel)))
      (do (println "Output channel closed")
          (println "Process time" (- (.getTime (Date.)) (.getTime start-time)) "milliseconds")))))
