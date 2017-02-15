package com.oching.hadoop.adhoc.tools

import java.io.File

import org.apache.hadoop.fs.{FileUtil, Path}
import org.apache.hadoop.hdfs.{DistributedFileSystem, HdfsConfiguration, MiniDFSCluster}

import org.joda.time.DateTime

import org.junit.runner.RunWith

import org.specs2.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAll

@RunWith(classOf[JUnitRunner])
class HdfsDataLatencyCheckerSpec extends Specification with BeforeAll {
  def is =
    s2"""
        An HdfsDataLatencyChecker
          should throw an error if no arguments are supplied $testMissingArguments()
          should throw an error if the number of arguments supplied are more than expected $testArgumentsMoreThanExpectedNumberOfArguments()
          should throw an error if the specified last N value is invalid $testInvalidLastNArgument()
          should throw an error if the specified partition format value is invalid $testInvalidPartitionFormatArgument()
      """

  private var _fs: DistributedFileSystem = _
  val baseDir = new File(".").getAbsolutePath
  val hdfsSourcesDir = "/tmp/sources/"
  lazy val fs: DistributedFileSystem = _fs
  var hdfsURI: String = "hdfs://localhost:8020"

  def testMissingArguments() = {
    HdfsDataLatencyChecker.main(Array[String]()) must throwA[Exception]
  }

  def testArgumentsMoreThanExpectedNumberOfArguments() = {
    HdfsDataLatencyChecker.main(Array[String]("1", "2", "3", "4")) must throwA[Exception]
  }

  def testInvalidLastNArgument() = {
    HdfsDataLatencyChecker.main(Array[String]("A", "/path/to/dir", "year=yyyy/month=MM/day=dd")) must throwA(
      new Exception("Invalid Last N months argument. Possible values are 1-12"))
  }

  def testInvalidPartitionFormatArgument() = {
    HdfsDataLatencyChecker.main(Array[String]("6", "/path/to/dir", "invalid_format")) must throwA(
      new Exception("Invalid partition format. Possible values are [partition_year]=yyyy/[partition_month]=MM/[partition_day]=dd or [partition_dir_name]=yyyy-MM-dd"))
  }

  // partition: year=yyyy/month=MM/day=dd
  def testLatencyCheckForLast6MonthsWithYearMonthDayPartition() = {

  }

  def testLatencyCheckForLast12MonthsWithYearMonthDayPartition() = {

  }

  // partition: partition_date=yyyy-MM-dd
  def testLatencyCheckForLast6MonthsWithDatePartition() = {

  }

  def testLatencyCheckForLast12MonthsWithDatePartition() = {

  }

  def testNoPartitions() = {

  }

  override def beforeAll() = {
    // setup a miniDFS cluster
    val hdfsRootDir = new File(baseDir, s"/target/hdfs/data-latency-check").getAbsoluteFile()
    val now = DateTime.now

    FileUtil.fullyDelete(hdfsRootDir)

    val conf = new HdfsConfiguration()
    conf.set(MiniDFSCluster.HDFS_MINIDFS_BASEDIR, hdfsRootDir.getAbsolutePath())

    val builder = new MiniDFSCluster.Builder(conf)
    val hdfsCluster = builder.build()
    hdfsURI = s"hdfs://localhost:${hdfsCluster.getNameNodePort()}"

    // create HDFS paths
    _fs = hdfsCluster.getFileSystem
    val sourcesPath = new Path(hdfsSourcesDir)
    val movies = new Path(sourcesPath, "movies") // partitioned by year/month/day
    val tvShows = new Path(sourcesPath, "tv_shows") // partitioned by date

    fs.create(sourcesPath)
//    fs.create(movies)
//    fs.create(tvShows)

//    var twelveMonthsAgo = now.minusMonths(12)
//    while (!twelveMonthsAgo.isEqual(now)) {
//      fs.create(new Path(movies, s"year=${twelveMonthsAgo.toString("yyyy")}/month=${twelveMonthsAgo.toString("MM")}/day=01"))
//      fs.create(new Path(tvShows, s"partition_date=${twelveMonthsAgo.toString("yyyy-MM-dd")}"))
//
//      twelveMonthsAgo = twelveMonthsAgo.plusMonths(1)
//    }
  }

}
