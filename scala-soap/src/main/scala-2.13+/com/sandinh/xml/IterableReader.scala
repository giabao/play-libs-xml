package com.sandinh.xml

import scala.collection.Factory

trait IterableReader {
  implicit def traversableReader[F[_], A](implicit bf: Factory[A, F[A]],
                                          r: XmlReader[A]): XmlReader[F[A]] = new XmlReader[F[A]] {
    def read(x: xml.NodeSeq): Option[F[A]] = {
      val ret = bf.fromSpecific(x.flatMap(n => r.read(n)))
      Some(ret)
    }
  }
}
