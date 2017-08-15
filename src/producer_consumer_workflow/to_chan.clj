(ns producer-consumer-workflow.to-chan
  (:require [clojure.core.async :as async]))

(def number-channel (async/to-chan (range 5)))

(defn get-from-channel []
  (loop []
    (let [item (async/<!! number-channel)]
      (if item
        (do
          (println "item is" item)
          (recur))
        (println "channel closed")))))
