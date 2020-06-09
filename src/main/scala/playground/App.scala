package playground

import fs2._
import fs2.io.file._
import cats.effect.Blocker
import cats.effect.Resource
import cats.effect.ExitCode
import cats.implicits._
import java.nio.file.Path
import cats.effect.Sync
import cats.effect.ContextShift

import Fold.{composeChunkFolds => compose}

object App {


  def app[F[_]: Sync: ContextShift](path: Path)(blocker: Blocker): F[ExitCode] = {
    val MyFolds = compose(compose(Hash.sha256, Hash.sha512), Length.length[Byte])
    for {
      results <- Fold.fold(MyFolds)(read(path, blocker))
      val ((sha256, sha512), length) = results
      _ <- print("sha256", Hash.hex(sha256))
      _ <- print("sha512", Hash.hex(sha512))
      _ <- print("length", length.toString)
    } yield ExitCode.Success
  }

  def print[F[_]: Sync](s: String): F[Unit] = Sync[F].delay { println(s) }

  def print[F[_]: Sync](name: String, s: String): F[Unit] = print[F](s"${name}: ${s}")

  def read[F[_]: Sync: ContextShift](path: Path, blocker: Blocker): Stream[F, Byte] =
    readAll[F](path, blocker, 512)

}
