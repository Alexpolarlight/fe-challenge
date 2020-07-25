(ns kasta.fe.lib.system
  (:require [rum.core :as rum]))

(def state (atom nil))

(def tag-state (atom "date"))

(def handlers (atom {}))

(defn register-handler [name handler]
  "register a new change handler"
  (swap! handlers update name #(conj (or % []) handler)))

(defn create-subscription [target]
  "create state subscription"
  (rum/react (rum/cursor state target)))

(defn dispatch [name & args]
  "call change handler"
  (do
    (println "dispatch " name args)
    (doseq [handler (name @handlers)]
      (apply handler (cons state args)))))
