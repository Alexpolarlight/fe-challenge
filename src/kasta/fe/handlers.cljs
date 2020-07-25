(ns kasta.fe.handlers
  (:require [kasta.fe.lib.system :refer [register-handler]]))

;; app state change handlers

(register-handler :init-state
  (fn [state campaignsData]
    (println "handlers campaignsData" campaignsData)
    (reset! state campaignsData)))
