(defproject tick42/throttler "1.0.1-SNAPSHOT"
  :description "Control the throughput of function calls and core.async channels using the token bucket algorithm"
  :url "https://github.com/tick42/throttler"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-RC1" :scope "provided"]
                 [org.clojure/spec.alpha "0.1.143" :scope "provided"]
                 [org.clojure/core.async "0.3.465" :scope "provided"]]
  :profiles {:dev {:dependencies [[criterium "0.4.3"]]
                   :plugins      [[test2junit "1.3.3"]]}}

  :test2junit-run-ant false
  :test2junit-output-dir "test-results")
