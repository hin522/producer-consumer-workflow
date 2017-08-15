(ns producer-consumer-workflow.chan
  (:require [clojure.core.async :as async])
  (:gen-class))

(def channel (async/chan 3))

(defn put-to-channel [item]
  (async/>!! channel item))

(defn async-get-from-channel []
  (async/go-loop []
    (let [item (async/<! channel)]
      (if item
        (do
          (println "async item is" item)
          (recur))
        (println "channel is closed")))))