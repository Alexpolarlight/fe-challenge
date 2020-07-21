(ns kasta.campaigns.campaigns_networking
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [rum.core :as rum]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(enable-console-print!)
(println "campaigns check ")

(go
  (let [response (<! (http/get "http://localhost:5000/api/campaigns"))]
    (def campaignsData (:body response))))
