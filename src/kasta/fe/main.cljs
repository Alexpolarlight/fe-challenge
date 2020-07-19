(ns kasta.fe.main
  (:require [rum.core :as rum]
            [clojure.string :as string]
            [kasta.shares.shares_mock :as shares]))

(enable-console-print!)

(println "main  ")
;(println "main Json " shares/parsedJson)
(def sharesData shares/parsedJson)
(def shares-state (atom sharesData
;                          :id 121347
;                          :name "H&M"
;                          :description "Трендовые обновки для вашего гардероба "
;                          :tags "FM"
;                          :starts_at "2020-07-20T03:00:00.000Z"
;                          :finishes_at "2020-07-21T03:00:00.000Z"

))

;;; Start Table
;(defn filter-shares
;  [filterstring]
;  (filter #(re-find (->>(str filterstring)
;                        (string/upper-case)
;                        (re-pattern))
;            (string/upper-case (:tags %)))
;          @shares-state)
;  )

(rum/defc shares-table
  [data]
    (println "data " data)
  [:table {:class "table"}
   [:thead
    [:tr
     [:th "Name"]
     [:th "Description"]
     [:th "Tag"]
     [:th "Starts at"]
     [:th "Finishes at"]]]
   [:tbody
    ;     (for [{:keys [ id
    ;                  name
    ;                  description
    ;                  tags
    ;                  starts_at
    ;                  finishes_at
    ;                  ]}]
    ;       ^{:key id}
    [:tr
     [:td "name"]
     [:td "description"]
     [:td "tags"]
     [:td "starts_at"]
     [:td "finishes_at"]
     ]]])
;; End Table

;; Start Header
(def state (atom "active"))

(rum/defc heading < rum.static [size text]
  [size text])

(rum/defc tagSelector [tag]
  [:div
   (heading :h3 "Active shares list")
   (heading :h4 "Filter tags list")
   (heading :h4 @tag)
   [:button {:on-click #(reset! tag "F")} "Females"]
   [:button {:on-click #(reset! tag "M")} "Males"]
   [:button {:on-click #(reset! tag "C")} "Kids"]
   [:button {:on-click #(reset! tag "H")} "Home"]
   [:button {:on-click #(reset! tag "A")} "FoodVine"]
   [:div (shares-table shares-state)]
   ]
)
;; End Header



;; Start Render
(add-watch state :render
           #(rum/mount
             (tagSelector state)
           (js/document.getElementById "content")))

(rum/defc Root []
;  (println "shares-state - " shares-state)
;  (println "state - " state)
  [(tagSelector state)]
  )

(defn ^:export trigger-render []
  (rum/mount (Root) (js/document.getElementById "content")))
;; End Render
