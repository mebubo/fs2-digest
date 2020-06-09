package playground

import fs2._
import java.security.MessageDigest
import java.math.BigInteger

import Fold.ChunkFold

object Hash {

  def digest(name: String): ChunkFold[Byte, Array[Byte]] = new ChunkFold[Byte, Array[Byte]] {
    type I = MessageDigest
    def init: I = MessageDigest.getInstance(name)
    def f(d: I, c: Chunk[Byte]): I = {
      val bytes = c.toBytes
      d.update(bytes.values, bytes.offset, bytes.length)
      d
    }
    def end(d: I): Array[Byte] = d.digest
  }

  def sha256: ChunkFold[Byte, Array[Byte]] = digest("SHA-256")

  def sha512: ChunkFold[Byte, Array[Byte]] = digest("SHA-512")

  def hex(b: Array[Byte]): String = String.format("%064x", new BigInteger(1, b))
}
