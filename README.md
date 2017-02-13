# Hadoop Adhoc Tools

Adhoc utility tools for Hadoop.

To build the project:

```
mvn clean install
```

An executable jar file, with the dependencies included in the bundle, will be generated in the `target` directory.

## HDFS Data Latency Checker

Calculates the percentage (%) of daily partition data latency. This tool is useful for gauging how frequent is your data delayed.

It lists the contents of each daily partition of the target directory path in HDFS, gets the latest modification date of the files, then takes the
difference (in days) between the partition date and the latest modification date. The number of daily partitions with the same latency are grouped
together then each group is divided by the total number of days covered in the calculation to get the % of data that are delayed by N number of days.

Required parameters:

* `N` - where N can be any value from 1 to 12, to indicate the last N months to be covered in the computation

* `/path/to/target/directory/in/HDFS` - the base path of the daily partitioned directory to be checked

* `partition format` - the format of the target directory's daily partition. Possible values are: `[partition_year]=yyyy/[partition_month]=MM/[partition_day]=dd or [partition_dir_name]=yyyy-MM-dd`

The tool expects the `core-site.xml` and `hdfs-site.xml` to be located at `/etc/hadoop/conf/` directory where the jar will be executed.

To run this utility tool, build the project then copy the generated `hadoop-adhoc-tools-1.0-SNAPSHOT-jar-with-dependencies.jar` to the node which acts as a
client for your Hadoop cluster. Make sure that the Hadoop configuration files are in `/etc/hadoop/conf`. To execute the tool, run the following command:

```
java -jar /path/to/hadoop-adhoc-tools-1.0-SNAPSHOT-jar-with-dependencies.jar [N] [/path/to/target/directory/in/HDFS] [partition format]
```


Below is an example to calculate % of daily partition of movies data latency for the last 6 months:

```
java -jar hadoop-adhoc-tools-1.0-SNAPSHOT-jar-with-dependencies.jar 6 /data/movies year=yyyy/month=MM/day=dd
```

It will produce an output similar to this:

```
17/02/13 16:54:25 INFO HdfsDataLatencyChecker$: Starting check of data latency: 6 /data/movies year=yyyy/month=MM/day=dd
17/02/13 16:54:26 WARN NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-13: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-14: 2
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-15: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-16: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-17: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-18: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-19: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-20: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-21: 2
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-22: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-23: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-24: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-25: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-26: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-27: 2
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-28: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-29: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-30: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-08-31: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-01: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-02: 3
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-03: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-04: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-05: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-06: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-07: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-08: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-09: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-10: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-11: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-12: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-13: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-14: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-15: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-16: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-17: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-18: 2
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-19: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-20: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-21: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-22: 2
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-23: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-24: 1
17/02/13 16:54:26 INFO HdfsDataLatencyChecker$: Latency for 2016-09-25: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-09-26: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-09-27: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-09-28: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-09-29: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-09-30: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-01: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-02: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-03: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-04: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-05: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-06: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-07: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-08: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-09: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-10: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-11: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-12: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-13: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-14: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-15: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-16: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-17: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-18: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-19: 2
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-20: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-21: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-22: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-23: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-24: 2
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-25: 2
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-26: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-27: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-28: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-29: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-30: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-10-31: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-01: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-02: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-03: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-04: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-05: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-06: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-07: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-08: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-09: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-10: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-11: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-12: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-13: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-14: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-15: 2
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-16: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-17: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-18: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-19: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-20: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-21: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-22: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-23: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-24: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-25: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-26: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-27: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-28: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-29: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-11-30: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-01: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-02: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-03: 2
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-04: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-05: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-06: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-07: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-08: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-09: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-10: 2
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-11: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-12: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-13: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-14: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-15: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-16: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-17: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-18: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-19: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-20: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-21: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-22: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-23: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-24: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-25: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-26: 2
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-27: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-28: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-29: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-30: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2016-12-31: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-01: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-02: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-03: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-04: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-05: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-06: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-07: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-08: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-09: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-10: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-11: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-12: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-13: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-14: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-15: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-16: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-17: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-18: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-19: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-20: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-21: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-22: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-23: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-24: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-25: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-26: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-27: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-28: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-29: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-30: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-01-31: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-01: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-02: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-03: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-04: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-05: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-06: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-07: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-08: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-09: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-10: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-11: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Latency for 2017-02-12: 1
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Successfully gathered data latency..

17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: Data latency in /data/movies for the last 6 months:
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: T - 2: 6.521739130434782 %  (12 out of 184 days)
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: T - 3: 0.543478260869565 %  (1 out of 184 days)
17/02/13 16:54:27 INFO HdfsDataLatencyChecker$: T - 1: 92.93478260869566 %  (171 out of 184 days)
```

The `T` in the output corresponds to the partition date of the data. Analyzing the output above, it means that for the last 6 months, `92.9%` of the movies data has a latency of `T - 1`, `6.5%` of the movies data has a latency of `T - 2`, and `0.54%` of the movies data has a latency of `T - 3`.