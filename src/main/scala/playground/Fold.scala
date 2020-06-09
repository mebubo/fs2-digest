package playground

import cats.effect.IO
import fs2._
import fs2.io.file._
import java.nio.file.Paths
import cats.effect.Blocker
import cats.effect.Resource
import cats.effect.IOApp
import cats.effect.ExitCode
import cats.Functor
import cats.implicits._
import java.security.MessageDigest

object Fold {

  trait ChunkFold[A, B] {
    type I
    def init: I
    def f(i: I, c: Chunk[A]): I
    def end(i: I): B
  }

  def fold[F[_], G[_], A, B](cf: ChunkFold[A, B])(s: Stream[F, A])(implicit c: Stream.Compiler[F, G], f: Functor[G]): G[B] =
    s.compile.foldChunks(cf.init)((a, b) => cf.f(a, b)).map(cf.end)

  def compose[A, B, C](f1: ChunkFold[A, B], f2: ChunkFold[A, C]): ChunkFold[A, (B, C)] =
    new ChunkFold[A, (B, C)] {
      type I = (f1.I, f2.I)
      def init: I = (f1.init, f2.init)
      def f(i: I, ca: Chunk[A]): I = (f1.f(i._1, ca), f2.f(i._2, ca))
      def end(i: I): (B, C) = (f1.end(i._1), f2.end(i._2))
    }

}

