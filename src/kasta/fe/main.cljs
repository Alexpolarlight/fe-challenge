(ns kasta.fe.main
  (:require [rum.core :as rum]))

(enable-console-print!)
(println "Main println")

(def state (atom "active"))
;(def shares-state (atom shares))

;(add-watch app-state :watcher)


(rum/defc heading < rum.static [size text]
  (do
    (println text))
  [size text])

(rum/defc tagSelector [tag]
    (println 'tag' tag)
  [:div
   (heading :h3 "Active shares list")
   (heading :h4 "Filter tags list")
   (heading :h4 @tag)
   [:button {:on-click #(reset! tag "F")} "Females"]
   [:button {:on-click #(reset! tag "M")} "Males"]
   [:button {:on-click #(reset! tag "C")} "Kids"]
   [:button {:on-click #(reset! tag "H")} "Home"]
   [:button {:on-click #(reset! tag "A")} "FoodVine"]
   ]
)

(defn shares-table
  [filter]
  [:table {:class "table table-condensed"}]
    [:thead
     [:tr
      [:th "Name"]
      [:th "Description"]
      [:th "Tag"]
      [:th "Starts at"]
      [:th "Finishes at"]]]
    [:tbody
;     (for [{:keys [ name
;                  description
;                  tags
;                  starts_at
;                  finishes_at
;                  ]}])
     ]
  )

(add-watch state :render
           #(rum/mount
             (tagSelector state)
           (js/document.getElementById "content")))

(rum/defc Root []
  (println "state " state)
  [(tagSelector state)])


(defn ^:export trigger-render []
  (rum/mount (Root) (js/document.getElementById "content")))
