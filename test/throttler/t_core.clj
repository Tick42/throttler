(ns throttler.t-core
  (:require [clojure.core.async :refer [chan timeout <!! >!! close!]]
            [throttler.core :refer :all]
            [throttler.bench :refer [rate]]

            [clojure.test :refer [deftest is testing use-fixtures]]))

(defn- cljs-env?
  [env]
  (boolean (:ns env)))

(defmacro roughly
  [value expected range]
  (let [is (if (cljs-env? &env) 'cljs.test/is 'clojure.test/is)]
    `(let [v# ~value
           e# ~expected
           r# ~range]
       (~is (and (>= v# (- e# r#))
                 (<= v# (+ e# r#)))))))

(deftest throttle-fn-test
  (let [+? (throttle-fn + 10 :second)
        +?? (throttle-fn + 0.00001 :microsecond)]           ; same, but expressed differently

    (testing "It returns something"
      (is (throttle-fn + 1 :second))
      (is (throttle-fn + 1 :second 9)))

    (testing "It acts like the original function"
      (is (= (+? 1 1) (+ 1 1)))
      (is (= (+?) (+)))
      (is (= (+? 1 1 1.2) (+ 1 1 1.2))))

    (testing "It runs at approximately the desired rate"
      (roughly (rate (fn [] (+? 1 1)) 10) 10 2)
      (roughly (rate (fn [] (+?? 1 1)) 10) 10 2))

    (testing "It fails graciously with the wrong unit"
      (is (thrown? IllegalArgumentException (throttle-fn + 1 :foo)))
      (is (thrown? IllegalArgumentException (throttle-fn + -1 :hour)))
      (is (thrown? IllegalArgumentException (throttle-fn + :foo :hour)))
      (is (thrown? IllegalArgumentException (throttle-fn + 0 :hour)))
      (is (thrown? IllegalArgumentException (throttle-fn + 1 :hour :foo)))
      (is (thrown? IllegalArgumentException (throttle-fn + 1 :hour -1))))))



(deftest throttle-chan-test
  (let [in (chan 1)
        out (throttle-chan in 10 :second)]
    (testing "acts like a piped channel"
      (>!! in :token)
      (is (= :token (<!! out))))
    (testing "closing the input closes the output"
      (close! in)
      (is (nil? (<!! out))))))

;(facts "about throttle-chan"
;       (let [in (chan 1)
;             out (throttle-chan in 10 :second)]
;
;         (fact "acts like a piped channel"
;               (>!! in :token)
;               (<!! out) => :token)
;
;         (fact "closing the input closes the output"
;               (close! in)
;               (<!! out) => nil)))
