(ns producer-consumer-workflow.go
  (:require [clojure.core.async :as async]))

(def go-channel (async/chan))

(defn print-item []
  (async/go (println "Item is" (async/<! go-channel))))

(defn put-item-to-channel [item]
  (async/>!! go-channel item))