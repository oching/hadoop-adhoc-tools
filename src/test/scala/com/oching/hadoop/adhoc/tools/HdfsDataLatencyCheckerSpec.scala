package com.oching.hadoop.adhoc.tools

import java.io.File

import org.apache.hadoop.fs.{FileUtil, Path}
import org.apache.hadoop.hdfs.{DistributedFileSystem, HdfsConfiguration, MiniDFSCluster}

import org.junit.runner.RunWith

import org.specs2.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAll

@RunWith(classOf[JUnitRunner])
class HdfsDataLatencyCheckerSpec extends Specification with BeforeAll {
  def is =
    s2"""
        An HdfsDataLatencyChecker
          should throw an error if no arguments are supplied $testLatencyCheckMissingArguments()
          should throw an error if the number of arguments supplied are more than expected $testLatencyCheckArgumentsMoreThanExpectedNumberOfArguments()
      """

  private var _fs: DistributedFileSystem = _
  val baseDir = new File(".").getAbsolutePath
  val hdfsSourcesDir = "/tmp/sources/"
  lazy val fs: DistributedFileSystem = _fs
  var hdfsURI: String = "hdfs://localhost:8020"

  def testLatencyCheckMissingArguments() = {
    HdfsDataLatencyChecker.main(Array[String]()) must throwA[Exception]
  }

  def testLatencyCheckArgumentsMoreThanExpectedNumberOfArguments() = {
    HdfsDataLatencyChecker.main(Array[String]("1", "2", "3", "4")) must throwA[Exception]
  }

  // TODO: add test for invalid arguments
  def testLatencyCheckInvalidArguments() = {

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


  override def beforeAll() = {
    // setup a miniDFS cluster
    val hdfsRootDir = new File(baseDir, s"/target/hdfs/data-latency-check").getAbsoluteFile()

    FileUtil.fullyDelete(hdfsRootDir)

    val conf = new HdfsConfiguration()
    conf.set(MiniDFSCluster.HDFS_MINIDFS_BASEDIR, hdfsRootDir.getAbsolutePath())

    val builder = new MiniDFSCluster.Builder(conf)
    val hdfsCluster = builder.build()
    hdfsURI = s"hdfs://localhost:${hdfsCluster.getNameNodePort()}"

    // create HDFS paths
    _fs = hdfsCluster.getFileSystem
    val sourcesPath = new Path(hdfsSourcesDir)

    fs.create(sourcesPath)
  }
}
