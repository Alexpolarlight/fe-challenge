(ns kasta.fe.subscriptions
  (:require [kasta.fe.lib.system :refer [create-subscription]]))

;; app state change subscriptions

(defn get-init-state [] (create-subscription [:init-state]))
