package com.sandinh.xml

import scala.collection.generic.CanBuildFrom
import scala.language.higherKinds

trait IterableReader {
  implicit def traversableReader[F[_], A](implicit bf: CanBuildFrom[F[_], A, F[A]], r: XmlReader[A]): XmlReader[F[A]] = new XmlReader[F[A]] {
    def read(x: xml.NodeSeq): Option[F[A]] = {
      val builder = bf()
      x.foreach {
        n => r.read(n) foreach builder.+=
      }
      Some(builder.result())
    }
  }
}
