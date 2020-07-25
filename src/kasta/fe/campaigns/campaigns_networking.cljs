(ns kasta.fe.campaigns.campaigns_networking
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [rum.core :as rum]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [kasta.fe.handlers]
            [kasta.fe.lib.system :refer [dispatch]]))
(enable-console-print!)

(go
  (let [response (<! (http/get "http://localhost:5000/api/campaigns"))]
    (def campaignsData (:body response))
    (dispatch :init-state campaignsData)
;    (println "campaignsData response" campaignsData)
    ))

