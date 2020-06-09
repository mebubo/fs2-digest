package playground

import fs2._
import Fold.ChunkFold
import java.security.MessageDigest
import java.math.BigInteger

object Hash {

  def digest(algo: Algo): ChunkFold[Byte, Array[Byte]] = new ChunkFold[Byte, Array[Byte]] {
    type I = MessageDigest
    def init: I = algo.messageDigest
    def f(d: I, c: Chunk[Byte]): I = {
      val bytes = c.toBytes
      d.update(bytes.values, bytes.offset, bytes.length)
      d
    }
    def end(d: I): Array[Byte] = d.digest
  }

  def sha256: ChunkFold[Byte, Array[Byte]] = digest(Sha256)

  def sha512: ChunkFold[Byte, Array[Byte]] = digest(Sha512)

  def hex(b: Array[Byte]): String = String.format("%064x", new BigInteger(1, b))

  sealed trait Algo {
    val name: String
    def messageDigest: MessageDigest = {
      MessageDigest.getInstance(name)
    }
  }

  case object Sha256 extends Algo {
    val name = "SHA-256"
  }

  case object Sha512 extends Algo {
    val name = "SHA-512"
  }

}
