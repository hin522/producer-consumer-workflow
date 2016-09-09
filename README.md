# producer-consumer-workflow

This is a simple experiment to test the concurrent processing using clojure.core.async channel.

<pre>
                                                  +----------+
                                                  | Consumer/|
                                        +-------> | Producer | +------+
                                        |         +----------+        |
                                        |         +----------+        |
                                        |         | Consumer/|        |
                                        +-------> | Producer | +------+
                                        |         +----------+        |
+----------+                            |         +----------+        |                            +----------+
| Producer |      +----------------+    |         | Consumer/|        |    +----------------+      | Consumer |
|          | ---> | input channel  | +----------> | Producer | +---------> | output channel | ---> |          |
+----------+      +----------------+    |         +----------+        |    +----------------+      +----------+
                                        |         +----------+        |
                                        |         | Consumer/|        |
                                        +-------> | Producer | +------+
                                        |         +----------+        |
                                        |         +----------+        |
                                        |         | Consumer/|        |
                                        +-------> | Producer | +------+
                                                  +----------+
</pre>

## How to run

    $ lein repl
    $ (-main [number_of_thread] [number_of_item_to_be_processed])